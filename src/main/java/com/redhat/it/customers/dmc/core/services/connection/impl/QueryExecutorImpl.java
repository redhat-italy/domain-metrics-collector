package com.redhat.it.customers.dmc.core.services.connection.impl;

import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Set;
import java.util.regex.Pattern;

import javax.inject.Inject;

import org.jboss.as.controller.client.ModelControllerClient;
import org.jboss.dmr.ModelNode;
import org.jboss.weld.environment.se.contexts.ThreadScoped;
import org.slf4j.Logger;

import com.redhat.it.customers.dmc.core.services.connection.QueryExecutor;

/**
 * The Class QueryExecutorImpl.
 *
 * @author Andrea Battaglia
 */
@ThreadScoped
class QueryExecutorImpl implements QueryExecutor {
    private static final DateFormat DATE_FORMATTER = new SimpleDateFormat(
            "yyyy-MM-dd'T'HH:mm:ssZ");

    /** Logger for this class. */
    @Inject
    private Logger LOG;

    /** The client. */
    private ModelControllerClient client;

    /** The hostname. */
    private String hostname;

    /** The port. */
    private int port;

    /** The username. */
    private String username;

    /** The password. */
    private String password;

    /** The realm. */
    private String realm;

    /** The pattern hostname. */
    private Pattern patternHostname;

    /** The pattern server. */
    private Pattern patternServer;

    /** The apps. */
    private Set<String> apps;

    /**
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
     * @see java.io.Closeable#close()
     */
    @Override
    public void close() throws IOException {
        client.close();
    }

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
     * Sets the apps.
     *
     * @param apps
     *            the new apps
     */
    @Override
    public void setApps(Set<String> apps) {
        this.apps = apps;
    }

    /**
     * @see com.redhat.it.customers.dmc.core.services.connection.QueryExecutor#extractHeader()
     */
    @Override
    public String[] extractHeader() throws IOException {
        return null;
    }

    /**
     * @see com.redhat.it.customers.dmc.core.services.connection.QueryExecutor#extractData()
     */
    @Override
    public String[] extractData() throws IOException {

        Set<String> hosts = getHosts(client);

        for (String host : hosts) {
            if ((patternHostname == null)
                    || patternHostname.matcher(host).matches()) {
                Set<String> servers = getServers(client, host);
                for (String server : servers) {
                    if ((patternServer == null)
                            || patternServer.matcher(server).matches()) {
                        Set<String> deployments = getDeployments(client, host,
                                server);
                        for (String deployment : deployments) {
                            if (apps.contains(deployment.substring(0,
                                    deployment.lastIndexOf('-')))) {
                                ModelNode statistics = getAllStatistics(client,
                                        host, server, deployment);
                                printStatistics(statistics, host, server,
                                        deployment);
                            }
                        }
                    }
                }
            }
        }

        return null;
    }

    /**
     * Gets the hosts.
     *
     * @param client
     *            the client
     * @return the hosts
     * @throws IOException
     *             Signals that an I/O exception has occurred.
     */
    private Set<String> getHosts(ModelControllerClient client)
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
    private Set<String> getServers(ModelControllerClient client, String host)
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
    private Set<String> getDeployments(ModelControllerClient client,
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
    private ModelNode getAllStatistics(ModelControllerClient client,
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

    /**
     * Prints the statistics.
     *
     * @param response
     *            the response
     * @param host
     *            the host
     * @param server
     *            the server
     * @param deploy
     *            the deploy
     */
    private void printStatistics(ModelNode response, String host,
            String server, String deploy) {
        String now = DATE_FORMATTER.format(new Date());

        for (String subdeployment : response.get("result").get("subdeployment")
                .keys()) {
            if (response.get("result").get("subdeployment").get(subdeployment)
                    .get("subsystem").has("ejb3")) {
                for (String stateful : response.get("result")
                        .get("subdeployment").get(subdeployment)
                        .get("subsystem").get("ejb3")
                        .get("stateful-session-bean").keys()) {
                    for (String method : response.get("result")
                            .get("subdeployment").get(subdeployment)
                            .get("subsystem").get("ejb3")
                            .get("stateful-session-bean").get(stateful)
                            .get("methods").keys()) {
                        ModelNode methodNode = response.get("result")
                                .get("subdeployment").get(subdeployment)
                                .get("subsystem").get("ejb3")
                                .get("stateful-session-bean").get(stateful)
                                .get("methods").get(method);

                        System.out.print(now + ",");
                        System.out.print(host + ",");
                        System.out.print(server + ",");
                        System.out.print(deploy + ",");
                        System.out.print(subdeployment + ",");
                        System.out.print(stateful + ",");
                        System.out.print(method + ",");
                        System.out.print(methodNode.get("invocations").asLong()
                                + ",");
                        System.out.print(methodNode.get("execution-time")
                                .asLong() + ",");
                        System.out.print(methodNode.get("wait-time").asLong());
                        System.out.println();
                    }
                }
            }
        }
    }

}
