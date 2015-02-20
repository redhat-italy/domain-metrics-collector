package com.redhat.it.customers.dmc.core.services.configuration;

import java.nio.file.Path;
import java.util.Collection;
import java.util.Set;

import com.redhat.it.customers.dmc.core.dto.configuration.Configuration;
import com.redhat.it.customers.dmc.core.exceptions.ConfigurationAlreadyExistsException;
import com.redhat.it.customers.dmc.core.exceptions.ConfigurationDeleteException;
import com.redhat.it.customers.dmc.core.exceptions.ConfigurationException;
import com.redhat.it.customers.dmc.core.exceptions.ConfigurationNotFoundException;
import com.redhat.it.customers.dmc.core.exceptions.ConfigurationStoreException;

// TODO: Auto-generated Javadoc
/**
 * The Interface ConnectionManagerService.
 * 
 * @author Andrea Battaglia
 */
public interface ConfigurationService {

    /**
     * Gets the configuration.
     *
     * @param configurationId
     *            the configuration id
     * @return the configuration
     */
    Configuration getConfiguration(String configurationId);

    /**
     * Gets the all configurations.
     *
     * @return the all configurations
     */
    Collection<Configuration> getAllConfigurations();

    /**
     * Adds the configuration.
     *
     * @param configuration
     *            the configuration
     * @throws ConfigurationAlreadyExistsException
     *             the configuration already exists exception
     * @throws ConfigurationStoreException
     *             the configuration store exception
     */
    Configuration addConfiguration(Configuration configuration)
            throws ConfigurationAlreadyExistsException,
            ConfigurationStoreException;

    /**
     * Reload connection parameters.
     *
     * @param configuration
     *            the configuration
     * @throws ConfigurationException
     *             the configuration exception
     * @throws ConfigurationStoreException
     *             the configuration store exception
     */
    Configuration updateConfiguration(Configuration configuration)
            throws ConfigurationException, ConfigurationStoreException;

    /**
     * Removes the configuration.
     *
     * @param configurationId
     *            the configuration id
     * @throws ConfigurationNotFoundException
     *             the configuration not found exception
     * @throws ConfigurationDeleteException
     *             the configuration delete exception
     */
    Configuration removeConfiguration(String configurationId)
            throws ConfigurationNotFoundException, ConfigurationDeleteException;

    Path getRootPath();

    Path getConfigurationFilesPath();

    Path getExportFilesPath();

    Path getLogFilePath();

    String getExportFileExtension();

    Set<String> getConfigurationNames();
}
