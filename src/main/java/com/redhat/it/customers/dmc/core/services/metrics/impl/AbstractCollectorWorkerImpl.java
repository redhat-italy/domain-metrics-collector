package com.redhat.it.customers.dmc.core.services.metrics.impl;

import static com.redhat.it.customers.dmc.core.constants.CollectorWorkerStatus.PAUSED;
import static com.redhat.it.customers.dmc.core.constants.CollectorWorkerStatus.RUNNING;
import static com.redhat.it.customers.dmc.core.constants.CollectorWorkerStatus.STOPPED;

import java.io.IOException;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicReference;

import javax.enterprise.event.Event;
import javax.inject.Inject;

import org.slf4j.Logger;
import org.slf4j.MDC;

import com.redhat.it.customers.dmc.core.constants.CollectorWorkerStatus;
import com.redhat.it.customers.dmc.core.constants.Constants;
import com.redhat.it.customers.dmc.core.dto.configuration.Configuration;
import com.redhat.it.customers.dmc.core.services.connection.QueryExecutor;
import com.redhat.it.customers.dmc.core.services.export.DataExporter;
import com.redhat.it.customers.dmc.core.services.metrics.CollectorWorker;

/**
 * The Class CollectorWorkerImpl.
 *
 * @author Andrea Battaglia
 * @param <C>
 *            the generic type
 */
