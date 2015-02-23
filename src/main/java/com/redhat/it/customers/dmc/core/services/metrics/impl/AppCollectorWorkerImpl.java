package com.redhat.it.customers.dmc.core.services.metrics.impl;

import java.util.regex.Pattern;

import javax.inject.Inject;

import com.redhat.it.customers.dmc.core.cdi.interfaces.AppCollectorWorkerBinding;
import com.redhat.it.customers.dmc.core.dto.configuration.AppConfiguration;
import com.redhat.it.customers.dmc.core.services.connection.QueryExecutor;
import com.redhat.it.customers.dmc.core.services.connection.impl.AppDMRQueryExecutorImpl;

/**
 * The Class AppCollectorWorkerImpl.
 * 
 * @author Andrea Battaglia
 */
@AppCollectorWorkerBinding
public class AppCollectorWorkerImpl extends
        AbstractCollectorWorkerImpl<AppConfiguration> {
    
    /** The query executor. */
    @Inject
    private AppDMRQueryExecutorImpl queryExecutor;

    /**
     * Instantiates a new app collector worker impl.
     */
    public AppCollectorWorkerImpl() {
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
        AppConfiguration configuration = this.configuration;
        queryExecutor.setUsername(configuration.getUsername());
        queryExecutor.setPassword(configuration.getPassword());
        queryExecutor.setRealm(configuration.getRealm());
        queryExecutor
                .setPatternHostname(configuration.getRegexpHostname() == null ? null
                        : Pattern.compile(configuration.getRegexpHostname()));
        queryExecutor
                .setPatternServer(configuration.getRegexpServer() == null ? null
                        : Pattern.compile(configuration.getRegexpServer()));
        // queryExecutor.setApps(configuration.getApps());

    }
}
