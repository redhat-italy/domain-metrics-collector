package com.redhat.it.customers.dmc.core.services.connection.impl;

import java.io.IOException;

import org.jboss.weld.environment.se.contexts.ThreadScoped;

/**
 * The Class InstanceDMRQueryExecutorImpl.
 * 
 * @author Andrea Battaglia
 */
//@ThreadScoped
public class InstanceDMRQueryExecutorImpl extends AbstractDMRQueryExecutorImpl {

    /**
     * Instantiates a new instance dmr query executor impl.
     */
    public InstanceDMRQueryExecutorImpl() {
        // TODO Auto-generated constructor stub
    }

    /**
     * @see com.redhat.it.customers.dmc.core.services.connection.impl.AbstractDMRQueryExecutorImpl#analyzeServer(java.lang.String, java.lang.String)
     */
    @Override
    protected void analyzeServer(String host, String server) throws IOException {
        // TODO Auto-generated method stub

    }

}
