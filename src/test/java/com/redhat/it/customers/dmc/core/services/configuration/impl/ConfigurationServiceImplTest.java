/**
 * 
 */
package com.redhat.it.customers.dmc.core.services.configuration.impl;

import static org.junit.Assert.*;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
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
import com.redhat.it.customers.dmc.core.dto.configuration.Configuration;
import com.redhat.it.customers.dmc.core.dto.configuration.InstanceConfiguration;
import com.redhat.it.customers.dmc.core.dto.configuration.JvmConfiguration;

/**
 * @author Andrea Battaglia
 *
 */
public class ConfigurationServiceImplTest {

    private static final ObjectMapper objectMapper = new ObjectMapper();
    private static Path configurationFilesPath;
    
    private final static Set<String> apps=new LinkedHashSet<String>();
    
    static{
        apps.add("test-enterprise.ear");
        apps.add("test-pippo.ear");
    }

    /**
     * @throws java.lang.Exception
     */
    @BeforeClass
    public static void setUpBeforeClass() throws Exception {
        configurationFilesPath = Paths.get("c:/dmc/test/configs");
        Files.createDirectories(configurationFilesPath);
    }

    /**
     * @throws java.lang.Exception
     */
    @AfterClass
    public static void tearDownAfterClass() throws Exception {
    }

    /**
     * @throws java.lang.Exception
     */
    @Before
    public void setUp() throws Exception {
    }

    /**
     * @throws java.lang.Exception
     */
    @After
    public void tearDown() throws Exception {
    }

    // /**
    // * Test method for
    // * {@link
    // com.redhat.it.customers.dmc.core.services.configuration.impl.ConfigurationServiceImpl#getConfiguration(java.lang.String)}
    // * .
    // */
    // @Test
    // public void testGetConfiguration() {
    // fail("Not yet implemented");
    // }
    //
    // /**
    // * Test method for
    // * {@link
    // com.redhat.it.customers.dmc.core.services.configuration.impl.ConfigurationServiceImpl#getAllConfigurations()}
    // * .
    // */
    // @Test
    // public void testGetAllConfigurations() {
    // fail("Not yet implemented");
    // }
    //
    // /**
    // * Test method for
    // * {@link
    // com.redhat.it.customers.dmc.core.services.configuration.impl.ConfigurationServiceImpl#addConfiguration(com.redhat.it.customers.dmc.core.dto.connection.Configuration)}
    // * .
    // */
    // @Test
    // public void testAddConfiguration() {
    // fail("Not yet implemented");
    // }
    //
    // /**
    // * Test method for
    // * {@link
    // com.redhat.it.customers.dmc.core.services.configuration.impl.ConfigurationServiceImpl#updateConfiguration(com.redhat.it.customers.dmc.core.dto.connection.Configuration)}
    // * .
    // */
    // @Test
    // public void testUpdateConfiguration() {
    // fail("Not yet implemented");
    // }
    //
    // /**
    // * Test method for
    // * {@link
    // com.redhat.it.customers.dmc.core.services.configuration.impl.ConfigurationServiceImpl#removeConfiguration(java.lang.String)}
    // * .
    // */
    // @Test
    // public void testRemoveConfiguration() {
    // fail("Not yet implemented");
    // }

    @Test
    public void writeAppConfigurationToFileSystem() {
        AppConfiguration appConfiguration = new AppConfiguration();
        appConfiguration.setId("testAppConfiguration");
        appConfiguration.setApps(apps);
        try (OutputStream os = Files.newOutputStream(
                configurationFilesPath.resolve(appConfiguration.getId()+Constants.CONFIGURATION_FILE_EXTENSION.getValue()),
                StandardOpenOption.CREATE, StandardOpenOption.WRITE)) {
            objectMapper.writeValue(os, appConfiguration);
        } catch (IOException e) {
            e.printStackTrace();
        }
        //
        Configuration configuration = null;
        try (InputStream is = Files.newInputStream(configurationFilesPath
                .resolve("testAppConfiguration"+Constants.CONFIGURATION_FILE_EXTENSION.getValue()))) {
            configuration = objectMapper.readValue(is, Configuration.class);
        } catch (IOException e) {
            e.printStackTrace();
        }
        assertEquals(AppConfiguration.class, configuration.getClass());
        assertEquals("testAppConfiguration", configuration.getId());
    }

    @Test
    public void writeInstanceConfigurationToFileSystem() {
        Configuration configuration = new InstanceConfiguration();
        configuration.setId("testInstanceConfiguration");
        try (OutputStream os = Files.newOutputStream(
                configurationFilesPath.resolve(configuration.getId()+Constants.CONFIGURATION_FILE_EXTENSION.getValue()),
                StandardOpenOption.CREATE, StandardOpenOption.WRITE)) {
            objectMapper.writeValue(os, configuration);
        } catch (IOException e) {
            e.printStackTrace();
        }
        //
        configuration = null;
        try (InputStream is = Files.newInputStream(configurationFilesPath
                .resolve("testInstanceConfiguration"+Constants.CONFIGURATION_FILE_EXTENSION.getValue()))) {
            configuration = objectMapper.readValue(is, Configuration.class);
        } catch (IOException e) {
            e.printStackTrace();
        }
        assertEquals(InstanceConfiguration.class, configuration.getClass());
        assertEquals("testInstanceConfiguration", configuration.getId());
    }

    @Test
    public void writeJvmConfigurationToFileSystem() {
        Configuration configuration = new JvmConfiguration();
        configuration.setId("testJvmConfiguration");
        try (OutputStream os = Files.newOutputStream(
                configurationFilesPath.resolve(configuration.getId()+Constants.CONFIGURATION_FILE_EXTENSION.getValue()),
                StandardOpenOption.CREATE, StandardOpenOption.WRITE)) {
            objectMapper.writeValue(os, configuration);
        } catch (IOException e) {
            e.printStackTrace();
        }
        //
        configuration = null;
        try (InputStream is = Files.newInputStream(configurationFilesPath
                .resolve("testJvmConfiguration"+Constants.CONFIGURATION_FILE_EXTENSION.getValue()))) {
            configuration = objectMapper.readValue(is, Configuration.class);
        } catch (IOException e) {
            e.printStackTrace();
        }
        assertEquals(JvmConfiguration.class, configuration.getClass());
        assertEquals("testJvmConfiguration", configuration.getId());
    }
}
