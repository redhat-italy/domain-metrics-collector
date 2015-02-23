package com.redhat.it.customers.dmc.application;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.UnknownHostException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Properties;
import java.util.Set;
import java.util.regex.Pattern;

import javax.security.auth.callback.Callback;
import javax.security.auth.callback.CallbackHandler;
import javax.security.auth.callback.NameCallback;
import javax.security.auth.callback.PasswordCallback;
import javax.security.auth.callback.UnsupportedCallbackException;
import javax.security.sasl.RealmCallback;

import org.jboss.as.controller.client.ModelControllerClient;
import org.jboss.dmr.ModelNode;

public class EJBMonitor {
    /**
     * Logger for this class
     */
    private static final Logger LOG = LoggerFactory.getLogger(EJBMonitor.class);

    public static void main(String[] args) {
        System.setProperty("java.util.logging.config.file",
                "ejb-monitor-logging.properties");

        if (args.length != 1) {
            LOG.error("Usage: java ejb-monitor <configuration-file>");
            System.exit(-1);
        }

        Properties configuration = new Properties();
        try {
            configuration.load(new FileInputStream(args[0]));
        } catch (FileNotFoundException e) {
            LOG.error("Error: file not found ", e);
            System.exit(-1);
        } catch (IOException e) {
            LOG.error("Error: reading file ", e);
            System.exit(-1);
        }

        String hostname = configuration.getProperty("hostname", "localhost");
        if (LOG.isInfoEnabled()) {
            LOG.info("main(String[]) - String hostname={}", hostname);
        }

        int port = Integer.parseInt(configuration.getProperty("port", "9999"));
        if (LOG.isInfoEnabled()) {
            LOG.info("main(String[]) - int port={}", port);
        }

        String username = configuration.getProperty("username", "");
        if (LOG.isInfoEnabled()) {
            LOG.info("main(String[]) - String username={}", username);
        }

        String password = configuration.getProperty("password", "");
        if (LOG.isInfoEnabled()) {
            LOG.info("main(String[]) - String password={}", password);
        }

        String realm = configuration.getProperty("realm", "");
        if (LOG.isInfoEnabled()) {
            LOG.info("main(String[]) - String realm={}", realm);
        }

        String regexpHostname = configuration.getProperty("regexpHostname",
                ".*");
        if (LOG.isInfoEnabled()) {
            LOG.info(
                    "main(String[]) - String regexpHostname={}", regexpHostname);
        }

        String regexpServer = configuration.getProperty("regexpServer", ".*");
        if (LOG.isInfoEnabled()) {
            LOG.info("main(String[]) - String regexpServer={}", regexpServer);
        }

        String appsProperty = configuration.getProperty("apps", "");
        if (LOG.isInfoEnabled()) {
            LOG.info("main(String[]) - String appsProperty={}", appsProperty);
        }

        List<String> apps = Arrays.asList(appsProperty.split(", *"));
        if (LOG.isInfoEnabled()) {
            LOG.info("main(String[]) - List<String> apps={}", apps);
        }

        Pattern patternHostname = Pattern.compile(regexpHostname);
        Pattern patternServer = Pattern.compile(regexpServer);

        LOG.info("date,host,server,deploy,subdeploy,ejb,method,invocations,execution-time,wait-time");

        ModelControllerClient client = null;
        try {
            client = connect(hostname, port, username, password, realm);

            Set<String> hosts = getHosts(client);
            if (LOG.isInfoEnabled()) {
                LOG.info("main(String[]) - Set<String> hosts={}", hosts);
            }

            for (String host : hosts) {
                if (patternHostname.matcher(host).matches()) {
                    Set<String> servers = getServers(client, host);
                    if (LOG.isInfoEnabled()) {
                        LOG.info(
                                "main(String[]) - Set<String> servers={}", servers);
                    }

                    for (String server : servers) {
                        if (patternServer.matcher(server).matches()) {
                            Set<String> deployments = getDeployments(client,
                                    host, server);
                            if (LOG.isInfoEnabled()) {
                                LOG.info(
                                        "main(String[]) - Set<String> deployments={}", deployments);
                            }

                            for (String deployment : deployments) {
                                if (apps.contains(deployment)) {
                                    ModelNode statistics = getAllStatistics(
                                            client, host, server, deployment);
                                    if (LOG.isInfoEnabled()) {
                                        LOG.info(
                                                "main(String[]) - ModelNode statistics={}", statistics);
                                    }

                                    printStatistics(statistics, host, server,
                                            deployment);
                                }
                            }
                        }
                    }
                }
            }
        } catch (UnknownHostException e) {
            LOG.error("Error: unknown hostname ", e);
        } catch (IOException e) {
            LOG.error("Error: i/o error ", e);
        } finally {
            if (client != null) {
                try {
                    disconnect(client);
                } catch (IOException e) {
                    LOG.error("Error: i/o error ", e);
                }
            }
        }
    }

