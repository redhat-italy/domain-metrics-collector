/**
 * 
 */
package com.redhat.it.customers.dmc.core.services.configuration.impl;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URISyntaxException;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.Collection;
import java.util.Collections;
import java.util.Map;
import java.util.Properties;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

import javax.annotation.PostConstruct;
import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.context.Initialized;
import javax.enterprise.event.Event;
import javax.enterprise.event.Observes;
import javax.inject.Inject;

import org.codehaus.jackson.map.ObjectMapper;
import org.slf4j.Logger;

import com.redhat.it.customers.dmc.core.constants.Constants;
import com.redhat.it.customers.dmc.core.constants.SystemConfigurationKey;
import com.redhat.it.customers.dmc.core.dto.configuration.Configuration;
import com.redhat.it.customers.dmc.core.dto.event.RestartCollectorEvent;
import com.redhat.it.customers.dmc.core.dto.event.StartCollectorEvent;
import com.redhat.it.customers.dmc.core.dto.event.StopCollectorEvent;
import com.redhat.it.customers.dmc.core.exceptions.ConfigurationAlreadyExistsException;
import com.redhat.it.customers.dmc.core.exceptions.ConfigurationDeleteException;
import com.redhat.it.customers.dmc.core.exceptions.ConfigurationException;
import com.redhat.it.customers.dmc.core.exceptions.ConfigurationNotFoundException;
import com.redhat.it.customers.dmc.core.exceptions.ConfigurationStoreException;
import com.redhat.it.customers.dmc.core.services.configuration.ConfigurationService;
import com.redhat.it.customers.dmc.core.util.ConfigurationFileFilter;

/**
 * The Class ConnectionManagerServiceImpl.
 *
 * @author Andrea Battaglia
 */
@ApplicationScoped
public class ConfigurationServiceImpl implements ConfigurationService {

    private static final ConfigurationFileFilter CONFIGURATION_FILE_FILTER = new ConfigurationFileFilter();

    private final ObjectMapper objectMapper;

    private Path JAR_FILE_LOCATION;

    private Path rootPath;
    private Path configurationFilesPath;
    private Path exportFilesPath;
    private Path logFilePath;

    @Inject
    private Logger LOG;

    @Inject
    private Event<StartCollectorEvent> configurationAddedEvent;
    @Inject
    private Event<StopCollectorEvent> configurationRemovedEvent;
    @Inject
    private Event<RestartCollectorEvent> configurationUpdatedEvent;

    private final Map<SystemConfigurationKey, String> systemConfigs;

    /** The configurations. */
    private final Map<String, Configuration> configurations;

    public ConfigurationServiceImpl() {
        systemConfigs = new ConcurrentHashMap<SystemConfigurationKey, String>();
        configurations = new ConcurrentHashMap<String, Configuration>();
        objectMapper = new ObjectMapper();
        try {
            JAR_FILE_LOCATION = Paths.get(ConfigurationServiceImpl.class.getProtectionDomain().getCodeSource().getLocation().toURI());
        } catch (URISyntaxException e) {
            LOG.warn("Cannot determine jar file location.", e);
        }
    }

    void init(@Observes @Initialized(ApplicationScoped.class) Object event) {
        LOG.debug("Starting");
    }

    @PostConstruct
    private void init() {
        loadSystemConfigurations();
        loadConfigurations();
    }

    private void loadSystemConfigurations() {
        Properties properties = null;
        try (InputStream is = getClass().getResourceAsStream("/system-configurations.properties")) {
            properties = new Properties();
            properties.load(is);

            String root = properties.getProperty(SystemConfigurationKey.ROOT_PATH.getValue());
            if ((root == null) || root.equals("")) {
                rootPath = JAR_FILE_LOCATION;
            } else {
                rootPath = Paths.get(root);
            }

            setProperties(properties.getProperty(SystemConfigurationKey.CONFIGURATIONS_PATH.getValue()), properties.getProperty(SystemConfigurationKey.EXPORT_PATH.getValue()), properties.getProperty(SystemConfigurationKey.EXPORT_FILE_EXTENSION.getValue()), properties.getProperty(SystemConfigurationKey.LOG_PATH.getValue()), properties.getProperty(SystemConfigurationKey.LOG_FILE_NAME.getValue()));
        } catch (IOException e) {
            LOG.warn("Cannot load system configs from file. Using defaults.");
            setProperties(null, null, null, null, null);
        }
    }

