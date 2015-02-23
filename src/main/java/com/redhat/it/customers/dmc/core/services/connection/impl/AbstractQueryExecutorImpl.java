package com.redhat.it.customers.dmc.core.services.connection.impl;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.regex.Pattern;

import javax.enterprise.context.Dependent;
import javax.inject.Inject;

import org.slf4j.Logger;

import com.redhat.it.customers.dmc.core.services.connection.QueryExecutor;

/**
 * The Class QueryExecutorImpl.
 *
 * @author Andrea Battaglia
 */
abstract class AbstractQueryExecutorImpl<C> implements QueryExecutor<C> {
    protected static final DateFormat DATE_FORMATTER = new SimpleDateFormat(
            "yyyy-MM-dd'T'HH:mm:ssZ");

    /** Logger for this class. */
    @Inject
    private Logger LOG;

    protected C client;

    /** The hostname. */
    protected String hostname;

    /** The port. */
    protected int port;

    /** The username. */
    protected String username;

    /** The password. */
    protected String password;

    /** The realm. */
    protected String realm;

    /** The pattern hostname. */
    protected Pattern patternHostname;

    /** The pattern server. */
    protected Pattern patternServer;

    /**
     * Sets the hostname.
     *
     * @param hostname
     *            the new hostname
     */
    @Override
    public void setHostname(String hostname) {
        this.hostname = hostname;
    }

    /**
     * Sets the port.
     *
     * @param port
     *            the new port
     */
    @Override
    public void setPort(int port) {
        this.port = port;
    }

    /**
     * Sets the username.
     *
     * @param username
     *            the new username
     */
    @Override
    public void setUsername(String username) {
        this.username = username;
    }

    /**
     * Sets the password.
     *
     * @param password
     *            the new password
     */
    @Override
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * Sets the realm.
     *
     * @param realm
     *            the new realm
     */
    @Override
    public void setRealm(String realm) {
        this.realm = realm;
    }

    /**
     * Sets the pattern hostname.
     *
     * @param patternHostname
     *            the new pattern hostname
     */
    @Override
    public void setPatternHostname(Pattern patternHostname) {
        this.patternHostname = patternHostname;
    }

    /**
     * Sets the pattern server.
     *
     * @param patternServer
     *            the new pattern server
     */
    @Override
    public void setPatternServer(Pattern patternServer) {
        this.patternServer = patternServer;
    }

    /**
     * @see com.redhat.it.customers.dmc.core.services.connection.QueryExecutor#extractHeader()
     */
    @Override
    public String[] extractHeader() throws IOException {
        return null;
    }

}
