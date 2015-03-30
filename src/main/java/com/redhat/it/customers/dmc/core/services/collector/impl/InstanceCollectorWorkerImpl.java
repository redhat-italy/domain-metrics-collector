package com.redhat.it.customers.dmc.core.services.collector.impl;

import javax.inject.Inject;

import com.redhat.it.customers.dmc.core.cdi.interfaces.InstanceCollectorWorkerBinding;
import com.redhat.it.customers.dmc.core.dto.configuration.InstanceConfiguration;
import com.redhat.it.customers.dmc.core.enums.MetricType;
import com.redhat.it.customers.dmc.core.services.data.export.DataExporter;
import com.redhat.it.customers.dmc.core.services.data.transformer.DataTransformer;
import com.redhat.it.customers.dmc.core.services.query.QueryExecutor;
import com.redhat.it.customers.dmc.core.services.query.impl.InstanceDMRQueryExecutorImpl;

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
     * @see com.redhat.it.customers.dmc.core.services.collector.CollectorWorker#getType()
     */
    @Override
    public MetricType getType() {
        return MetricType.INSTANCE;
    }

    /**
     * @see com.redhat.it.customers.dmc.core.services.collector.impl.AbstractCollectorWorkerImpl#getQueryExecutor()
     */
    @Override
    protected QueryExecutor getQueryExecutor() {
        return queryExecutor;
    }

    @Override
    protected DataExporter getDataExporter() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    protected DataTransformer getDataTransformer() {
        // TODO Auto-generated method stub
        return null;
    }

    /**
     * @see com.redhat.it.customers.dmc.core.services.collector.impl.AbstractCollectorWorkerImpl#configureQueryExecutor()
     */
    @Override
    protected void configureQueryExecutor() {
        // TODO Auto-generated method stub

    }

}