    private void setProperties(final String configurationsPath, final String exportPath, final String exportFileExtension, final String logPath, final String logFileName) {

        // CONFIGURATIONS_PATH
        if ((configurationsPath == null) || configurationsPath.equals("")) {
            systemConfigs.put(SystemConfigurationKey.CONFIGURATIONS_PATH, rootPath.resolve(Constants.DEFAULT_CONFIGURATIONS_PATH.getValue()).toString());
        } else {
            systemConfigs.put(SystemConfigurationKey.CONFIGURATIONS_PATH, configurationsPath);
        }
        this.configurationFilesPath = Paths.get(systemConfigs.get(SystemConfigurationKey.CONFIGURATIONS_PATH));
        try {
            Files.createDirectories(this.configurationFilesPath);
        } catch (IOException e) {
            LOG.error("Cannot create folder for metric configurations files ({" + this.configurationFilesPath + "}). " + e.getMessage());
        }

        // EXPORT_PATH
        if ((exportPath == null) || exportPath.equals("")) {
            systemConfigs.put(SystemConfigurationKey.EXPORT_PATH, rootPath.resolve(Constants.DEFAULT_EXPORT_DIR.getValue()).toString());
        } else {
            systemConfigs.put(SystemConfigurationKey.EXPORT_PATH, exportPath);
        }
        this.exportFilesPath = Paths.get(systemConfigs.get(SystemConfigurationKey.EXPORT_PATH));
        try {
            Files.createDirectories(this.exportFilesPath);
        } catch (IOException e) {
            LOG.error("Cannot create folder for export files ({" + this.exportFilesPath + "}). " + e.getMessage());
        }
        LOG.debug("Export files path: {}", this.exportFilesPath);
        System.setProperty(SystemConfigurationKey.EXPORT_PATH.getValue(), this.exportFilesPath.toString() + File.separatorChar);

        // EXPORT_FILE_EXTENSION
        String ext = exportFileExtension;
        if ((ext == null) || ext.equals("")) {
            ext = Constants.DEFAULT_EXPORT_FILE_EXTENSION.getValue();
        }
        systemConfigs.put(SystemConfigurationKey.EXPORT_FILE_EXTENSION, ext);
        // remove dot as first character, if any
        if (ext.charAt(0) == '.') {
            ext = ext.substring(1, ext.length());
        }
        LOG.debug("Export files extension: {}", ext);
        System.setProperty(SystemConfigurationKey.EXPORT_FILE_EXTENSION.getValue(), ext);

        // LOG_PATH
        if ((logPath == null) || logPath.equals("")) {
            systemConfigs.put(SystemConfigurationKey.LOG_PATH, rootPath.resolve(Constants.DEFAULT_LOG_DIR.getValue()).toString());
        } else {
            systemConfigs.put(SystemConfigurationKey.LOG_PATH, logPath);
        }
        this.logFilePath = Paths.get(systemConfigs.get(SystemConfigurationKey.LOG_PATH));
        try {
            Files.createDirectories(this.logFilePath);
        } catch (IOException e) {
            LOG.error("Cannot create folder for log file ({" + this.logFilePath + "}). " + e.getMessage());
        }

        // LOG_FILE_NAME
        if ((logFileName == null) || logFileName.equals("")) {
            systemConfigs.put(SystemConfigurationKey.LOG_FILE_NAME, Constants.DEFAULT_LOG_FILE_NAME.getValue());
        } else {
            systemConfigs.put(SystemConfigurationKey.LOG_FILE_NAME, logFileName);
        }
    }

    private void loadConfigurations() {
        Configuration configuration = null;
        try (DirectoryStream<Path> directoryStream = Files.newDirectoryStream(configurationFilesPath, CONFIGURATION_FILE_FILTER)) {
            for (Path configFilePath : directoryStream) {
                configuration = objectMapper.readValue(configFilePath.toFile(), Configuration.class);
                configurations.put(configuration.getId(), configuration);
                configurationAddedEvent.fire(new StartCollectorEvent(configuration));
                configuration = null;
            }
        } catch (Exception e) {
            e.printStackTrace();
            LOG.error("Error loading configurations.", e);
        }
    }

    /**
     * @see com.redhat.it.customers.dmc.core.services.configuration.ConfigurationService#getConfiguration(java.lang.String)
     */
    @Override
    public Configuration getConfiguration(String configurationId) {
        return configurations.get(configurationId);
    }

