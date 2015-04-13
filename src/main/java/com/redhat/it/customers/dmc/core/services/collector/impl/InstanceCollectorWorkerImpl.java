package com.redhat.it.customers.dmc.core.services.collector.impl;

import javax.enterprise.inject.Instance;
import javax.enterprise.inject.New;
import javax.inject.Inject;

import com.redhat.it.customers.dmc.core.cdi.interfaces.InstanceCollectorWorkerBinding;
import com.redhat.it.customers.dmc.core.dto.configuration.InstanceConfiguration;
import com.redhat.it.customers.dmc.core.enums.MetricType;
import com.redhat.it.customers.dmc.core.exceptions.DMCException;
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
    
    @Inject
    @New(InstanceDMRQueryExecutorImpl.class)
    private Instance<QueryExecutor> queryExecutorInstance;
    private InstanceDMRQueryExecutorImpl queryExecutor;

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
    protected QueryExecutor getQueryExecutor() {
        return queryExecutor;
    }

    /**
     * @see com.redhat.it.customers.dmc.core.services.collector.impl.AbstractCollectorWorkerImpl#configureQueryExecutor()
     */
    @Override
    protected void configureQueryExecutor() {
        InstanceConfiguration configuration = this.configuration;
        InstanceDMRQueryExecutorImpl queryExecutor = (InstanceDMRQueryExecutorImpl) queryExecutorInstance.get();
        //...
        this.queryExecutor=queryExecutor;
    }

}
