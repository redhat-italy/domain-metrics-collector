package com.redhat.it.customers.dmc.core.services.metrics.impl;

import static com.redhat.it.customers.dmc.core.constants.CollectorWorkerStatus.PAUSED;
import static com.redhat.it.customers.dmc.core.constants.CollectorWorkerStatus.RUNNING;
import static com.redhat.it.customers.dmc.core.constants.CollectorWorkerStatus.STOPPED;

import java.io.IOException;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicReference;
import java.util.regex.Pattern;

import javax.enterprise.context.Dependent;
import javax.inject.Inject;

import org.slf4j.Logger;
import org.slf4j.MDC;

import com.redhat.it.customers.dmc.core.constants.CollectorWorkerStatus;
import com.redhat.it.customers.dmc.core.constants.Constants;
import com.redhat.it.customers.dmc.core.dto.configuration.Configuration;
import com.redhat.it.customers.dmc.core.exceptions.CollectorStoppedException;
import com.redhat.it.customers.dmc.core.services.connection.QueryExecutor;
import com.redhat.it.customers.dmc.core.services.export.DataExporter;
import com.redhat.it.customers.dmc.core.services.metrics.CollectorWorker;

/**
 * The Class CollectorWorkerImpl.
 * 
 * @author Andrea Battaglia
 */
@Dependent
public class CollectorWorkerImpl implements CollectorWorker {

    /** The uuid. */
    private final String uuid = UUID.randomUUID().toString();

    /** The id. */
    private String id;
    private Configuration configuration;
    private long scanInterval;

    private final AtomicReference<CollectorWorkerStatus> status;

    @Inject
    private Logger LOG;

    @Inject
    private QueryExecutor queryExecutor;

    @Inject
    private DataExporter dataExporter;

    /**
     * Instantiates a new collector worker impl.
     *
     * @param id
     *            the id
     */
    public CollectorWorkerImpl() {
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
    public void setConfiguration(Configuration configuration) {
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
            status.compareAndSet(PAUSED, RUNNING);
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

    private void configureQueryExecutor() {
        queryExecutor.setUsername(configuration.getUsername());
        queryExecutor.setPassword(configuration.getPassword());
        queryExecutor.setRealm(configuration.getRealm());
        queryExecutor
                .setPatternHostname(configuration.getRegexpHostname() == null ? null
                        : Pattern.compile(configuration.getRegexpHostname()));
        queryExecutor
                .setPatternServer(configuration.getRegexpServer() == null ? null
                        : Pattern.compile(configuration.getRegexpServer()));
        queryExecutor.setApps(configuration.getApps());
    }

    private void initComponents() throws IOException {
        queryExecutor.open();
        dataExporter.open();
    }

    private void disposeComponents() throws IOException {
        queryExecutor.close();
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

    /**
     * @see com.redhat.it.customers.dmc.core.services.metrics.CollectorWorker#setDMRConnection(org.jboss.as.controller.client.ModelControllerClient)
     */
    @Override
    public void setQueryExecutor(QueryExecutor queryExecutor) {
        this.queryExecutor = queryExecutor;
    }
}