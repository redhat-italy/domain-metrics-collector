package com.redhat.it.customers.dmc.core.services.metrics.impl;

import javax.inject.Inject;

import com.redhat.it.customers.dmc.core.cdi.interfaces.InstanceCollectorWorkerBinding;
import com.redhat.it.customers.dmc.core.constants.MetricType;
import com.redhat.it.customers.dmc.core.dto.configuration.InstanceConfiguration;
import com.redhat.it.customers.dmc.core.services.connection.QueryExecutor;
import com.redhat.it.customers.dmc.core.services.connection.impl.InstanceDMRQueryExecutorImpl;

/**
 * The Class InstanceCollectorWorkerImpl.
 * 
 * @author Andrea Battaglia
 */
@InstanceCollectorWorkerBinding
public class InstanceCollectorWorkerImpl extends
        AbstractCollectorWorkerImpl<InstanceConfiguration> {

    /** The query executor. */
    @Inject
    private InstanceDMRQueryExecutorImpl queryExecutor;

    /**
     * @see com.redhat.it.customers.dmc.core.services.metrics.CollectorWorker#getType()
     */
    @Override
    public MetricType getType() {
        return MetricType.INSTANCE;
    }

    /**
     * @see com.redhat.it.customers.dmc.core.services.metrics.impl.AbstractCollectorWorkerImpl#getQueryExecutor()
     */
    @Override
    protected QueryExecutor getQueryExecutor() {
        return queryExecutor;
    }

    /**
     * @see com.redhat.it.customers.dmc.core.services.metrics.impl.AbstractCollectorWorkerImpl#configureQueryExecutor()
     */
    @Override
    protected void configureQueryExecutor() {
        // TODO Auto-generated method stub

    }

}
