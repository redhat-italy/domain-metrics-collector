package com.redhat.it.customers.dmc.core.services.query;

import com.redhat.it.customers.dmc.core.exceptions.DMRException;

/**
 * The Interface JMXFacade.
 * 
 * @author Andrea Battaglia
 */
public interface JMXFacade {

    /**
     * Execute.
     *
     * @param host
     *            the host
     * @param port
     *            the port
     * @param username
     *            the username
     * @param password
     *            the password
     * @param object
     *            the object
     * @param attribute
     *            the attribute
     * @param path
     *            the path
     * @return the string
     * @throws DMRException
     *             the DMR exception
     */
    public abstract String execute(String host, String port, String username,
            String password, String object, String attribute, String path)
            throws DMRException;

}