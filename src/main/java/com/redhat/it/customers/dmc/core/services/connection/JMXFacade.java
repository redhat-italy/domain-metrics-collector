package com.redhat.it.customers.dmc.core.services.connection;

import java.io.IOException;

import javax.management.AttributeNotFoundException;
import javax.management.InstanceNotFoundException;
import javax.management.MBeanException;
import javax.management.MalformedObjectNameException;
import javax.management.ReflectionException;

import com.redhat.it.customers.dmc.core.exceptions.DMCException;
import com.redhat.it.customers.dmc.core.exceptions.DMRException;

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
     * @throws DMCException
     *             the DMC exception
     * @throws MalformedObjectNameException
     *             the malformed object name exception
     * @throws AttributeNotFoundException
     *             the attribute not found exception
     * @throws InstanceNotFoundException
     *             the instance not found exception
     * @throws MBeanException
     *             the m bean exception
     * @throws ReflectionException
     *             the reflection exception
     * @throws IOException
     *             Signals that an I/O exception has occurred.
     */
    public abstract String execute(String host, String port, String username,
            String password, String object, String attribute, String path) throws DMRException
            ;

}