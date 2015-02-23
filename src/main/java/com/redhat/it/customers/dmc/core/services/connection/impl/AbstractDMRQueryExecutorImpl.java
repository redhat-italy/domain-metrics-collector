package com.redhat.it.customers.dmc.core.services.connection.impl;

import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Set;

import javax.inject.Inject;

import org.jboss.as.controller.client.ModelControllerClient;
import org.jboss.dmr.ModelNode;
import org.slf4j.Logger;

// TODO: Auto-generated Javadoc
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
    public void open() throws IOException {
        // client =
        // ModelControllerClientInvocationHandler.buildProxy(
        // configuration.getHostname(),
        // configuration.getPort(),
        // configuration.getUsername(),
        // configuration.getPassword(),
        // configuration.getRealm());

        DMRCallbackHandler callbackHandler = null;
        callbackHandler = new DMRCallbackHandler(username, password, realm);
        try {
            client = ModelControllerClient.Factory.create(
                    InetAddress.getByName(hostname), port, callbackHandler);
        } catch (UnknownHostException e) {
            LOG.error(
                    "Cannot instantiate DMR Connection object because "
                            + e.getMessage(), e);
            throw new IOException(e);
        }
    }

    /**
     * Close.
     *
     * @throws IOException
     *             Signals that an I/O exception has occurred.
     * @see java.io.Closeable#close()
     */
    @Override
    public void close() throws IOException {
        client.close();
    }

    /**
     * Extract data.
     *
     * @return the string[]
     * @throws IOException
     *             Signals that an I/O exception has occurred.
     * @see com.redhat.it.customers.dmc.core.services.connection.QueryExecutor#extractData()
     */
    @Override
    public String[] extractData() throws IOException {

        Set<String> hosts = getHosts(client);
        if (LOG.isInfoEnabled()) {
            LOG.info("main(String[]) - Set<String> hosts={}", hosts);
        }

        for (String host : hosts) {
            if (patternHostname.matcher(host).matches()) {
                Set<String> servers = getServers(client, host);
                if (LOG.isInfoEnabled()) {
                    LOG.info("main(String[]) - Set<String> servers={}", servers);
                }

                for (String server : servers) {
                    if (patternServer.matcher(server).matches()) {
                        analyzeServer(host, server);
                    }
                }
            }
        }
        return null;
    }

    /**
     * Analyze server.
     *
     * @param host
     *            the host
     * @param server
     *            the server
     * @throws IOException
     *             Signals that an I/O exception has occurred.
     */
    protected abstract void analyzeServer(String host, String server)
            throws IOException;

    /**
     * Gets the hosts.
     *
     * @param client
     *            the client
     * @return the hosts
     * @throws IOException
     *             Signals that an I/O exception has occurred.
     */
    protected Set<String> getHosts(ModelControllerClient client)
            throws IOException {
        ModelNode op = new ModelNode();
        op.get("operation").set("read-resource");
        op.get("operations").set(true);
        ModelNode response = client.execute(op);

        return response.get("result").get("host").keys();
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
     */
    protected Set<String> getServers(ModelControllerClient client, String host)
            throws IOException {
        ModelNode op = new ModelNode();
        op.get("operation").set("read-resource");
        op.get("operations").set(true);
        ModelNode address = op.get("address");
        address.add("host", host);

        ModelNode response = client.execute(op);

        return response.get("result").get("server").keys();
    }

    /**
     * Gets the deployments.
     *
     * @param client
     *            the client
     * @param host
     *            the host
     * @param server
     *            the server
     * @return the deployments
     * @throws IOException
     *             Signals that an I/O exception has occurred.
     */
    protected Set<String> getDeployments(ModelControllerClient client,
            String host, String server) throws IOException {
        ModelNode op = new ModelNode();
        op.get("operation").set("read-resource");
        op.get("operations").set(true);
        ModelNode address = op.get("address");
        address.add("host", host);
        address.add("server", server);

        ModelNode response = client.execute(op);

        return response.get("result").get("deployment").keys();
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
    protected ModelNode getAllStatistics(ModelControllerClient client,
            String host, String server, String deployment) throws IOException {
        ModelNode op = new ModelNode();
        op.get("operation").set("read-resource");
        op.get("operations").set(true);
        op.get("include-runtime").set(true);
        op.get("recursive").set(true);
        ModelNode address = op.get("address");
        address.add("host", host);
        address.add("server", server);
        address.add("deployment", deployment);

        return client.execute(op);
    }

}
