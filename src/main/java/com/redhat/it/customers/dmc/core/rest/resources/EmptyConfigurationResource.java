package com.redhat.it.customers.dmc.core.rest.resources;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import org.slf4j.Logger;

import com.redhat.it.customers.dmc.core.constants.MetricType;
import com.redhat.it.customers.dmc.core.dto.configuration.AppConfiguration;
import com.redhat.it.customers.dmc.core.dto.configuration.Configuration;
import com.redhat.it.customers.dmc.core.dto.configuration.InstanceConfiguration;
import com.redhat.it.customers.dmc.core.dto.configuration.JvmConfiguration;
import com.redhat.it.customers.dmc.core.exceptions.ConfigurationAlreadyExistsException;
import com.redhat.it.customers.dmc.core.exceptions.ConfigurationStoreException;
import com.redhat.it.customers.dmc.core.exceptions.InvalidConfigurationIdException;
import com.redhat.it.customers.dmc.core.services.configuration.ConfigurationService;

/**
 * The Class ConfigurationResource.
 * 
 * @author Andrea Battaglia
 */
@Path("/emptyConfiguration")
public class EmptyConfigurationResource {

    /** The log. */
    @Inject
    private Logger LOG;

    /**
     * Gets the empty configuration.
     *
     * @return the empty configuration
     */
    @GET
    @Path("")
    @Produces(MediaType.APPLICATION_JSON)
    public Configuration getEmptyConfiguration(
            @QueryParam("metricType") MetricType metricType) {
        Configuration configuration = null;
        switch (metricType) {
        case APP:
            configuration = new AppConfiguration();
            break;
        case INSTANCE:
            configuration = new InstanceConfiguration();
            break;
        case JVM:
            configuration = new JvmConfiguration();
            break;
        }
        return configuration;
    }

}
