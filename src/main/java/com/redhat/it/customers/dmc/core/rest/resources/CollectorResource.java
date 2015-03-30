package com.redhat.it.customers.dmc.core.rest.resources;

import java.util.Arrays;
import java.util.LinkedHashSet;
import java.util.Set;

import javax.inject.Inject;
import javax.ws.rs.DefaultValue;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import org.slf4j.Logger;

import com.redhat.it.customers.dmc.core.constants.CollectorWorkerStatus;
import com.redhat.it.customers.dmc.core.constants.CollectorWorkerStatusAction;
import com.redhat.it.customers.dmc.core.constants.Constants;
import com.redhat.it.customers.dmc.core.dto.collector.CollectorActionResult;
import com.redhat.it.customers.dmc.core.exceptions.CollectorAlreadyExistsException;
import com.redhat.it.customers.dmc.core.exceptions.CollectorNotFoundException;
import com.redhat.it.customers.dmc.core.exceptions.CollectorStoppedException;
import com.redhat.it.customers.dmc.core.exceptions.ConfigurationNotFoundException;
import com.redhat.it.customers.dmc.core.exceptions.DMRException;
import com.redhat.it.customers.dmc.core.services.collector.MetricCollectorService;

/**
 * The Class AdminService.
 * 
 * @author Andrea Battaglia
 */
@Path("/collector")
public class CollectorResource {

    // private final static String[] EMPTY_STRING_ARRAY = new String[0];

    /** The log. */
    @Inject
    private Logger LOG;

    /** The metric collector service. */
    @Inject
    private MetricCollectorService metricCollectorService;

    /**
     * Update collector status.
     *
     * @param action
     *            the action
     * @param collectors
     *            the collectors
     * @return the string
     * @throws CollectorAlreadyExistsException
     *             the collector already exists exception
     * @throws DMRException
     *             the DMR exception
     * @throws ConfigurationNotFoundException
     *             the configuration not found exception
     * @throws CollectorNotFoundException
     *             the collector not found exception
     * @throws CollectorStoppedException
     *             the collector stopped exception
     */
    @POST
    @Path("")
    @Produces(MediaType.APPLICATION_JSON)
    public CollectorActionResult updateCollectorStatus(
            @QueryParam("action") CollectorWorkerStatusAction action,
            @QueryParam("collectors") String collectors)
            throws CollectorAlreadyExistsException, DMRException,
            ConfigurationNotFoundException, CollectorNotFoundException,
            CollectorStoppedException {
        switch (action) {
        case START:
            return start(collectors);
        case PAUSE:
            return pause(collectors);
        case RESUME:
            return resume(collectors);
        case STOP:
            return stop(collectors);

        default:
            throw new IllegalArgumentException("Invalid action \"" + action
                    + "\"");
        }
    }

    /**
     * Start.
     *
     * @param collectors
     *            the collectors
     * @return the string
     * @throws CollectorAlreadyExistsException
     *             the collector already exists exception
     * @throws DMRException
     *             the DMR exception
     * @throws ConfigurationNotFoundException
     *             the configuration not found exception
     */
    private CollectorActionResult start(String collectors) {
        Set<String> collectorNames = null;
        CollectorActionResult result = null;
        if (collectors == null) {
            collectorNames = metricCollectorService
                    .getCollectorNames(CollectorWorkerStatus.STOPPED);
        } else {
            collectorNames = new LinkedHashSet<String>(Arrays.asList(collectors
                    .split(Constants.DEFAULT_SEPARATOR.getValue())));
        }
        LOG.info("Starting collectors {}", collectorNames);
        result = new CollectorActionResult();
        result.setAction(CollectorWorkerStatusAction.START);
        for (String collectorName : collectorNames) {
            try {
                metricCollectorService.startCollector(collectorName);
                result.addSuccess(collectorName);
            } catch (CollectorAlreadyExistsException
                    | ConfigurationNotFoundException | DMRException e) {
                result.putFail(collectorName, e.getMessage());
            }
        }
        collectorNames = null;
        return result;
    }