    /**
     * @see com.redhat.it.customers.dmc.core.services.configuration.ConfigurationService#getConfigurationNames()
     */
    @Override
    public Set<String> getConfigurationNames() {
        return Collections.unmodifiableSet(configurations.keySet());
    }

    /**
     * @see com.redhat.it.customers.dmc.core.services.configuration.ConfigurationService#getAllConfigurations()
     */
    @Override
    public Collection<Configuration> getAllConfigurations() {
        return Collections.unmodifiableCollection(configurations.values());
    }

    /**
     * @throws ConfigurationAlreadyExistsException
     * @throws ConfigurationStoreException
     * @see com.redhat.it.customers.dmc.core.services.configuration.ConfigurationService#addConfiguration(com.redhat.it.customers.dmc.core.dto.configuration.Configuration)
     */
    @Override
    public Configuration addConfiguration(Configuration configuration) throws ConfigurationAlreadyExistsException, ConfigurationStoreException {
        if (configurations.containsKey(configuration.getId())) {
            throw new ConfigurationAlreadyExistsException(configuration.getId());
        }
        configurations.put(configuration.getId(), configuration);
        storeConfigurationFS(configuration);
        configurationAddedEvent.fire(new StartCollectorEvent(configuration));
        return configuration;
    }

    /**
     * @throws ConfigurationNotFoundException
     * @throws ConfigurationStoreException
     * @see com.redhat.it.customers.dmc.core.services.configuration.ConfigurationService#updateConfiguration(com.redhat.it.customers.dmc.core.dto.configuration.Configuration)
     */
    @Override
    public Configuration updateConfiguration(Configuration configuration) throws ConfigurationException, ConfigurationStoreException {
        if (!configurations.containsKey(configuration.getId())) {
            throw new ConfigurationNotFoundException(configuration.getId());
        }
        storeConfigurationFS(configuration);
        configurationUpdatedEvent.fire(new RestartCollectorEvent(configuration));
        return configuration;
    }

    /**
     * @throws ConfigurationNotFoundException
     * @throws ConfigurationDeleteException
     * @see com.redhat.it.customers.dmc.core.services.configuration.ConfigurationService#removeConfiguration(java.lang.String)
     */
    @Override
    public Configuration removeConfiguration(String configurationId) throws ConfigurationNotFoundException, ConfigurationDeleteException {
        Configuration configuration = null;
        if ((configuration = configurations.remove(configurationId)) == null) {
            throw new ConfigurationNotFoundException(configurationId);
        }
        deleteConfigurationFS(configurationId);
        configurationRemovedEvent.fire(new StopCollectorEvent(configurationId));
        return configuration;
    }

    /**
     * Store configuration fs.
     *
     * @param configuration
     *            the configuration
     * @throws ConfigurationStoreException
     */
    private void storeConfigurationFS(Configuration configuration) throws ConfigurationStoreException {
        try (OutputStream os = Files.newOutputStream(configurationFilesPath.resolve(configuration.getId() + Constants.CONFIGURATION_FILE_EXTENSION.getValue()), StandardOpenOption.CREATE, StandardOpenOption.WRITE)) {
            objectMapper.writeValue(os, configuration);
        } catch (IOException e) {
            throw new ConfigurationStoreException(configuration.getId(), e);
        }
    }

    /**
     * Delete configuration fs.
     *
     * @param configurationId
     *            the configuration id
     * @throws ConfigurationDeleteException
     */
    private void deleteConfigurationFS(String configurationId) throws ConfigurationDeleteException {
        try {
            Files.deleteIfExists(configurationFilesPath.resolve(configurationId + Constants.CONFIGURATION_FILE_EXTENSION.getValue()));
        } catch (IOException e) {
            throw new ConfigurationDeleteException(configurationId, e);
        }
    }

    @Override
    public Path getRootPath() {
        return rootPath;
    }

    @Override
    public Path getConfigurationFilesPath() {
        return configurationFilesPath;
    }

    @Override
    public Path getExportFilesPath() {
        return exportFilesPath;
    }

    @Override
    public Path getLogFilePath() {
        return logFilePath;
    }

    @Override
    public String getExportFileExtension() {
        return systemConfigs.get(SystemConfigurationKey.EXPORT_FILE_EXTENSION);
    }
}
