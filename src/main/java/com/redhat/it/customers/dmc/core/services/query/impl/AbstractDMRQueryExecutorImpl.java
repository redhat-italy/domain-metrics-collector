package com.redhat.it.customers.dmc.core.services.query.impl;

import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.LinkedHashSet;
import java.util.Set;

import javax.inject.Inject;

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
        AbstractQueryExecutorImpl<ModelControllerClient> {

    /** Logger for this class. */
    @Inject
    protected Logger LOG;

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
            client = initClient();
        } catch (UnknownHostException e) {
            LOG.error(
                    "Cannot instantiate DMR Connection object because "
                            + e.getMessage(), e);
            throw new DMCOpenException(e);
        }
    }

    protected ModelControllerClient initClient() throws UnknownHostException {
        DMRCallbackHandler callbackHandler = null;
        callbackHandler = new DMRCallbackHandler(username, password, realm);
        return ModelControllerClient.Factory.create(
                InetAddress.getByName(hostname), port, callbackHandler);
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
        try {
            client.close();
        } catch (IOException e) {
            throw new DMCCloseException(e);
        }
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

        hosts = getHosts(client);
        if (LOG.isDebugEnabled()) {
            LOG.debug("extractData() - Set<String> hosts={}", hosts);
        }

        rawData = new DMRRawQueryData();

        for (String host : hosts) {
            if (patternHostname.matcher(host).matches()) {
                servers = getServers(client, host);
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
    protected Set<String> getHosts(ModelControllerClient client)
            throws DMCQueryException {
        ModelNode response = null;
        ModelNode op = null;
        ModelNode result = null;
        ModelNode host = null;
        Set<String> hostList = null;

        op = new ModelNode();
        op.get("operation").set("read-resource");
        op.get("operations").set(true);
        response = executeOperation(client, op);

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

    private ModelNode executeOperation(ModelControllerClient client,
            ModelNode op) throws DMCQueryException {
        try {
            return client.execute(op);
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
    protected Set<String> getServers(ModelControllerClient client, String host)
            throws DMCQueryException {
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

        response = executeOperation(client, op);
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

    /**
     * Gets the all statistics.
     *
     * @param client
     *            the client
     * @param host
     *            the host
     * @param server
     *            the server
     * @param deployment
     *            the deployment
     * @return the all statistics
     * @throws IOException
     *             Signals that an I/O exception has occurred.
     */
    protected ModelNode getDeploymentStatistics(String host, String server,
            String deployment) throws IOException {
        ModelNode op = null;
        ModelNode address = null;
        ModelNode response = null;

        op = new ModelNode();
        op.get("operation").set("read-resource");
        op.get("operations").set(true);
        op.get("include-runtime").set(true);
        op.get("recursive").set(true);
        address = op.get("address");
        address.add("host", host);
        address.add("server", server);
        address.add("deployment", deployment);

        address = null;

        response = client.execute(op);
        op = null;

        return response;
    }

}
