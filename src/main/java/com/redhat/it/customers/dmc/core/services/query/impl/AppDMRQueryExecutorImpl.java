/*
 *
 */
package com.redhat.it.customers.dmc.core.services.query.impl;

import java.io.IOException;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.Set;
import java.util.regex.Pattern;

import javax.inject.Inject;

import org.jboss.dmr.ModelNode;
import org.slf4j.Logger;

import com.redhat.it.customers.dmc.core.dto.collector.qd.raw.DMRRawQueryData;
import com.redhat.it.customers.dmc.core.dto.collector.qdk.raw.AppDMRRawQueryDataKey;
import com.redhat.it.customers.dmc.core.exceptions.DMCQueryException;

/**
 * The Class AppDMRQueryExecutorImpl.
 *
 * @author Andrea Battaglia
 */
public class AppDMRQueryExecutorImpl extends AbstractDMRQueryExecutorImpl {

    /** Logger for this class. */
    @Inject
    private Logger LOG;

    // @Inject
    // private JsonFunctions jsonFunctions;

    /** The apps. */
    protected Pattern patternApps;

    /** The pattern subdeployment. */
    protected Pattern patternSubdeployment;

    protected Pattern patternSubsystemComponents;
    /** The app object name pattern. */
    protected Pattern patternAppObjectName;

    public Pattern getPatternApps() {
        return patternApps;
    }

    public void setPatternApps(Pattern patternApps) {
        this.patternApps = patternApps;
    }

    public Pattern getPatternSubdeployment() {
        return patternSubdeployment;
    }

    public void setPatternSubsystemComponents(Pattern patternSubsystemComponents) {
        this.patternSubsystemComponents = patternSubsystemComponents;
    }

    public Pattern getPatternSubsystemComponents() {
        return patternSubsystemComponents;
    }

    public void setPatternSubdeployment(Pattern patternSubdeployment) {
        this.patternSubdeployment = patternSubdeployment;
    }

    /**
     * @return the patternAppObjectName
     */
    public Pattern getPatternAppObjectName() {
        return patternAppObjectName;
    }

    /**
     * @param patternAppObjectName
     *            the patternAppObjectName to set
     */
    public void setPatternAppObjectName(Pattern patternAppObjectName) {
        this.patternAppObjectName = patternAppObjectName;
    }

