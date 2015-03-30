package com.redhat.it.customers.dmc.core.interfaces;

import com.redhat.it.customers.dmc.core.exceptions.DMCStartException;
import com.redhat.it.customers.dmc.core.exceptions.DMCStopException;

/**
 * The Interface Startable.
 * 
 * @author Andrea Battaglia
 */
public interface Startable {

    /**
     * Start.
     *
     * @throws DMCStartException
     *             the start exception
     */
    void start() throws DMCStartException;

    /**
     * Stop.
     *
     * @throws DMCStopException
     *             the stop exception
     */
    void stop() throws DMCStopException;
}
