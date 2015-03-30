/**
 * 
 */
package com.redhat.it.customers.dmc.core.interfaces;

import com.redhat.it.customers.dmc.core.exceptions.DMCCloseException;
import com.redhat.it.customers.dmc.core.exceptions.DMCOpenException;

/**
 * The Interface Openable.
 *
 * @author Andrea Battaglia
 */
public interface Openable {

    /**
     * Open.
     *
     * @throws IOException
     *             Signals that an I/O exception has occurred.
     * @throws DMCOpenException
     */
    void open() throws DMCOpenException;
    
    /**
     * Close.
     *
     * @throws DMCCloseException the DMC close exception
     */
    void close() throws DMCCloseException;
}
