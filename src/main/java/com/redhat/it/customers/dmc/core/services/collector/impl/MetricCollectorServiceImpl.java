/**
 * 
 */
package com.redhat.it.customers.dmc.core.services.collector.impl;

import java.util.Collections;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

import javax.annotation.PostConstruct;
import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.context.Initialized;
import javax.enterprise.event.Observes;
import javax.enterprise.inject.Instance;
import javax.inject.Inject;

import org.slf4j.Logger;

import com.redhat.it.customers.dmc.core.cdi.interfaces.AppCollectorWorkerBinding;
import com.redhat.it.customers.dmc.core.cdi.interfaces.InstanceCollectorWorkerBinding;
import com.redhat.it.customers.dmc.core.cdi.interfaces.JvmCollectorWorkerBinding;
import com.redhat.it.customers.dmc.core.constants.CollectorWorkerStatus;
import com.redhat.it.customers.dmc.core.dto.configuration.Configuration;
import com.redhat.it.customers.dmc.core.dto.event.RestartCollectorEvent;
import com.redhat.it.customers.dmc.core.dto.event.StartCollectorEvent;
import com.redhat.it.customers.dmc.core.dto.event.StopCollectorEvent;
import com.redhat.it.customers.dmc.core.enums.MetricType;
import com.redhat.it.customers.dmc.core.exceptions.CollectorAlreadyExistsException;
import com.redhat.it.customers.dmc.core.exceptions.CollectorNotFoundException;
import com.redhat.it.customers.dmc.core.exceptions.CollectorStoppedException;
import com.redhat.it.customers.dmc.core.exceptions.ConfigurationNotFoundException;
import com.redhat.it.customers.dmc.core.exceptions.DMRException;
import com.redhat.it.customers.dmc.core.services.collector.CollectorWorker;
import com.redhat.it.customers.dmc.core.services.collector.MetricCollectorService;
import com.redhat.it.customers.dmc.core.services.configuration.ConfigurationService;

/**
 * The Class MetricCollectorServiceImpl.
 *
 * @author Andrea Battaglia
 */
@ApplicationScoped
public class MetricCollectorServiceImpl implements MetricCollectorService {

    /** The log. */
    @Inject
    private Logger LOG;

    @Inject
    private ConfigurationService configurationService;

    @Inject
    @AppCollectorWorkerBinding
    private Instance<AppCollectorWorkerImpl> appThreads;

    @Inject
    @InstanceCollectorWorkerBinding
    private Instance<InstanceCollectorWorkerImpl> instanceThreads;

    @Inject
    @JvmCollectorWorkerBinding
    private Instance<JmxCollectorWorkerImpl> jvmThreads;

    private final Map<String, CollectorWorker> runningCollectors;

    /**
     * 
     */
    public MetricCollectorServiceImpl() {
        runningCollectors = new ConcurrentHashMap<String, CollectorWorker>();
    }

    /**
     * @param event
     */
    void init(@Observes @Initialized(ApplicationScoped.class) Object event) {
        LOG.debug("Starting");
    }

    /**
     * Inits the.
     */
    @PostConstruct
    private void init() {
        // LOG.info("I AM " + this.getClass().getSimpleName());
    }

    @Override
    public Set<String> getCollectorNames() {
        return Collections.unmodifiableSet(runningCollectors.keySet());
    }

    @Override
    public Set<String> getCollectorNames(CollectorWorkerStatus status) {
        Set<String> collectorNames = new LinkedHashSet<String>();
        switch (status) {
        case STOPPED:
            for (String configurationName : configurationService
                    .getConfigurationNames()) {
                if (!runningCollectors.containsKey(configurationName)) {
                    collectorNames.add(configurationName);
                }
            }
            break;
        default:
            for (CollectorWorker collectorWorker : runningCollectors.values()) {
                if (collectorWorker.getStatus() == status) {
                    collectorNames.add(collectorWorker.getId());
                }
            }
        }
        return Collections.unmodifiableSet(collectorNames);
    }

