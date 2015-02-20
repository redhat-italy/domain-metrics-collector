package com.redhat.it.customers.dmc.core.interfaces;

import com.redhat.it.customers.dmc.core.exceptions.StartException;
import com.redhat.it.customers.dmc.core.exceptions.StopException;

/**
 * The Interface Startable.
 * 
 * @author Andrea Battaglia
 */
public interface Startable {

    /**
     * Start.
     *
     * @throws StartException
     *             the start exception
     */
    void start() throws StartException;

    /**
     * Stop.
     *
     * @throws StopException
     *             the stop exception
     */
    void stop() throws StopException;
}
