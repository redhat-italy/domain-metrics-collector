package com.redhat.it.customers.dmc.core.services.collector.impl;

import static com.redhat.it.customers.dmc.core.constants.CollectorWorkerStatus.PAUSED;
import static com.redhat.it.customers.dmc.core.constants.CollectorWorkerStatus.RUNNING;
import static com.redhat.it.customers.dmc.core.constants.CollectorWorkerStatus.STOPPED;

import java.io.IOException;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicReference;

import javax.enterprise.event.Event;
import javax.enterprise.inject.Instance;
import javax.inject.Inject;

import org.slf4j.Logger;
import org.slf4j.MDC;

import com.redhat.it.customers.dmc.core.constants.CollectorWorkerStatus;
import com.redhat.it.customers.dmc.core.constants.Constants;
import com.redhat.it.customers.dmc.core.dto.collector.qd.raw.AbstractRawQueryData;
import com.redhat.it.customers.dmc.core.dto.collector.qd.t.AbstractTransformedQueryData;
import com.redhat.it.customers.dmc.core.dto.configuration.Configuration;
import com.redhat.it.customers.dmc.core.exceptions.DMCException;
import com.redhat.it.customers.dmc.core.services.collector.CollectorWorker;
import com.redhat.it.customers.dmc.core.services.data.export.DataExporter;
import com.redhat.it.customers.dmc.core.services.data.transformer.DataTransformer;
import com.redhat.it.customers.dmc.core.services.query.QueryExecutor;

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

    /** The data transformer. */
    @Inject
    private Instance<DataTransformer> dataTransformerInstance;
    private DataTransformer dataTransformer;
    /** The data exporter. */
    @Inject
    private Instance<DataExporter> dataExporterInstance;
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
     * @see com.redhat.it.customers.dmc.core.services.collector.CollectorWorker#getStatus()
     */
    @Override
    public CollectorWorkerStatus getStatus() {
        return status.get();
    }

    /**
     * Start.
     *
     * @return true, if successful
     * @see com.redhat.it.customers.dmc.core.services.collector.CollectorWorker#start()
     */
    @Override
    public boolean start() {
        return status.compareAndSet(PAUSED, RUNNING);
    }

    /**
     * Pause.
     *
     * @return true, if successful
     * @see com.redhat.it.customers.dmc.core.services.collector.CollectorWorker#pause()
     */
    @Override
    public boolean pause() {
        return status.compareAndSet(RUNNING, PAUSED);
    }

    /**
     * Resume.
     *
     * @return true, if successful
     * @see com.redhat.it.customers.dmc.core.services.collector.CollectorWorker#pause()
     */
    @Override
    public boolean resume() {
        return status.compareAndSet(PAUSED, RUNNING);
    }

    /**
     * Stop.
     *
     * @return true, if successful
     * @see com.redhat.it.customers.dmc.core.services.collector.CollectorWorker#stop()
     */
    @Override
    public boolean stop() {
        return status.getAndSet(STOPPED) != STOPPED;
    }

    /**
     * Checks if is started.
     *
     * @return true, if is started
     * @see com.redhat.it.customers.dmc.core.services.collector.CollectorWorker#isStarted()
     */
    @Override
    public boolean isStarted() {
        return status.get() == RUNNING;
    }

    /**
     * Checks if is paused.
     *
     * @return true, if is paused
     * @see com.redhat.it.customers.dmc.core.services.collector.CollectorWorker#isPaused()
     */
    @Override
    public boolean isPaused() {
        return status.get() == PAUSED;
    }

    /**
     * Checks if is stopped.
     *
     * @return true, if is stopped
     * @see com.redhat.it.customers.dmc.core.services.collector.CollectorWorker#isStopped()
     */
    @Override
    public boolean isStopped() {
        return status.get() == STOPPED;
    }

    /**
     * Fire.
     *
     * @return the string
     * @see com.redhat.it.customers.dmc.core.services.collector.CollectorWorker#fire()
     */
    @Override
    public String fire() {
        return _doWork();
    }

    /**
     * Fire and forget.
     *
     * @see com.redhat.it.customers.dmc.core.services.collector.CollectorWorker#fireAndForget()
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
        } catch (DMCException e1) {
            LOG.error("", e1);
        } finally {
            try {
                disposeComponents();
            } catch (DMCException e) {
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

    protected abstract DataExporter getDataExporter();

    protected abstract DataTransformer getDataTransformer();

    /**
     * Inits the components.
     *
     * @throws IOException
     *             Signals that an I/O exception has occurred.
     */
    private void initComponents() throws DMCException {
        getQueryExecutor().open();
        dataExporter.open();
    }

    /**
     * Dispose components.
     *
     * @throws IOException
     *             Signals that an I/O exception has occurred.
     */
    private void disposeComponents() throws DMCException {
        getQueryExecutor().close();
        dataExporter.close();

    }

    /**
     * _do work.
     *
     * @return the string
     */
    @SuppressWarnings({ "rawtypes"})
    private String _doWork() {
        try {
            AbstractRawQueryData rawData = getQueryExecutor().extractData();
            AbstractTransformedQueryData transformedData = getDataTransformer()
                    .transformData(rawData);
            getDataExporter().exportData(transformedData);
            // dataExporter.writeData(new String[] { "I AM WORKING...",
            // "Field 1",
            // "Field \"2\"", ",,,field 3,,," });
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