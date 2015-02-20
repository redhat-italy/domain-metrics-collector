package com.redhat.it.customers.dmc.core.services.metrics;

import com.redhat.it.customers.dmc.core.constants.CollectorWorkerStatus;
import com.redhat.it.customers.dmc.core.dto.configuration.Configuration;
import com.redhat.it.customers.dmc.core.services.connection.QueryExecutor;

/**
 * The Interface CollectorWorker.
 * 
 * @author Andrea Battaglia
 */
public interface CollectorWorker extends Runnable {

    /**
     * Gets the id.
     *
     * @return the id
     */
    String getId();

    /**
     * Sets the id.
     *
     * @param configuration
     *            the new id
     */
    void setConfiguration(Configuration configuration);

    /**
     * Start.
     *
     * @return true, if successful
     */
    boolean start();

    /**
     * Pause.
     *
     * @return true, if successful
     */
    boolean pause();

    /**
     * Stop.
     *
     * @return true, if successful
     */
    boolean stop();

    /**
     * Fire.
     *
     * @return the string
     */
    String fire();

    /**
     * Fire and forget.
     */
    void fireAndForget();

    /**
     * Sets the DMR connection.
     *
     * @param queryExecutor the new query executor
     */
    void setQueryExecutor(QueryExecutor queryExecutor);

    /**
     * Gets the status.
     *
     * @return the status
     */
    CollectorWorkerStatus getStatus();

    /**
     * Resume.
     *
     * @return true, if successful
     */
    boolean resume();

    /**
     * Checks if is started.
     *
     * @return true, if is started
     */
    boolean isStarted();

    /**
     * Checks if is paused.
     *
     * @return true, if is paused
     */
    boolean isPaused();

    /**
     * Checks if is stopped.
     *
     * @return true, if is stopped
     */
    boolean isStopped();
}