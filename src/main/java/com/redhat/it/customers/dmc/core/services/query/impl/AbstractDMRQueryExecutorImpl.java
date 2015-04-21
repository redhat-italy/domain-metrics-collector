/*
 *
 */
package com.redhat.it.customers.dmc.core.services.query.impl;

import java.io.IOException;
import java.net.UnknownHostException;
import java.util.LinkedHashSet;
import java.util.Set;

import javax.annotation.PostConstruct;
import javax.inject.Inject;

import org.jboss.as.cli.scriptsupport.CLI;
import org.jboss.as.controller.client.ModelControllerClient;
import org.jboss.dmr.ModelNode;
import org.slf4j.Logger;

import com.redhat.it.customers.dmc.core.dto.collector.qd.raw.DMRRawQueryData;
import com.redhat.it.customers.dmc.core.exceptions.DMCCloseException;
import com.redhat.it.customers.dmc.core.exceptions.DMCOpenException;
import com.redhat.it.customers.dmc.core.exceptions.DMCQueryException;

/**
 * The Class AbstractDMRQueryExecutorImpl.
 *
 * @author Andrea Battaglia
 */
public abstract class AbstractDMRQueryExecutorImpl extends
        AbstractQueryExecutorImpl<CLI> {

    /** Logger for this class. */
    @Inject
    protected Logger LOG;

    protected ModelControllerClient mcClient;

    /** The subsystem. */
    protected String subsystem;

    @PostConstruct
    private void init() {
        client = CLI.newInstance();
    }

    /**
     * @return the subsystem
     */
    public String getSubsystem() {
        return subsystem;
    }

    /**
     * @param subsystem
     *            the subsystem to set
     */
    public void setSubsystem(String subsystem) {
        this.subsystem = subsystem;
    }

    /**
     * Open.
     *
     * @throws IOException
     *             Signals that an I/O exception has occurred.
     * @see com.redhat.it.customers.dmc.core.interfaces.Openable#open()
     */
    @Override
    public void open() throws DMCOpenException {
        // client =
        // ModelControllerClientInvocationHandler.buildProxy(
        // configuration.getHostname(),
        // configuration.getPort(),
        // configuration.getUsername(),
        // configuration.getPassword(),
        // configuration.getRealm());

        try {
            mcClient = initClient();
        } catch (UnknownHostException e) {
            LOG.error(
                    "Cannot instantiate DMR Connection object because "
                            + e.getMessage(), e);
            throw new DMCOpenException(e);
        }
    }

    protected ModelControllerClient initClient() throws UnknownHostException {
        // DMRCallbackHandler callbackHandler = null;
        // callbackHandler = new DMRCallbackHandler(username, password, realm);
        // return ModelControllerClient.Factory.create(
        // InetAddress.getByName(hostname), port, callbackHandler);
        client.connect(hostname, port, username, password.toCharArray());
        return client.getCommandContext().getModelControllerClient();
    }

    /**
     * Close.
     *
     * @throws IOException
     *             Signals that an I/O exception has occurred.
     * @see java.io.Closeable#close()
     */
    @Override
    public void close() throws DMCCloseException {
        client.disconnect();
    }

    /**
     * Extract data.
     *
     * @return the string[]
     * @throws IOException
     *             Signals that an I/O exception has occurred.
     * @see com.redhat.it.customers.dmc.core.services.query.QueryExecutor#extractData()
     */
    @Override
    public DMRRawQueryData extractData() throws DMCQueryException {
        long now = System.currentTimeMillis();
        Set<String> hosts = null;
        DMRRawQueryData rawData = null;
        Set<String> servers = null;

        hosts = getHosts();
        if (LOG.isDebugEnabled()) {
            LOG.debug("extractData() - Set<String> hosts={}", hosts);
        }

        rawData = new DMRRawQueryData();

        for (String host : hosts) {
            if (patternHostname.matcher(host).matches()) {
                servers = getServers(host);
                if (LOG.isDebugEnabled()) {
                    LOG.debug("extractData() - Set<String> servers={}", servers);
                }

                for (String server : servers) {
                    if (patternServer.matcher(server).matches()) {
                        analyzeServer(now, host, server, rawData);
                    }
                }
                servers = null;
            }
        }
        return rawData;
    }

    /**
     * Analyze server.
     *
     * @param host
     *            the host
     * @param server
     *            the server
     * @param server2
     * @param rawData
     * @throws IOException
     *             Signals that an I/O exception has occurred.
     */
    protected abstract void analyzeServer(final long timestamp, String host,
            String server, DMRRawQueryData rawData) throws DMCQueryException;

    /**
     * Gets the hosts.
     *
     * @param client
     *            the client
     * @return the hosts
     * @throws DMCQueryException
     * @throws IOException
     *             Signals that an I/O exception has occurred.
     */
    protected Set<String> getHosts() throws DMCQueryException {
        ModelNode response = null;
        ModelNode op = null;
        ModelNode result = null;
        ModelNode host = null;
        Set<String> hostList = null;

        op = new ModelNode();
        op.get("operation").set("read-resource");
        op.get("operations").set(true);
        response = executeOperation(op);

        try {
            result = response.get("result");
            host = result.get("host");
            hostList = host.keys();
        } catch (IllegalArgumentException e) {
            hostList = new LinkedHashSet<>();
        } finally {
            result = null;
            response = null;
        }
        return hostList;
    }

    private ModelNode executeOperation(ModelNode op) throws DMCQueryException {
        try {
            return mcClient.execute(op);
        } catch (IOException e) {
            throw new DMCQueryException(e);
        }
    }

    /**
     * Gets the servers.
     *
     * @param client
     *            the client
     * @param host
     *            the host
     * @return the servers
     * @throws IOException
     *             Signals that an I/O exception has occurred.
     * @throws DMCQueryException
     */
    protected Set<String> getServers(String host) throws DMCQueryException {
        ModelNode result = null;
        ModelNode response = null;
        Set<String> serverList = null;
        ModelNode server = null;
        ModelNode op = null;
        ModelNode address = null;

        op = new ModelNode();
        op.get("operation").set("read-resource");
        op.get("operations").set(true);
        address = op.get("address");
        address.add("host", host);

        response = executeOperation(op);
        op = null;

        result = response.get("result");
        response = null;
        serverList = null;
        try {
            server = result.get("server");
            serverList = server.keys();
        } catch (IllegalArgumentException e) {
            serverList = new LinkedHashSet<>();
        } finally {
            result = null;
            server = null;
        }
        return serverList;
    }

}