    @Override
    protected void analyzeServer(final long timestamp, final String host,
            final String server, final DMRRawQueryData rawData)
            throws DMCQueryException {
        Set<String> deployments = null;

        try {
            deployments = getDeployments(host, server);
        } catch (IOException e) {
            throw new DMCQueryException(e);
        }

        for (String deployment : deployments) {
            if (patternApps.matcher(deployment).matches()) {
                ModelNode deploymentStatistics;
                try {
                    deploymentStatistics = getDeploymentStatistics(host,
                            server, deployment);
                } catch (IOException e) {
                    throw new DMCQueryException(e);
                }
                // try {
                // LOG.info(jsonFunctions.createFlatKeyValues(deploymentStatistics)
                // .toString());
                // } catch (IOException e) {
                // throw new DMCQueryException(e);
                // }
                if (LOG.isDebugEnabled()) {
                    LOG.debug("main(String[]) - ModelNode statistics={}",
                            deploymentStatistics.toJSONString(false));
                }

                analyzeDeployment(timestamp, host, server, deployment,
                        deploymentStatistics, rawData);
            }
        }
        deployments = null;
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

        response = mcClient.execute(op);
        op = null;

        return response;
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
    protected Set<String> getDeployments(String host, String server)
            throws IOException {
        ModelNode op = null;
        ModelNode address = null;
        ModelNode response = null;
        ModelNode result = null;
        ModelNode deployment = null;
        Set<String> deploymentList = null;

        op = new ModelNode();
        op.get("operation").set("read-resource");
        op.get("operations").set(true);
        address = op.get("address");
        address.add("host", host);
        address.add("server", server);

        response = mcClient.execute(op);

        result = response.get("result");
        response = null;
        if (!result.has("deployment")) {
            return new HashSet<>();
        }
        deployment = result.get("deployment");
        result = null;
        deploymentList = deployment.keys();
        deployment = null;
        return deploymentList;
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
     * @param deployment
     */

    private void analyzeDeployment(final long timestamp, final String host,
            final String server, final String deploy,
            final ModelNode deploymentStatistics, DMRRawQueryData rawData) {
        ModelNode subdeployment = null;
        ModelNode modelNodeResult = null;
        ModelNode modelNodeSubdeployment = null;
        ModelNode modelNodeSubsystems = null;
        ModelNode modelNodeSubsystem = null;

        modelNodeResult = deploymentStatistics.get("result");

        subdeployment = modelNodeResult.get("subdeployment");
        for (String subdeploymentName : subdeployment.keys()) {
            if (LOG.isDebugEnabled()) {
                LOG.debug("extractStatistics("
                        + "String configurationId = {}, String host={}, "
                        + "String server={}, String deploy={}) - "
                        + "String subdeployment={}, ModelNode response={}",
                        configurationId, host, server, deploy,
                        subdeploymentName, deploymentStatistics);
            }
            // foreach subdeployment
            if (patternSubdeployment.matcher(subdeploymentName).matches()) {
                modelNodeSubdeployment = modelNodeResult.get("subdeployment")
                        .get(subdeploymentName);
                // one single subsystem
                // get subsystem tag
                modelNodeSubsystems = modelNodeSubdeployment.get("subsystem");
                // search for requested subsystem
                if (modelNodeSubsystems.has(subsystem)) {
                    // get requested subsystem
                    modelNodeSubsystem = modelNodeSubsystems.get(subsystem);
                    // search for subsystem components

                    if (LOG.isDebugEnabled()) {
                        LOG.debug(modelNodeSubsystem.toJSONString(false));
                    }

                    cleanRawData(modelNodeSubsystem);
                    rawData.put(new AppDMRRawQueryDataKey(configurationId,
                            timestamp, host, server, deploy, subdeploymentName,
                            subsystem), modelNodeSubsystem);
                } else {
                    LOG.warn("requested subsystem {} not found.", subsystem);
                }
            }
        }
    }

    private void cleanRawData(ModelNode modelNodeSubsystem) {
        ModelNode modelNodeSubsystemComponent = null;
        ModelNode removed = null;

        for (String subsystemComponentName : new LinkedHashSet<>(
                modelNodeSubsystem.keys())) {
            if (patternSubsystemComponents.matcher(subsystemComponentName)
                    .matches()) {
                modelNodeSubsystemComponent = modelNodeSubsystem
                        .get(subsystemComponentName);
                if (modelNodeSubsystemComponent.isDefined()) {
                    cleanAppObjects(modelNodeSubsystemComponent);
                } else {
                    removed = modelNodeSubsystem.remove(subsystemComponentName);
                }
            } else {
                removed = modelNodeSubsystem.remove(subsystemComponentName);
                if (LOG.isDebugEnabled()) {
                    LOG.debug("removing modelNode {}",
                            removed.toJSONString(false));
                }
            }
        }
    }

    private void cleanAppObjects(ModelNode modelNodeSubsystemComponent) {
        ModelNode removed = null;

        // search for matching app objects
        for (String appObjectName : new LinkedHashSet<>(
                modelNodeSubsystemComponent.keys())) {
            if (!patternAppObjectName.matcher(appObjectName).matches()) {
                removed = modelNodeSubsystemComponent.remove(appObjectName);
                if (LOG.isDebugEnabled()) {
                    LOG.debug(removed.toJSONString(false));
                }
            }

        }
    }
}

// System.out.print(now + ",");
// System.out.print(host + ",");
// System.out.print(server + ",");
// System.out.print(deploy + ",");
// System.out.print(subdeployment + ",");
// System.out.print(stateful + ",");
// System.out.print(method + ",");
// System.out.print(methodNode.get("invocations").asLong() + ",");
// System.out.print(methodNode.get("execution-time").asLong() + ",");
// System.out.print(methodNode.get("wait-time").asLong());
// System.out.println();