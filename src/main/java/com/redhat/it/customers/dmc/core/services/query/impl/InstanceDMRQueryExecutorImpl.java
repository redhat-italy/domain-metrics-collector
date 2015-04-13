package com.redhat.it.customers.dmc.core.services.query.impl;

import com.redhat.it.customers.dmc.core.dto.collector.qd.raw.DMRRawQueryData;
import com.redhat.it.customers.dmc.core.exceptions.DMCQueryException;

/**
 * The Class InstanceDMRQueryExecutorImpl.
 * 
 * @author Andrea Battaglia
 */
// @ThreadScoped
public class InstanceDMRQueryExecutorImpl extends AbstractDMRQueryExecutorImpl {

    public InstanceDMRQueryExecutorImpl() {
        // TODO Auto-generated constructor stub
    }

    @Override
    protected void analyzeServer(final long timestamp, final String host,
            final String server, final DMRRawQueryData rawData)
            throws DMCQueryException {
        // TODO Auto-generated method stub

    }

}
