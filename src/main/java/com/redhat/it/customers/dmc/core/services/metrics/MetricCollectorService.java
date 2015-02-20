/**
 * 
 */
package com.redhat.it.customers.dmc.core.services.metrics;

import java.util.Set;

import com.redhat.it.customers.dmc.core.constants.CollectorWorkerStatus;
import com.redhat.it.customers.dmc.core.exceptions.CollectorAlreadyExistsException;
import com.redhat.it.customers.dmc.core.exceptions.CollectorNotFoundException;
import com.redhat.it.customers.dmc.core.exceptions.CollectorStoppedException;
import com.redhat.it.customers.dmc.core.exceptions.ConfigurationNotFoundException;
import com.redhat.it.customers.dmc.core.exceptions.DMRException;

// TODO: Auto-generated Javadoc
/**
 * The Interface MetricCollectorService.
 *
 * @author Andrea Battaglia
 */
public interface MetricCollectorService {

    /**
     * Start collector.
     *
     * @param collectorName
     *            the collector name
     * @return true, if successful
     * @throws CollectorAlreadyExistsException
     *             the collector already exists exception
     * @throws DMRException
     *             the DMR exception
     * @throws ConfigurationNotFoundException
     *             the configuration not found exception
     */
    boolean startCollector(String collectorName)
            throws CollectorAlreadyExistsException, DMRException,
            ConfigurationNotFoundException;

    /**
     * Start all collectors.
     *
     * @return true, if successful
     */
//    boolean startAllCollectors();

    /**
     * Pause collector.
     *
     * @param collectorName
     *            the collector name
     * @return true, if successful
     * @throws CollectorNotFoundException
     *             the collector not found exception
     * @throws CollectorStoppedException 
     */
    boolean pauseCollector(String collectorName)
            throws CollectorNotFoundException, CollectorStoppedException;

    /**
     * Pause all collectors.
     */
//    void pauseAllCollectors();

    /**
     * Pause collector.
     *
     * @param collectorName
     *            the collector name
     * @return true, if successful
     * @throws CollectorNotFoundException
     *             the collector not found exception
     * @throws CollectorStoppedException 
     */
    boolean resumeCollector(String collectorName)
            throws CollectorNotFoundException, CollectorStoppedException;

    /**
     * Pause all collectors.
     */
//    void resumeAllCollectors();

    /**
     * Stop collector.
     *
     * @param collectorName
     *            the collector name
     * @return true, if successful
     * @throws CollectorNotFoundException
     *             the collector not found exception
     * @throws CollectorStoppedException 
     */
    boolean stopCollector(String collectorName)
            throws CollectorNotFoundException, CollectorStoppedException;

    /**
     * Stop all collectors.
     *
     * @return true, if successful
     */
//    boolean stopAllCollectors();

    /**
     * Fire collector.
     *
     * @param collectorName
     *            the collector name
     * @return the string
     * @throws CollectorNotFoundException
     *             the collector not found exception
     * @throws CollectorStoppedException
     *             the collector stopped exception
     */
    String fireCollector(String collectorName)
            throws CollectorNotFoundException, CollectorStoppedException;

    /**
     * Fire and forget collector.
     *
     * @param collectorName
     *            the collector name
     * @return the string
     * @throws CollectorNotFoundException
     *             the collector not found exception
     * @throws CollectorStoppedException 
     */
    void fireAndForgetCollector(String collectorName)
            throws CollectorNotFoundException, CollectorStoppedException;

    Set<String> getCollectorNames();

    Set<String> getCollectorNames(CollectorWorkerStatus status);

}
