/**
 * 
 */
package com.redhat.it.customers.dmc.core.services.query;

import java.io.Closeable;
import java.io.IOException;
import java.util.regex.Pattern;

import com.redhat.it.customers.dmc.core.dto.collector.qd.raw.AbstractRawQueryData;
import com.redhat.it.customers.dmc.core.interfaces.Openable;
import com.redhat.it.customers.dmc.core.services.query.impl.AppDMRQueryExecutorImpl;
import com.redhat.it.customers.dmc.core.services.query.impl.InstanceDMRQueryExecutorImpl;
import com.redhat.it.customers.dmc.core.services.query.impl.JmxQueryExecutorImpl;

/**
 * The Interface QueryExecutor. Manages connections and executes queries to the
 * right destination.
 * 
 * @see JmxQueryExecutorImpl
 * @see AppDMRQueryExecutorImpl
 * @see InstanceDMRQueryExecutorImpl
 *
 * @author Andrea Battaglia
 */
public interface QueryExecutor<C> extends Openable {

    /**
     * Extract data.
     *
     * @return the string[]
     * @throws Exception
     *             the exception
     */
    AbstractRawQueryData extractData() throws Exception;

    /**
     * Sets the hostname.
     *
     * @param hostname
     *            the new hostname
     */
    void setHostname(String hostname);

    /**
     * Sets the port.
     *
     * @param port
     *            the new port
     */
    void setPort(int port);

    /**
     * Sets the username.
     *
     * @param username
     *            the new username
     */
    void setUsername(String username);

    /**
     * Sets the password.
     *
     * @param password
     *            the new password
     */
    void setPassword(String password);

    /**
     * Sets the realm.
     *
     * @param realm
     *            the new realm
     */
    void setRealm(String realm);

    /**
     * Sets the pattern hostname.
     *
     * @param patternHostname
     *            the new pattern hostname
     */
    void setPatternHostname(Pattern patternHostname);

    /**
     * Sets the pattern server.
     *
     * @param patternServer
     *            the new pattern server
     */
    void setPatternServer(Pattern patternServer);

}
