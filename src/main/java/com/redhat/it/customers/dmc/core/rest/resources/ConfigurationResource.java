package com.redhat.it.customers.dmc.core.rest.resources;

import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import org.slf4j.Logger;

import com.redhat.it.customers.dmc.core.dto.configuration.Configuration;
import com.redhat.it.customers.dmc.core.exceptions.ConfigurationAlreadyExistsException;
import com.redhat.it.customers.dmc.core.exceptions.ConfigurationDeleteException;
import com.redhat.it.customers.dmc.core.exceptions.ConfigurationException;
import com.redhat.it.customers.dmc.core.exceptions.ConfigurationNotFoundException;
import com.redhat.it.customers.dmc.core.exceptions.ConfigurationStoreException;
import com.redhat.it.customers.dmc.core.exceptions.InvalidConfigurationIdException;
import com.redhat.it.customers.dmc.core.services.configuration.ConfigurationService;

/**
 * The Class ConfigurationResource.
 * 
 * @author Andrea Battaglia
 */
@Path("/configuration")
public class ConfigurationResource {

    /** The log. */
    @Inject
    private Logger LOG;

    /** The metric collector service. */
    @Inject
    private ConfigurationService configurationService;

    /**
     * Adds the configuration.
     *
     * @return the string
     * @throws ConfigurationStoreException
     * @throws ConfigurationAlreadyExistsException
     * @throws InvalidConfigurationIdException
     */
    @PUT
    @Path("")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Configuration addConfiguration(
            @QueryParam("configuration") Configuration configuration
    // @QueryParam("configurationId") String configurationId,
    // @QueryParam("hostname") String hostname,
    // @QueryParam("port") Integer port,
    // @QueryParam("username") String username,
    // @QueryParam("password") String password,
    // @QueryParam("realm") String realm,
    // @QueryParam("regexpHostname") String regexpHostname,
    // @QueryParam("regexpServer") String regexpServer,
    // @QueryParam("apps") String apps,
    // @QueryParam("scanInterval") Integer scanInterval,
    // @QueryParam("start") @DefaultValue("false") boolean start
    ) throws ConfigurationAlreadyExistsException, ConfigurationStoreException,
            InvalidConfigurationIdException {
        // if ((configurationId == null) || configurationId.equals("")) {
        // throw new InvalidConfigurationIdException(configurationId);
        // }
        // Configuration configuration = adaptRawDataToConfigurationObject(
        // configurationId, hostname, port, username, password, realm,
        // regexpHostname, regexpServer, apps, scanInterval, start);
        Configuration newConfiguration = configurationService
                .addConfiguration(configuration);
        LOG.info(
                "Configuration {} added successfully. The full configuration is \n{}",
                newConfiguration.getId(), newConfiguration);
        return newConfiguration;
    }

    /**
     * Update configuration.
     *
     * @return the string
     * @throws ConfigurationException
     * @throws ConfigurationStoreException
     */
    @POST
    @Path("")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Configuration updateConfiguration(
    // @HeaderParam("configurationId") String configurationId,
    // @PathParam("parameters") PathSegment parameters
            @QueryParam("newConfiguration") Configuration newConfiguration)
            throws ConfigurationStoreException, ConfigurationException {

        // String path = parameters.getPath();
        // MultivaluedMap<String, String> mvm =
        // parameters.getMatrixParameters();
        // LOG.info("entries to update: {}", mvm);
        String configurationId = newConfiguration.getId();
        if ((newConfiguration.getId() == null)
                || newConfiguration.getId().equals("")) {
            throw new InvalidConfigurationIdException(configurationId);
        }
        Configuration oldConfiguration = configurationService
                .updateConfiguration(newConfiguration);
        LOG.info(
                "Configuration {} updated successfully.\nThe new configuration is {}, the old one is {}",
                newConfiguration.getId(), newConfiguration, oldConfiguration);
        return oldConfiguration;
    }

    /**
     * Removes the configuration.
     *
     * @return the string
     * @throws ConfigurationDeleteException
     * @throws ConfigurationNotFoundException
     * @throws InvalidConfigurationIdException
     */
    @DELETE
    @Path("")
    @Produces(MediaType.APPLICATION_JSON)
    public Configuration removeConfiguration(
            @QueryParam("configurationId") final String configurationId)
            throws ConfigurationNotFoundException,
            ConfigurationDeleteException, InvalidConfigurationIdException {
        if ((configurationId == null) || configurationId.equals("")) {
            throw new InvalidConfigurationIdException(configurationId);
        }
        Configuration configuration = configurationService
                .removeConfiguration(configurationId);
        LOG.info(
                "Configuration {} removed successfully. The full removed configuration is \n{}",
                configurationId, configuration);
        return configuration;
    }

    // private Configuration adaptRawDataToConfigurationObject(
    // String configurationId, String hostname, Integer port,
    // String username, String password, String realm,
    // String regexpHostname, String regexpServer, String apps,
    // Integer scanInterval, boolean start) {
    // Configuration configuration = ConfigurationBuilderFactory
    // .configuration().withId(configurationId).withHostname(hostname)
    // .withPort(port).withUsername(username).withPassword(password)
    // .withRealm(realm).withRegexpHostname(regexpHostname)
    // .withRegexpServer(regexpServer).withApps(apps)
    // .withScanInterval(scanInterval).withStart(start).build();
    // return configuration;
    // }

}
