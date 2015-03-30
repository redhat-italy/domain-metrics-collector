package com.redhat.it.customers.dmc.core.services.collector.impl;

import javax.inject.Inject;

import com.redhat.it.customers.dmc.core.cdi.interfaces.JvmCollectorWorkerBinding;
import com.redhat.it.customers.dmc.core.dto.configuration.JvmConfiguration;
import com.redhat.it.customers.dmc.core.enums.MetricType;
import com.redhat.it.customers.dmc.core.services.data.export.DataExporter;
import com.redhat.it.customers.dmc.core.services.data.transformer.DataTransformer;
import com.redhat.it.customers.dmc.core.services.query.QueryExecutor;
import com.redhat.it.customers.dmc.core.services.query.impl.JmxQueryExecutorImpl;

/**
 * The Class JmxCollectorWorkerImpl.
 * 
 * @author Andrea Battaglia
 */
@JvmCollectorWorkerBinding
public class JmxCollectorWorkerImpl extends
        AbstractCollectorWorkerImpl<JvmConfiguration> {

    /** The query executor. */
    @Inject
    private JmxQueryExecutorImpl queryExecutor;

    /**
     * @see com.redhat.it.customers.dmc.core.services.collector.CollectorWorker#getType()
     */
    @Override
    public MetricType getType() {
        return MetricType.JVM;
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