    private static ModelControllerClient connect(final String host,
            final int port, final String username, final String password,
            final String securityRealmName) throws UnknownHostException {
        final CallbackHandler callbackHandler = new CallbackHandler() {
            @Override
            public void handle(Callback[] callbacks) throws IOException,
                    UnsupportedCallbackException {
                for (Callback current : callbacks) {
                    if (current instanceof NameCallback) {
                        NameCallback ncb = (NameCallback) current;
                        ncb.setName(username);
                    } else if (current instanceof PasswordCallback) {
                        PasswordCallback pcb = (PasswordCallback) current;
                        pcb.setPassword(password.toCharArray());
                    } else if (current instanceof RealmCallback) {
                        RealmCallback rcb = (RealmCallback) current;
                        rcb.setText(rcb.getDefaultText());
                    } else {
                        throw new UnsupportedCallbackException(current);
                    }
                }
            }
        };

        return ModelControllerClient.Factory
                .create(host, port, callbackHandler);
    }

    private static void disconnect(ModelControllerClient client)
            throws IOException {
        client.close();
    }

    private static Set<String> getHosts(ModelControllerClient client)
            throws IOException {
        ModelNode op = new ModelNode();
        op.get("operation").set("read-resource");
        op.get("operations").set(true);
        ModelNode response = client.execute(op);

        return response.get("result").get("host").keys();
    }

    private static Set<String> getServers(ModelControllerClient client,
            String host) throws IOException {
        ModelNode op = new ModelNode();
        op.get("operation").set("read-resource");
        op.get("operations").set(true);
        ModelNode address = op.get("address");
        address.add("host", host);

        ModelNode response = client.execute(op);

        return response.get("result").get("server").keys();
    }

    private static Set<String> getDeployments(ModelControllerClient client,
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

    private static ModelNode getAllStatistics(ModelControllerClient client,
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

    private static void printStatistics(ModelNode response, String host,
            String server, String deploy) {
        if (LOG.isInfoEnabled()) {
            LOG.info("printStatistics(ModelNode response={}, String host={}, String server={}, String deploy={}) - start", response, host, server, deploy);
        }

        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssZ");
        String now = dateFormat.format(new Date());

        for (String subdeployment : response.get("result").get("subdeployment")
                .keys()) {
            if (response.get("result").get("subdeployment").get(subdeployment)
                    .get("subsystem").has("ejb3")) {

                LOG.info("Subsystem \"eb3\" components: {}", response.get("result")
                        .get("subdeployment").get(subdeployment)
                        .get("subsystem").get("ejb3") );
                
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
                        StringBuffer sb = new StringBuffer();
                        sb.append(now + ",");
                        sb.append(host + ",");
                        sb.append(server + ",");
                        sb.append(deploy + ",");
                        sb.append(subdeployment + ",");
                        sb.append(stateful + ",");
                        sb.append(method + ",");
                        sb.append(methodNode.get("invocations").asLong() + ",");
                        sb.append(methodNode.get("execution-time").asLong()
                                + ",");
                        sb.append(methodNode.get("wait-time").asLong());
                        LOG.info(sb.toString());
                    }
                }
            }
        }

        if (LOG.isInfoEnabled()) {
            LOG.info("printStatistics(ModelNode, String, String, String) - end");
        }
    }
}
