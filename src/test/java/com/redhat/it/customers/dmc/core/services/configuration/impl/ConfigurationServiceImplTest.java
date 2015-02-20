/**
 * 
 */
package com.redhat.it.customers.dmc.core.services.configuration.impl;

import static org.junit.Assert.fail;

import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;

import org.codehaus.jackson.map.ObjectMapper;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import com.redhat.it.customers.dmc.core.constants.Constants;
import com.redhat.it.customers.dmc.core.dto.configuration.Configuration;
import com.redhat.it.customers.dmc.core.exceptions.ConfigurationStoreException;

/**
 * @author Andrea Battaglia
 *
 */
public class ConfigurationServiceImplTest {

    private static final ObjectMapper objectMapper = new ObjectMapper();
    private static Path configurationFilesPath;

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

//    /**
//     * Test method for
//     * {@link com.redhat.it.customers.dmc.core.services.configuration.impl.ConfigurationServiceImpl#getConfiguration(java.lang.String)}
//     * .
//     */
//    @Test
//    public void testGetConfiguration() {
//        fail("Not yet implemented");
//    }
//
//    /**
//     * Test method for
//     * {@link com.redhat.it.customers.dmc.core.services.configuration.impl.ConfigurationServiceImpl#getAllConfigurations()}
//     * .
//     */
//    @Test
//    public void testGetAllConfigurations() {
//        fail("Not yet implemented");
//    }
//
//    /**
//     * Test method for
//     * {@link com.redhat.it.customers.dmc.core.services.configuration.impl.ConfigurationServiceImpl#addConfiguration(com.redhat.it.customers.dmc.core.dto.connection.Configuration)}
//     * .
//     */
//    @Test
//    public void testAddConfiguration() {
//        fail("Not yet implemented");
//    }
//
//    /**
//     * Test method for
//     * {@link com.redhat.it.customers.dmc.core.services.configuration.impl.ConfigurationServiceImpl#updateConfiguration(com.redhat.it.customers.dmc.core.dto.connection.Configuration)}
//     * .
//     */
//    @Test
//    public void testUpdateConfiguration() {
//        fail("Not yet implemented");
//    }
//
//    /**
//     * Test method for
//     * {@link com.redhat.it.customers.dmc.core.services.configuration.impl.ConfigurationServiceImpl#removeConfiguration(java.lang.String)}
//     * .
//     */
//    @Test
//    public void testRemoveConfiguration() {
//        fail("Not yet implemented");
//    }

    @Test
    public void writeConfigurationToFileSystem() {
        Configuration configuration = new Configuration();
        configuration.setId("testConfiguration");
        try (OutputStream os = Files.newOutputStream(
                configurationFilesPath.resolve(configuration.getId()),
                StandardOpenOption.CREATE,StandardOpenOption.WRITE)) {
            objectMapper.writeValue(os, configuration);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
