package com.redhat.it.customers.dmc.core.services.metrics.impl;

import javax.inject.Inject;

import com.redhat.it.customers.dmc.core.cdi.interfaces.JvmCollectorWorkerBinding;
import com.redhat.it.customers.dmc.core.dto.configuration.JvmConfiguration;
import com.redhat.it.customers.dmc.core.services.connection.QueryExecutor;
import com.redhat.it.customers.dmc.core.services.connection.impl.JmxQueryExecutorImpl;

/**
 * The Class JvmCollectorWorkerImpl.
 * 
 * @author Andrea Battaglia
 */
@JvmCollectorWorkerBinding
public class JvmCollectorWorkerImpl extends
        AbstractCollectorWorkerImpl<JvmConfiguration> {
    
    /** The query executor. */
    @Inject
    private JmxQueryExecutorImpl queryExecutor;

    /**
     * Instantiates a new jvm collector worker impl.
     */
    public JvmCollectorWorkerImpl() {
        // TODO Auto-generated constructor stub
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