public abstract class AbstractCollectorWorkerImpl<C extends Configuration>
        implements CollectorWorker<C> {

    /** The uuid. */
    private final String uuid = UUID.randomUUID().toString();

    /** The id. */
    protected String id;

    @Inject
    private Event<DestroyCollectorInstanceEvent> destroyCollectorInstanceEvent;

    /** The configuration. */
    protected C configuration;

    /** The scan interval. */
    protected long scanInterval;

    /** The status. */
    private final AtomicReference<CollectorWorkerStatus> status;

    /** The log. */
    @Inject
    private Logger LOG;

    /** The data exporter. */
    @Inject
    private DataExporter dataExporter;

    /**
     * Instantiates a new collector worker impl.
     */
    public AbstractCollectorWorkerImpl() {
        super();
        status = new AtomicReference<CollectorWorkerStatus>(PAUSED);
    }

    /**
     * Gets the id.
     *
     * @return the id
     */
    @Override
    public String getId() {
        return id;
    }

    /**
     * Sets the configuration.
     *
     * @param configuration
     *            the new configuration
     */
    @Override
    public void setConfiguration(C configuration) {
        this.id = configuration.getId();
        this.scanInterval = configuration.getScanInterval() * 1000;
        this.configuration = configuration;
    }

    /**
     * @see com.redhat.it.customers.dmc.core.services.metrics.CollectorWorker#getStatus()
     */
    @Override
    public CollectorWorkerStatus getStatus() {
        return status.get();
    }

    /**
     * Start.
     *
     * @return true, if successful
     * @see com.redhat.it.customers.dmc.core.services.metrics.CollectorWorker#start()
     */
    @Override
    public boolean start() {
        return status.compareAndSet(PAUSED, RUNNING);
    }

    /**
     * Pause.
     *
     * @return true, if successful
     * @see com.redhat.it.customers.dmc.core.services.metrics.CollectorWorker#pause()
     */
    @Override
    public boolean pause() {
        return status.compareAndSet(RUNNING, PAUSED);
    }

    /**
     * Resume.
     *
     * @return true, if successful
     * @see com.redhat.it.customers.dmc.core.services.metrics.CollectorWorker#pause()
     */
    @Override
    public boolean resume() {
        return status.compareAndSet(PAUSED, RUNNING);
    }

    /**
     * Stop.
     *
     * @return true, if successful
     * @see com.redhat.it.customers.dmc.core.services.metrics.CollectorWorker#stop()
     */
    @Override
    public boolean stop() {
        return status.getAndSet(STOPPED) != STOPPED;
    }

    /**
     * Checks if is started.
     *
     * @return true, if is started
     * @see com.redhat.it.customers.dmc.core.services.metrics.CollectorWorker#isStarted()
     */
    @Override
    public boolean isStarted() {
        return status.get() == RUNNING;
    }

    /**
     * Checks if is paused.
     *
     * @return true, if is paused
     * @see com.redhat.it.customers.dmc.core.services.metrics.CollectorWorker#isPaused()
     */
    @Override
    public boolean isPaused() {
        return status.get() == PAUSED;
    }

    /**
     * Checks if is stopped.
     *
     * @return true, if is stopped
     * @see com.redhat.it.customers.dmc.core.services.metrics.CollectorWorker#isStopped()
     */
    @Override
    public boolean isStopped() {
        return status.get() == STOPPED;
    }

    /**
     * Fire.
     *
     * @return the string
     * @see com.redhat.it.customers.dmc.core.services.metrics.CollectorWorker#fire()
     */
    @Override
    public String fire() {
        return _doWork();
    }

    /**
     * Fire and forget.
     *
     * @see com.redhat.it.customers.dmc.core.services.metrics.CollectorWorker#fireAndForget()
     */
    @Override
    public void fireAndForget() {
        _doWork();
    }

    /**
     * Run.
     *
     * @see java.lang.Runnable#run()
     */
    @Override
    public void run() {
        // configure();
        configureQueryExecutor();
        configureDataExporter();
        MDC.put(Constants.COLLECTOR_ID_MDC_KEY.getValue(), id);
        try {
            initComponents();
            // status.compareAndSet(PAUSED, RUNNING);
            while (status.get() != STOPPED) {
                if (status.get() == RUNNING) {
                    _doWork();
                } else {
                    LOG.info("I AM PAUSED");
                }
                if (status.get() != STOPPED) {
                    synchronized (this) {
                        try {
                            wait(scanInterval);
                        } catch (InterruptedException e) {
                            LOG.error(
                                    "Error waiting for timeout between metrics collector executions.",
                                    e);
                        }
                    }
                }
            }
        } catch (IOException e1) {
            LOG.error("", e1);
        } finally {
            try {
                disposeComponents();
            } catch (IOException e) {
                LOG.error("", e);
            }
            destroyCollectorInstanceEvent
                    .fire(new DestroyCollectorInstanceEvent(getType(), id));
        }
    }

    /**
     * Configure data exporter.
     */
    private void configureDataExporter() {
        // TODO Auto-generated method stub

    }

    /**
     * Configure query executor.
     */
    protected abstract void configureQueryExecutor();

    /**
     * Gets the query executor.
     *
     * @return the query executor
     */
    protected abstract QueryExecutor<?> getQueryExecutor();

    /**
     * Inits the components.
     *
     * @throws IOException
     *             Signals that an I/O exception has occurred.
     */
    private void initComponents() throws IOException {
        getQueryExecutor().open();
        dataExporter.open();
    }

    /**
     * Dispose components.
     *
     * @throws IOException
     *             Signals that an I/O exception has occurred.
     */
    private void disposeComponents() throws IOException {
        getQueryExecutor().close();
        dataExporter.close();

    }

    /**
     * _do work.
     *
     * @return the string
     */
    private String _doWork() {
        try {
            String[] data = getQueryExecutor().extractData();
            dataExporter.writeData(new String[] { "I AM WORKING...", "Field 1",
                    "Field \"2\"", ",,,field 3,,," });
            return "I AM WORKING...";
        } catch (Exception e) {
            LOG.error("Error extracting statistics.", e);
        }
        // LOG.info("I AM WORKING...");
        return null;
    }

    /**
     * Finalize.
     *
     * @throws Throwable
     *             the throwable
     * @see java.lang.Object#finalize()
     */
    @Override
    protected void finalize() throws Throwable {
        super.finalize();
        MDC.remove(Constants.COLLECTOR_ID_MDC_KEY.getValue());
    }

}