    /***
     * Pause.
     *
     * @param collectors
     *            the collectors
     * @return the string
     * @throws CollectorNotFoundException
     *             the collector not found exception
     * @throws CollectorStoppedException
     *             the collector stopped exception
     */
    private CollectorActionResult pause(String collectors) {
        Set<String> collectorNames = null;
        CollectorActionResult result = null;
        if (collectors == null) {
            collectorNames = metricCollectorService
                    .getCollectorNames(CollectorWorkerStatus.RUNNING);
        } else {
            collectorNames = new LinkedHashSet<String>(Arrays.asList(collectors
                    .split(Constants.DEFAULT_SEPARATOR.getValue())));
        }
        LOG.info("Pausing collectors {}", collectorNames);
        result = new CollectorActionResult();
        result.setAction(CollectorWorkerStatusAction.PAUSE);
        for (String collectorName : collectorNames) {
            try {
                metricCollectorService.resumeCollector(collectorName);
                result.addSuccess(collectorName);
            } catch (CollectorNotFoundException | CollectorStoppedException e) {
                result.putFail(collectorName, e.getMessage());
            }
        }
        collectorNames = null;
        return result;
    }

    /***
     * Resume.
     *
     * @param collectors
     *            the collectors
     * @return the string
     * @throws CollectorNotFoundException
     *             the collector not found exception
     * @throws CollectorStoppedException
     *             the collector stopped exception
     */
    private CollectorActionResult resume(String collectors) {
        Set<String> collectorNames = null;
        CollectorActionResult result = null;
        if (collectors == null) {
            collectorNames = metricCollectorService
                    .getCollectorNames(CollectorWorkerStatus.PAUSED);
        } else {
            collectorNames = new LinkedHashSet<String>(Arrays.asList(collectors
                    .split(Constants.DEFAULT_SEPARATOR.getValue())));
        }
        LOG.info("Resuming collectors {}", collectorNames);
        result = new CollectorActionResult();
        result.setAction(CollectorWorkerStatusAction.RESUME);
        for (String collectorName : collectorNames) {
            try {
                metricCollectorService.resumeCollector(collectorName);
                result.addSuccess(collectorName);
            } catch (CollectorNotFoundException | CollectorStoppedException e) {
                result.putFail(collectorName, e.getMessage());
            }
        }
        collectorNames = null;
        return result;
    }

    /***
     * Stop.
     *
     * @param collectors
     *            the collectors
     * @return the string
     * @throws CollectorNotFoundException
     *             the collector not found exception
     * @throws CollectorStoppedException
     *             the collector stopped exception
     */
    private CollectorActionResult stop(String collectors)
            throws CollectorNotFoundException, CollectorStoppedException {
        Set<String> collectorNames = null;
        CollectorActionResult result = null;
        if (collectors == null) {
            collectorNames = metricCollectorService
                    .getCollectorNames();
        } else {
            collectorNames = new LinkedHashSet<String>(Arrays.asList(collectors
                    .split(Constants.DEFAULT_SEPARATOR.getValue())));
        }
        LOG.info("Stopping collectors {}", collectorNames);
        result = new CollectorActionResult();
        result.setAction(CollectorWorkerStatusAction.STOP);
        for (String collectorName : collectorNames) {
            try {
                metricCollectorService.stopCollector(collectorName);
                result.addSuccess(collectorName);
            } catch (CollectorNotFoundException | CollectorStoppedException e) {
                result.putFail(collectorName, e.getMessage());
            }
        }
        collectorNames = null;
        return result;
    }

    /**
     * Fire collector.
     *
     * @param collectorName
     *            the collector name
     * @param forget
     *            the forget
     * @return the string
     * @throws CollectorNotFoundException
     *             the collector not found exception
     * @throws CollectorStoppedException
     *             the collector stopped exception
     */
    @GET
    @Path("")
    public String fireCollector(
            @QueryParam("collectorName") String collectorName,
            @QueryParam("forget") @DefaultValue("false") boolean forget)
            throws CollectorNotFoundException, CollectorStoppedException {
        String result = null;
        if (forget) {
            metricCollectorService.fireAndForgetCollector(collectorName);
            result = "";
        } else {
            result = metricCollectorService.fireCollector(collectorName);
        }
        return result;
    }
}
