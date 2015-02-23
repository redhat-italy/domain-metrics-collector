package com.redhat.it.customers.dmc.core.services.metrics.impl;

import static com.redhat.it.customers.dmc.core.constants.CollectorWorkerStatus.PAUSED;
import static com.redhat.it.customers.dmc.core.constants.CollectorWorkerStatus.RUNNING;
import static com.redhat.it.customers.dmc.core.constants.CollectorWorkerStatus.STOPPED;

import java.io.IOException;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicReference;

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
 */
public abstract class AbstractCollectorWorkerImpl<C extends Configuration>
        implements CollectorWorker<C> {

    /** The uuid. */
    private final String uuid = UUID.randomUUID().toString();

    /** The id. */
    protected String id;
    protected C configuration;
    protected long scanInterval;

    private final AtomicReference<CollectorWorkerStatus> status;

    @Inject
    private Logger LOG;

    @Inject
    private DataExporter dataExporter;

    /**
     * Instantiates a new collector worker impl.
     *
     * @param id
     *            the id
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
     * @param id
     */
    @Override
    public void setConfiguration(C configuration) {
        this.id = configuration.getId();
        this.scanInterval = configuration.getScanInterval() * 1000;
        this.configuration = configuration;
    }

    @Override
    public CollectorWorkerStatus getStatus() {
        return status.get();
    }

    /**
     * @see com.redhat.it.customers.dmc.core.services.metrics.CollectorWorker#start()
     */
    @Override
    public boolean start() {
        return status.compareAndSet(PAUSED, RUNNING);
    }

    /**
     * @see com.redhat.it.customers.dmc.core.services.metrics.CollectorWorker#pause()
     */
    @Override
    public boolean pause() {
        return status.compareAndSet(RUNNING, PAUSED);
    }

    /**
     * @see com.redhat.it.customers.dmc.core.services.metrics.CollectorWorker#pause()
     */
    @Override
    public boolean resume() {
        return status.compareAndSet(PAUSED, RUNNING);
    }

    /**
     * @see com.redhat.it.customers.dmc.core.services.metrics.CollectorWorker#stop()
     */
    @Override
    public boolean stop() {
        return status.getAndSet(STOPPED) != STOPPED;
    }

    /**
     * @see com.redhat.it.customers.dmc.core.services.metrics.CollectorWorker#isStarted()
     */
    @Override
    public boolean isStarted() {
        return status.get() == RUNNING;
    }

    /**
     * @see com.redhat.it.customers.dmc.core.services.metrics.CollectorWorker#isPaused()
     */
    @Override
    public boolean isPaused() {
        return status.get() == PAUSED;
    }

    /**
     * @see com.redhat.it.customers.dmc.core.services.metrics.CollectorWorker#isStopped()
     */
    @Override
    public boolean isStopped() {
        return status.get() == STOPPED;
    }

    /**
     * @see com.redhat.it.customers.dmc.core.services.metrics.CollectorWorker#fire()
     */
    @Override
    public String fire() {
        return _doWork();
    }

    /**
     * @see com.redhat.it.customers.dmc.core.services.metrics.CollectorWorker#fireAndForget()
     */
    @Override
    public void fireAndForget() {
        _doWork();
    }

    /**
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
        }
    }

    private void configureDataExporter() {
        // TODO Auto-generated method stub

    }

    protected abstract void configureQueryExecutor();

    protected abstract QueryExecutor getQueryExecutor();

    private void initComponents() throws IOException {
        getQueryExecutor().open();
        dataExporter.open();
    }

    private void disposeComponents() throws IOException {
        getQueryExecutor().close();
        dataExporter.close();

    }

    private String _doWork() {
        // LOG.info("I AM WORKING...");
        dataExporter.writeData(new String[] { "I AM WORKING...", "Field 1",
                "Field \"2\"", ",,,field 3,,," });
        return "I AM WORKING...";
    }

    /**
     * @see java.lang.Object#finalize()
     */
    @Override
    protected void finalize() throws Throwable {
        super.finalize();
        MDC.remove(Constants.COLLECTOR_ID_MDC_KEY.getValue());
    }

}