package com.redhat.it.customers.dmc.core.services.connection.impl;

import java.io.IOException;
import java.util.Date;
import java.util.Set;

import javax.inject.Inject;

import org.jboss.dmr.ModelNode;
import org.jboss.weld.environment.se.contexts.ThreadScoped;
import org.slf4j.Logger;

// TODO: Auto-generated Javadoc
/**
 * The Class AppDMRQueryExecutorImpl.
 * 
 * @author Andrea Battaglia
 */
@ThreadScoped
public class AppDMRQueryExecutorImpl extends AbstractDMRQueryExecutorImpl {

    /** Logger for this class. */
    @Inject
    private Logger LOG;

    /** The apps. */
    protected Set<String> apps;

    /**
     * Sets the apps.
     *
     * @param apps
     *            the new apps
     */
    public void setApps(Set<String> apps) {
        this.apps = apps;
    }

    
    /**
     * @see com.redhat.it.customers.dmc.core.services.connection.impl.AbstractDMRQueryExecutorImpl#analyzeServer(java.lang.String, java.lang.String)
     */
    @Override
    protected void analyzeServer(String host, String server) throws IOException {
        Set<String> deployments = getDeployments(client, host, server);
        if (LOG.isInfoEnabled()) {
            LOG.info("main(String[]) - Set<String> deployments={}", deployments);
        }

        for (String deployment : deployments) {
            if (apps.contains(deployment)) {
                ModelNode statistics = getAllStatistics(client, host, server,
                        deployment);
                if (LOG.isInfoEnabled()) {
                    LOG.info("main(String[]) - ModelNode statistics={}",
                            statistics);
                }

                printStatistics(statistics, host, server, deployment);
            }
        }
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
    protected void printStatistics(ModelNode response, String host,
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