    public void startCollector(@Observes StartCollectorEvent event)
            throws DMRException {
        Configuration configuration = event.getConfiguration();
        try {
            initCollector(configuration);
        } catch (CollectorAlreadyExistsException e) {
            LOG.error(e.getMessage());
        }
    }

    public void restartCollector(@Observes RestartCollectorEvent event)
            throws DMRException, CollectorStoppedException {
        Configuration configuration = event.getConfiguration();
        try {
            stopCollector(configuration.getId());
            initCollector(configuration);
        } catch (CollectorNotFoundException | CollectorAlreadyExistsException e) {
            LOG.error(e.getMessage());
        }
    }

    public void stopCollector(@Observes StopCollectorEvent event)
            throws CollectorStoppedException {
        String configurationId = event.getConfigurationId();
        try {
            stopCollector(configurationId);
        } catch (CollectorNotFoundException e) {
            LOG.error(e.getMessage());
        }
    }

    /**
     * @param event
     */
    void destroyCollectorInstance(@Observes DestroyCollectorInstanceEvent event) {
        MetricType metricType = null;
        String collectorName = null;
        metricType = event.getMetricType();
        collectorName = event.getCollectorName();
        LOG.debug("Destroying stoped collector thread {} of type {}",
                collectorName, metricType);
        switch (metricType) {
        case APP:
            appThreads.destroy((AppCollectorWorkerImpl) runningCollectors
                    .get(collectorName));
            break;
        case INSTANCE:
            instanceThreads
                    .destroy((InstanceCollectorWorkerImpl) runningCollectors
                            .get(collectorName));

            break;
        case JVM:
            jvmThreads.destroy((JmxCollectorWorkerImpl) runningCollectors
                    .get(collectorName));

            break;
        }
        metricType = null;
        collectorName = null;
    }

    /**
     * @throws CollectorAlreadyExistsException
     * @throws DMRException
     * @throws ConfigurationNotFoundException
     * @see com.redhat.it.customers.dmc.core.services.collector.MetricCollectorService#startCollector(java.lang.String)
     */
    @Override
    public boolean startCollector(String collectorName)
            throws CollectorAlreadyExistsException, DMRException,
            ConfigurationNotFoundException {
        Configuration configuration = configurationService
                .getConfiguration(collectorName);
        if (configuration == null) {
            throw new ConfigurationNotFoundException(collectorName);
        }
        return initCollector(configuration);
    }

    private boolean initCollector(Configuration configuration)
            throws CollectorAlreadyExistsException, DMRException {
        String collectorName = configuration.getId();
        if (runningCollectors.containsKey(collectorName)) {
            throw new CollectorAlreadyExistsException(collectorName);
        }
        CollectorWorker collectorWorker = buildCollectorWorker(configuration);
        // submit returns a Future on which the submitting thread can call get
        // to block until the task completes.
        //
        // Future<?> future = executor.submit(new Runnable() { ... });
        // future.get();

        runningCollectors.put(collectorName, collectorWorker);
        new Thread(collectorWorker).start();
        return true;
    }

    // /**
    // * @see
    // com.redhat.it.customers.dmc.core.services.collector.MetricCollectorService#startAllCollectors()
    // */
    // @Override
    // public boolean startAllCollectors() {
    // for (String collectorName : configurationService
    // .getConfigurationNames()) {
    // if (!runningCollectors.containsKey(collectorName)) {
    // try {
    // startCollector(collectorName);
    // } catch (CollectorAlreadyExistsException
    // | ConfigurationNotFoundException | DMRException e) {
    // LOG.warn("", e);
    // }
    // }
    // }
    // return true;
    // }

    /**
     * @throws CollectorNotFoundException
     * @throws CollectorStoppedException
     * @see com.redhat.it.customers.dmc.core.services.collector.MetricCollectorService#pauseCollector(java.lang.String)
     */
    @Override
    public boolean pauseCollector(String collectorName)
            throws CollectorNotFoundException, CollectorStoppedException {
        checkCollector(collectorName);
        return runningCollectors.get(collectorName).pause();
    }

