package com.redhat.it.customers.dmc.core.services.connection.impl;

import java.io.IOException;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.regex.Pattern;

import javax.inject.Inject;

import org.codehaus.jackson.annotate.JsonUnwrapped;
import org.jboss.dmr.ModelNode;
import org.jboss.dmr.Property;
import org.slf4j.Logger;

import com.redhat.it.customers.dmc.core.dto.configuration.AppObjectAttributeConfiguration;
import com.redhat.it.customers.dmc.core.util.JsonFunctions;

/**
 * The Class AppDMRQueryExecutorImpl.
 * 
 * @author Andrea Battaglia
 */
public class AppDMRQueryExecutorImpl extends AbstractDMRQueryExecutorImpl {

    /** Logger for this class. */
    @Inject
    private Logger LOG;

    @Inject
    private JsonFunctions jsonFunctions;

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
    public void setApps(Set<String> apps) {
        this.apps = apps;
    }

    /**
     * @return the patternSubdeployment
     */
    public Pattern getPatternpSubdeployment() {
        return patternSubdeployment;
    }

    /**
     * @param patternSubdeployment
     *            the patternSubdeployment to set
     */
    public void setPatternpSubdeployment(Pattern patternSubdeployment) {
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
     * Gets the pattern subsystem components.
     *
     * @return the pattern subsystem components
     */
    public Pattern getPatternSubsystemComponents() {
        return patternSubsystemComponents;
    }

    /**
     * Sets the pattern subsystem components.
     *
     * @param patternSubsystemComponents
     *            the new pattern subsystem components
     */
    public void setPatternSubsystemComponents(Pattern patternSubsystemComponents) {
        this.patternSubsystemComponents = patternSubsystemComponents;
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

    /**
     * @see com.redhat.it.customers.dmc.core.services.connection.impl.AbstractDMRQueryExecutorImpl#analyzeServer(java.lang.String,
     *      java.lang.String)
     */
    @Override
    protected void analyzeServer(String host, String server) throws IOException {
        Set<String> deployments = getDeployments(host, server);
        if (LOG.isDebugEnabled()) {
            LOG.debug("main(String[]) - Set<String> deployments={}",
                    deployments);
        }

        for (String deployment : deployments) {
            if (apps.contains(deployment)) {
                 ModelNode statistics = getAllStatistics(client, host, server,
                 deployment);
                 LOG.info(jsonFunctions.createFlatKeyValues(statistics)
                         .toString());
                // if (LOG.isDebugEnabled()) {
                // LOG.debug("main(String[]) - ModelNode statistics={}",
                // statistics);
                // }
                //
                // extractStatistics(statistics, host, server, deployment);
            }
        }
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
     */
    protected void extractStatistics(ModelNode response, String host,
            String server, String deploy) {
        Map<String, Object> statistics = null;
        String now = null;
        ModelNode modelNodeResult = null;
        ModelNode modelNodeSubdeployment = null;
        ModelNode modelNodeSubsystems = null;
        ModelNode modelNodeSubsystem = null;
        ModelNode modelNodeSubsystemComponent = null;
        ModelNode modelNodeAppObject = null;

        now = DATE_FORMATTER.format(new Date());
        statistics = new LinkedHashMap<String, Object>();
        modelNodeResult = response.get("result");

        for (String subdeployment : modelNodeResult.get("subdeployment").keys()) {
            if (LOG.isDebugEnabled()) {
                LOG.debug(
                        "extractStatistics(ModelNode response={}, String host={}, String server={}, String deploy={}) - String subdeployment={}",
                        response, host, server, deploy, subdeployment);
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

                    LOG.info(modelNodeSubsystem.toJSONString(false));

                    // for (String subsystemComponentName : modelNodeSubsystem
                    // .keys()) {
                    // if (patternSubsystemComponents.matcher(
                    // subsystemComponentName).matches()) {
                    // // get matching subsystem component
                    // modelNodeSubsystemComponent = modelNodeSubsystem
                    // .get(subsystemComponentName);
                    // // search for matching app objects
                    // for (String appObjectName : modelNodeSubsystemComponent
                    // .keys()) {
                    // if (patternAppObjectName.matcher(appObjectName)
                    // .matches()) {
                    // // analyze the matching app object
                    // statistics.put("app.object.name",
                    // appObjectName);
                    // modelNodeAppObject = modelNodeSubsystemComponent
                    // .get(appObjectName);
                    // int depthCounter = 0;
                    // extractAppObjectStatistics(depthCounter,
                    // "", modelNodeAppObject, statistics);
                    // }
                    // }
                    // // TODO: remove
                    // // for (String stateful : modelNodeSubsystem
                    // // .get(subsystem)
                    // // .get("stateful-session-bean").keys()) {
                    // // for (String method : modelNodeSubsystem
                    // // .get(subsystem)
                    // // .get("stateful-session-bean")
                    // // .get(stateful).get("methods").keys()) {
                    // // ModelNode methodNode = modelNodeSubsystem
                    // // .get(subsystem)
                    // // .get("stateful-session-bean")
                    // // .get(stateful).get("methods")
                    // // .get(method);
                    // // // TODO send statistics event!!!!
                    // // return;
                    // // }
                    // // }
                    // }
                    // }
                } else {
                    LOG.warn("requested subsystem {} not found.", subsystem);
                }
            }
        }
    }

    private void extractAppObjectStatistics(int depthCounter, String prefix,
            ModelNode modelNodeAppObject, Map<String, Object> statistics) {
        if (depthCounter > depth) {
            return;
        }

        AppObjectAttributeConfiguration appObjectAttributeConfiguration = null;
        Set<String> attributeSet = null;

        appObjectAttributeConfiguration = appObjectAttributeConfigurations
                .get(depthCounter);
        attributeSet = new LinkedHashSet<String>(modelNodeAppObject.keys());
        if (appObjectAttributeConfiguration != null) {
            if (appObjectAttributeConfiguration.isAppObjectAttributeExclude()) {
                for (String attribute : appObjectAttributeConfiguration
                        .getAppObjectAttributes()) {
                    attributeSet.remove(attribute);
                }
            } else {
                attributeSet = new LinkedHashSet<String>(
                        appObjectAttributeConfiguration
                                .getAppObjectAttributes());
            }
        }

        // lavoro di estrazione
        for (String attribute : attributeSet) {
            ModelNode modelNodeDetail = modelNodeAppObject.get(attribute);
            if (modelNodeDetail.isDefined()) {
                saveNodeProperties(modelNodeDetail, statistics);
            }
        }
        // alla fine incremento il counter della profondità
        depthCounter++;
    }

    private void saveNodeProperties(ModelNode modelNode,
            Map<String, Object> statistics) {
        List<Property> modelNodeProperties = modelNode.asPropertyList();
        for (Property property : modelNodeProperties) {
            statistics.put(property.getName(), property.getValue().asObject());
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