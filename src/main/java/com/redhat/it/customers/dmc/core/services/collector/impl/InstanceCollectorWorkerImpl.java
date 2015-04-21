/*
 *
 */
package com.redhat.it.customers.dmc.core.services.collector.impl;

import java.util.regex.Pattern;

import javax.enterprise.inject.Instance;
import javax.enterprise.inject.New;
import javax.inject.Inject;

import com.redhat.it.customers.dmc.core.cdi.interfaces.InstanceCollectorWorkerBinding;
import com.redhat.it.customers.dmc.core.dto.configuration.InstanceConfiguration;
import com.redhat.it.customers.dmc.core.enums.MetricType;
import com.redhat.it.customers.dmc.core.exceptions.DMCException;
import com.redhat.it.customers.dmc.core.services.query.QueryExecutor;
import com.redhat.it.customers.dmc.core.services.query.impl.InstanceDMRQueryExecutorImpl;

/**
 * The Class InstanceCollectorWorkerImpl.
 *
 * @author Andrea Battaglia
 */
// @Dependent
@InstanceCollectorWorkerBinding
public class InstanceCollectorWorkerImpl extends
        AbstractCollectorWorkerImpl<InstanceConfiguration> {

    /** The query executor. */
    private QueryExecutor<?> queryExecutor;

    @Inject
    @New(InstanceDMRQueryExecutorImpl.class)
    private Instance<QueryExecutor<?>> queryExecutorInstance;

    /**
     * @see com.redhat.it.customers.dmc.core.services.collector.CollectorWorker#getType()
     */
    @Override
    public MetricType getType() {
        return MetricType.INSTANCE;
    }

    @Override
    protected void disposeComponents() throws DMCException {
        super.disposeComponents();
        queryExecutorInstance.destroy(queryExecutor);
    }

    /**
     * @see com.redhat.it.customers.dmc.core.services.collector.impl.AbstractCollectorWorkerImpl#getQueryExecutor()
     */
    @Override
    protected QueryExecutor<?> getQueryExecutor() {
        return queryExecutor;
    }

    @Override
    protected void configureDataTransformer() {
        super.configureDataTransformer();

    }

    /**
     * @see com.redhat.it.customers.dmc.core.services.collector.impl.AbstractCollectorWorkerImpl#configureQueryExecutor()
     */
    @Override
    protected void configureQueryExecutor() {
        InstanceConfiguration configuration = this.configuration;
        InstanceDMRQueryExecutorImpl queryExecutor = (InstanceDMRQueryExecutorImpl) queryExecutorInstance
                .get();
        queryExecutor.setConfigurationId(id);
        queryExecutor.setHostname(configuration.getHostname());
        queryExecutor.setPort(configuration.getPort());
        queryExecutor.setUsername(configuration.getUsername());
        queryExecutor.setPassword(configuration.getPassword());
        queryExecutor.setRealm(configuration.getRealm());
        queryExecutor
                .setPatternHostname(configuration.getRegexpHostname() == null ? null
                        : Pattern.compile(configuration.getRegexpHostname()));
        queryExecutor
                .setPatternServer(configuration.getRegexpServer() == null ? null
                        : Pattern.compile(configuration.getRegexpServer()));

        queryExecutor.setSubsystem(configuration.getSubsystem());
        queryExecutor.setSubsystemComponentKey(configuration
                .getSubsystemComponentKey());
        queryExecutor.setSubsystemComponentValue(configuration
                .getSubsystemComponentValue());
        queryExecutor.setSubsystemComponentAttributeKey(configuration
                .getSubsystemComponentAttributeKey());
        queryExecutor.setSubsystemComponentAttributeValue(configuration
                .getSubsystemComponentAttributeValue());
        queryExecutor.setIncludeRuntime(configuration.isIncludeRuntime());
        queryExecutor.setRecursive(configuration.isRecursive());
        this.queryExecutor = queryExecutor;
    }
}
