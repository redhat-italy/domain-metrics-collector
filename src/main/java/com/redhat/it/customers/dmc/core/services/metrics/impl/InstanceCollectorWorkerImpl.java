package com.redhat.it.customers.dmc.core.services.metrics.impl;

import javax.inject.Inject;

import com.redhat.it.customers.dmc.core.cdi.interfaces.InstanceCollectorWorkerBinding;
import com.redhat.it.customers.dmc.core.dto.configuration.InstanceConfiguration;
import com.redhat.it.customers.dmc.core.services.connection.QueryExecutor;
import com.redhat.it.customers.dmc.core.services.connection.impl.InstanceDMRQueryExecutorImpl;

/**
 * The Class InstanceCollectorWorkerImpl.
 * 
 * @author Andrea Battaglia
 */
@InstanceCollectorWorkerBinding
public class InstanceCollectorWorkerImpl extends AbstractCollectorWorkerImpl<InstanceConfiguration> {

    
    /** The query executor. */
    @Inject
    private InstanceDMRQueryExecutorImpl queryExecutor;
    
    /**
     * Instantiates a new instance collector worker impl.
     */
    public InstanceCollectorWorkerImpl() {
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
