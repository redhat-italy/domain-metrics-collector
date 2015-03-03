/**
 * 
 */
package com.redhat.it.customers.dmc.core.services.configuration.impl;

import static org.junit.Assert.assertEquals;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.Set;

import org.codehaus.jackson.map.ObjectMapper;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import com.redhat.it.customers.dmc.core.constants.Constants;
import com.redhat.it.customers.dmc.core.dto.configuration.AppConfiguration;
import com.redhat.it.customers.dmc.core.dto.configuration.AppObjectAttributeConfiguration;
import com.redhat.it.customers.dmc.core.dto.configuration.Configuration;
import com.redhat.it.customers.dmc.core.dto.configuration.InstanceConfiguration;
import com.redhat.it.customers.dmc.core.dto.configuration.JvmConfiguration;

// TODO: Auto-generated Javadoc
/**
 * The Class ConfigurationServiceImplTest.
 *
 * @author Andrea Battaglia
 */
public class ConfigurationServiceImplTest {

    /** The Constant objectMapper. */
    private static final ObjectMapper objectMapper = new ObjectMapper();

    /** The configuration files path. */
    private static Path configurationFilesPath;

    /** The app object attribute configurations. */
    private static LinkedHashMap<Integer, AppObjectAttributeConfiguration> appObjectAttributeConfigurations;

    /** The Constant apps. */
    private final static Set<String> apps = new LinkedHashSet<String>();

    static {
        apps.add("test-enterprise.ear");
        apps.add("test-pippo.ear");

        appObjectAttributeConfigurations = new LinkedHashMap<Integer, AppObjectAttributeConfiguration>();
        AppObjectAttributeConfiguration value = new AppObjectAttributeConfiguration();
        value.setAppObjectAttributeExclude(false);
        LinkedHashSet<String> appObjectAttributes = new LinkedHashSet<String>();
        appObjectAttributes.add("methods");
        value.setAppObjectAttributes(appObjectAttributes);
        appObjectAttributeConfigurations.put(0, value);
        appObjectAttributeConfigurations.put(1, null);
    }

    /**
     * Sets the up before class.
     *
     * @throws Exception
     *             the exception
     */
    @BeforeClass
    public static void setUpBeforeClass() throws Exception {
        configurationFilesPath = Paths.get("/dmc/test/configs");
        Files.createDirectories(configurationFilesPath);
        Files.deleteIfExists(configurationFilesPath.resolve("testAppConfiguration.json"));
        Files.deleteIfExists(configurationFilesPath.resolve("testInstanceConfiguration.json"));
        Files.deleteIfExists(configurationFilesPath.resolve("testJvmConfiguration.json"));
    }

    /**
     * Tear down after class.
     *
     * @throws Exception
     *             the exception
     */
    @AfterClass
    public static void tearDownAfterClass() throws Exception {
    }

    /**
     * Sets the up.
     *
     * @throws Exception
     *             the exception
     */
    @Before
    public void setUp() throws Exception {
    }

    /**
     * Tear down.
     *
     * @throws Exception
     *             the exception
     */
    @After
    public void tearDown() throws Exception {
    }

    /**
     * Write app configuration to file system.
     */
    @Test
    public void writeAppConfigurationToFileSystem() {
        AppConfiguration appConfiguration = new AppConfiguration();
        appConfiguration.setId("testAppConfiguration");
        appConfiguration.setDepth(1);
        appConfiguration.setApps(apps);
        appConfiguration
                .setAppObjectAttributeConfigurations(appObjectAttributeConfigurations);
        try (OutputStream os = Files.newOutputStream(
                configurationFilesPath.resolve(appConfiguration.getId()
                        + Constants.CONFIGURATION_FILE_EXTENSION.getValue()),
                StandardOpenOption.CREATE, StandardOpenOption.WRITE)) {
            objectMapper.writeValue(os, appConfiguration);
        } catch (IOException e) {
            e.printStackTrace();
        }
        //
        Configuration configuration = null;
        try (InputStream is = Files.newInputStream(configurationFilesPath
                .resolve("testAppConfiguration"
                        + Constants.CONFIGURATION_FILE_EXTENSION.getValue()))) {
            configuration = objectMapper.readValue(is, Configuration.class);
        } catch (IOException e) {
            e.printStackTrace();
        }
        assertEquals(AppConfiguration.class, configuration.getClass());
        assertEquals("testAppConfiguration", configuration.getId());
    }

    /**
     * Write instance configuration to file system.
     */
    @Test
    public void writeInstanceConfigurationToFileSystem() {
        Configuration configuration = new InstanceConfiguration();
        configuration.setId("testInstanceConfiguration");
        try (OutputStream os = Files.newOutputStream(
                configurationFilesPath.resolve(configuration.getId()
                        + Constants.CONFIGURATION_FILE_EXTENSION.getValue()),
                StandardOpenOption.CREATE, StandardOpenOption.WRITE)) {
            objectMapper.writeValue(os, configuration);
        } catch (IOException e) {
            e.printStackTrace();
        }
        //
        configuration = null;
        try (InputStream is = Files.newInputStream(configurationFilesPath
                .resolve("testInstanceConfiguration"
                        + Constants.CONFIGURATION_FILE_EXTENSION.getValue()))) {
            configuration = objectMapper.readValue(is, Configuration.class);
        } catch (IOException e) {
            e.printStackTrace();
        }
        assertEquals(InstanceConfiguration.class, configuration.getClass());
        assertEquals("testInstanceConfiguration", configuration.getId());
    }

    /**
     * Write jvm configuration to file system.
     */
    @Test
    public void writeJvmConfigurationToFileSystem() {
        Configuration configuration = new JvmConfiguration();
        configuration.setId("testJvmConfiguration");
        try (OutputStream os = Files.newOutputStream(
                configurationFilesPath.resolve(configuration.getId()
                        + Constants.CONFIGURATION_FILE_EXTENSION.getValue()),
                StandardOpenOption.CREATE, StandardOpenOption.WRITE)) {
            objectMapper.writeValue(os, configuration);
        } catch (IOException e) {
            e.printStackTrace();
        }
        //
        configuration = null;
        try (InputStream is = Files.newInputStream(configurationFilesPath
                .resolve("testJvmConfiguration"
                        + Constants.CONFIGURATION_FILE_EXTENSION.getValue()))) {
            configuration = objectMapper.readValue(is, Configuration.class);
        } catch (IOException e) {
            e.printStackTrace();
        }
        assertEquals(JvmConfiguration.class, configuration.getClass());
        assertEquals("testJvmConfiguration", configuration.getId());
    }
}