    // /**
    // * @see
    // com.redhat.it.customers.dmc.core.services.collector.MetricCollectorService#pauseAllCollectors()
    // */
    // @Override
    // public void pauseAllCollectors() {
    // for (CollectorWorker collectorWorker : runningCollectors.values()) {
    // if (collectorWorker.isStarted()) {
    // collectorWorker.pause();
    // }
    // }
    // }

    /**
     * @see com.redhat.it.customers.dmc.core.services.collector.MetricCollectorService#resumeCollector(java.lang.String)
     */
    @Override
    public boolean resumeCollector(String collectorName)
            throws CollectorNotFoundException, CollectorStoppedException {
        checkCollector(collectorName);
        return runningCollectors.get(collectorName).pause();
    }

    // /**
    // * @see
    // com.redhat.it.customers.dmc.core.services.collector.MetricCollectorService#resumeAllCollectors()
    // */
    // @Override
    // public void resumeAllCollectors() {
    // for (CollectorWorker collectorWorker : runningCollectors.values()) {
    // if (collectorWorker.isPaused()) {
    // collectorWorker.resume();
    // }
    // }
    // }

    /**
     * @throws CollectorNotFoundException
     * @throws CollectorStoppedException
     * @see com.redhat.it.customers.dmc.core.services.collector.MetricCollectorService#stopCollector(java.lang.String)
     */
    @Override
    public boolean stopCollector(String collectorName)
            throws CollectorNotFoundException, CollectorStoppedException {
        checkCollector(collectorName);
        return runningCollectors.get(collectorName).stop();
    }

    // /**
    // * @see
    // com.redhat.it.customers.dmc.core.services.collector.MetricCollectorService#stopAllCollectors()
    // */
    // @Override
    // public boolean stopAllCollectors() {
    // for (String collectorName : runningCollectors.keySet()) {
    // try {
    // stopCollector(collectorName);
    // } catch (CollectorNotFoundException | CollectorStoppedException e) {
    // LOG.warn("", e);
    // }
    // }
    // return true;
    // }

    /**
     * @see com.redhat.it.customers.dmc.core.services.collector.MetricCollectorService#fireCollector(java.lang.String)
     */
    @Override
    public String fireCollector(String collectorName)
            throws CollectorNotFoundException, CollectorStoppedException {
        checkCollector(collectorName);
        return runningCollectors.get(collectorName).fire();
    }

    /**
     * @throws CollectorNotFoundException
     * @throws CollectorStoppedException
     * @see com.redhat.it.customers.dmc.core.services.collector.MetricCollectorService#fireAndForgetCollector(java.lang.String)
     */
    @Override
    public void fireAndForgetCollector(String collectorName)
            throws CollectorNotFoundException, CollectorStoppedException {
        checkCollector(collectorName);
        runningCollectors.get(collectorName).fire();
    }

    private void checkCollector(String collectorName)
            throws CollectorNotFoundException, CollectorStoppedException {
        CollectorWorker collectorWorker = runningCollectors.get(collectorName);
        if (collectorWorker == null) {
            throw new CollectorNotFoundException(collectorName);
        }
        if (collectorWorker.getStatus() == CollectorWorkerStatus.STOPPED) {
            throw new CollectorStoppedException(collectorName);
        }
    }

    private CollectorWorker buildCollectorWorker(Configuration configuration)
            throws DMRException {
        CollectorWorker collectorWorker = null;
        switch (configuration.getMetricType()) {
        case APP:
            collectorWorker = appThreads.get();
            break;
        case INSTANCE:
            collectorWorker = instanceThreads.get();
            break;
        case JVM:
            collectorWorker = jvmThreads.get();
            break;
        }
        collectorWorker.setConfiguration(configuration);
        if (configuration.isStart()) {
            collectorWorker.start();
        }
        // collectorWorker.setQueryExecutor(connectionService
        // .getQueryExecutor(configuration.getId()));
        return collectorWorker;
    }
}
