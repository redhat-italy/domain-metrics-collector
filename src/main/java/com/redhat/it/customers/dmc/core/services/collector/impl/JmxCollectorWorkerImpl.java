package com.redhat.it.customers.dmc.core.services.collector.impl;

import javax.enterprise.inject.Instance;
import javax.enterprise.inject.New;
import javax.inject.Inject;

import com.redhat.it.customers.dmc.core.cdi.interfaces.JvmCollectorWorkerBinding;
import com.redhat.it.customers.dmc.core.dto.configuration.InstanceConfiguration;
import com.redhat.it.customers.dmc.core.dto.configuration.JvmConfiguration;
import com.redhat.it.customers.dmc.core.enums.MetricType;
import com.redhat.it.customers.dmc.core.exceptions.DMCException;
import com.redhat.it.customers.dmc.core.services.data.export.DataExporter;
import com.redhat.it.customers.dmc.core.services.data.transformer.DataTransformer;
import com.redhat.it.customers.dmc.core.services.query.QueryExecutor;
import com.redhat.it.customers.dmc.core.services.query.impl.InstanceDMRQueryExecutorImpl;
import com.redhat.it.customers.dmc.core.services.query.impl.JmxQueryExecutorImpl;

/**
 * The Class JmxCollectorWorkerImpl.
 * 
 * @author Andrea Battaglia
 */
@JvmCollectorWorkerBinding
public class JmxCollectorWorkerImpl extends
        AbstractCollectorWorkerImpl<JvmConfiguration> {
    
    @Inject
    @New(JmxQueryExecutorImpl.class)
    private Instance<QueryExecutor> queryExecutorInstance;
    private JmxQueryExecutorImpl queryExecutor;

    /**
     * @see com.redhat.it.customers.dmc.core.services.collector.CollectorWorker#getType()
     */
    @Override
    public MetricType getType() {
        return MetricType.JVM;
    }
    
    @Override
    protected void disposeComponents() throws DMCException {
        super.disposeComponents();
        queryExecutorInstance.destroy(queryExecutor);
    }

    @Override
    protected QueryExecutor<?> getQueryExecutor() {
        return queryExecutor;
    }

    /**
     * @see com.redhat.it.customers.dmc.core.services.collector.impl.AbstractCollectorWorkerImpl#configureQueryExecutor()
     */
    @Override
    protected void configureQueryExecutor() {
        JvmConfiguration configuration = this.configuration;
        JmxQueryExecutorImpl queryExecutor = (JmxQueryExecutorImpl) queryExecutorInstance.get();
        //...
        this.queryExecutor=queryExecutor;
    }

}
