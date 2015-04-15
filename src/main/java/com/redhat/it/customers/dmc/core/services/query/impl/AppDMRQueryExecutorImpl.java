package com.redhat.it.customers.dmc.core.services.query.impl;

import java.io.IOException;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.Map;
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

//    @Inject
//    private JsonFunctions jsonFunctions;

    /** The apps. */
    protected Set<String> apps;

    /** The pattern subdeployment. */
    protected Pattern patternSubdeployment;

    /** The subsystem. */
    protected String subsystem;

    protected Pattern patternSubsystemComponents;
    /** The app object name pattern. */
    protected Pattern patternAppObjectName;

    /**
     * @return the apps
     */
    public Set<String> getApps() {
        return apps;
    }

    /**
     * @param apps
     *            the apps to set
     */
    public void setApps(final Set<String> apps) {
        this.apps = apps;
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
            if (apps.contains(deployment)) {
                ModelNode deploymentStatistics;
                try {
                    deploymentStatistics = getDeploymentStatistics(host, server,
                            deployment);
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
        Map<String, Object> statistics = null;
        ModelNode modelNodeResult = null;
        ModelNode modelNodeSubdeployment = null;
        ModelNode modelNodeSubsystems = null;
        ModelNode modelNodeSubsystem = null;
        ModelNode modelNodeSubsystemComponent = null;
        ModelNode modelNodeAppObject = null;

        statistics = new LinkedHashMap<String, Object>();
        modelNodeResult = deploymentStatistics.get("result");

        for (String subdeployment : modelNodeResult.get("subdeployment").keys()) {
            if (LOG.isDebugEnabled()) {
                LOG.debug(
                        "extractStatistics(ModelNode response={}, String host={}, "
                                + "String server={}, String deploy={}) - String subdeployment={}",
                        deploymentStatistics, host, server, deploy,
                        subdeployment);
            }
            // foreach subdeployment
            if (patternSubdeployment.matcher(subdeployment).matches()) {
                modelNodeSubdeployment = modelNodeResult.get("subdeployment")
                        .get(subdeployment);
                // one single subsystem
                // get subsystem tag
                modelNodeSubsystems = modelNodeSubdeployment.get("subsystem");
                // search for requested subsystem
                if (modelNodeSubsystems.has(subsystem)) {
                    // get requested subsystem
                    modelNodeSubsystem = modelNodeSubsystems.get(subsystem);
                    // search for subsystem components

                    if (LOG.isDebugEnabled())
                        LOG.debug(modelNodeSubsystem.toJSONString(false));

                    cleanRawData(modelNodeSubsystem);
                    rawData.put(new AppDMRRawQueryDataKey(configurationId,
                            timestamp, host, server, deploy, subdeployment,
                            subsystem), modelNodeSubsystem);
                } else {
                    LOG.warn("requested subsystem {} not found.", subsystem);
                }
            }
        }
    }

    private void cleanRawData(ModelNode modelNodeSubsystem) {
        ModelNode removed = null;

        for (String subsystemComponentName : new LinkedHashSet<>(modelNodeSubsystem.keys())) {
            if (patternSubsystemComponents.matcher(subsystemComponentName)
                    .matches()) {
                cleanAppObjects(modelNodeSubsystem.get(subsystemComponentName));
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
        for (String appObjectName : new LinkedHashSet<>(modelNodeSubsystemComponent.keys())) {
            if (!patternAppObjectName.matcher(appObjectName).matches()) {
                removed = modelNodeSubsystemComponent.remove(appObjectName);
                if (LOG.isDebugEnabled())
                    LOG.debug(removed.toJSONString(false));
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