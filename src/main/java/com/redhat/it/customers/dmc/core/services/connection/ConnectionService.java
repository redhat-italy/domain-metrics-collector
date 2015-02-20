package com.redhat.it.customers.dmc.core.services.connection;

import com.redhat.it.customers.dmc.core.exceptions.DMRException;

/**
 * The Interface ConnectionManagerService.
 * 
 * @author Andrea Battaglia
 */
public interface ConnectionService {

    /**
     * Gets the DMR connection.
     *
     * @param configurationId
     *            the configuration id
     * @return the DMR connection
     * @throws DMRException
     */
    QueryExecutor getQueryExecutor(String configurationId) throws DMRException;

}
