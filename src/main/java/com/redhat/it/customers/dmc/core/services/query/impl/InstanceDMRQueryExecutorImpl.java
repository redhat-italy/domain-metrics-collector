package com.redhat.it.customers.dmc.core.services.query.impl;

import java.io.IOException;

import org.jboss.weld.environment.se.contexts.ThreadScoped;

import com.redhat.it.customers.dmc.core.exceptions.DMCQueryException;

/**
 * The Class InstanceDMRQueryExecutorImpl.
 * 
 * @author Andrea Battaglia
 */
//@ThreadScoped
public class InstanceDMRQueryExecutorImpl extends AbstractDMRQueryExecutorImpl {


    /**
     * @see com.redhat.it.customers.dmc.core.services.query.impl.AbstractDMRQueryExecutorImpl#analyzeServer(java.lang.String, java.lang.String)
     */
    @Override
    protected void analyzeServer(String host, String server) throws DMCQueryException {
        // TODO Auto-generated method stub

    }

}
