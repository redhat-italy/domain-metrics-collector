package com.redhat.it.customers.dmc.core.services.connection.impl;

import java.io.IOException;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;
import java.util.regex.Pattern;

import javax.inject.Inject;

import org.jboss.dmr.ModelNode;
import org.slf4j.Logger;

/**
 * The Class AppDMRQueryExecutorImpl.
 * 
 * @author Andrea Battaglia
 */
public class AppDMRQueryExecutorImpl extends AbstractDMRQueryExecutorImpl {

    /** Logger for this class. */
    @Inject
    private Logger LOG;

    /** The apps. */
    protected Set<String> apps;

    /** The pattern subdeployment. */
    protected Pattern patternSubdeployment;

    /** The subsystem. */
    protected String subsystem;

    protected Pattern patternSubsystemComponents;
    /** The app object name pattern. */
    protected Pattern patternAppObjectName;

    /** The app object attributes. */
    protected Set<String> appObjectAttributes;

    /** The app object attribute exclude. */
    protected boolean appObjectAttributeExclude;

    /** The app object detail. */
    protected String appObjectDetail;

    /** The app object detail attributes. */
    protected Set<String> appObjectDetailAttributes;

    /** The app object detail attribute exclude. */
    protected boolean appObjectDetailAttributeExclude;

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
     * @return the appObjectAttributes
     */
    public Set<String> getAppObjectAttributes() {
        return appObjectAttributes;
    }

    /**
     * @param appObjectAttributes
     *            the appObjectAttributes to set
     */
    public void setAppObjectAttributes(Set<String> appObjectAttributes) {
        this.appObjectAttributes = appObjectAttributes;
    }

    /**
     * @return the appObjectAttributeExclude
     */
    public boolean isAppObjectAttributeExclude() {
        return appObjectAttributeExclude;
    }

    /**
     * @param appObjectAttributeExclude
     *            the appObjectAttributeExclude to set
     */
    public void setAppObjectAttributeExclude(boolean appObjectAttributeExclude) {
        this.appObjectAttributeExclude = appObjectAttributeExclude;
    }

    /**
     * @return the appObjectDetail
     */
    public String getAppObjectDetail() {
        return appObjectDetail;
    }

    /**
     * @param appObjectDetail
     *            the appObjectDetail to set
     */
    public void setAppObjectDetail(String appObjectDetail) {
        this.appObjectDetail = appObjectDetail;
    }

    /**
     * @return the appObjectDetailAttributes
     */
    public Set<String> getAppObjectDetailAttributes() {
        return appObjectDetailAttributes;
    }

    /**
     * @param appObjectDetailAttributes
     *            the appObjectDetailAttributes to set
     */
    public void setAppObjectDetailAttributes(
            Set<String> appObjectDetailAttributes) {
        this.appObjectDetailAttributes = appObjectDetailAttributes;
    }

    /**
     * @return the appObjectDetailAttributeExclude
     */
    public boolean isAppObjectDetailAttributeExclude() {
        return appObjectDetailAttributeExclude;
    }

    /**
     * @param appObjectDetailAttributeExclude
     *            the appObjectDetailAttributeExclude to set
     */
    public void setAppObjectDetailAttributeExclude(
            boolean appObjectDetailAttributeExclude) {
        this.appObjectDetailAttributeExclude = appObjectDetailAttributeExclude;
    }

    /**
     * @see com.redhat.it.customers.dmc.core.services.connection.impl.AbstractDMRQueryExecutorImpl#analyzeServer(java.lang.String,
     *      java.lang.String)
     */
    @Override
    protected void analyzeServer(String host, String server) throws IOException {
        Set<String> deployments = getDeployments(host, server);
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

                extractStatistics(statistics, host, server, deployment);
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
        ModelNode modelNodeSubsystem = null;
        ModelNode modelNodeSubsystemComponent = null;
        ModelNode modelNodeAppObject = null;

        now = DATE_FORMATTER.format(new Date());
        statistics = new LinkedHashMap<String, Object>();
        modelNodeResult = response.get("result");

        for (String subdeployment : modelNodeResult.get("subdeployment").keys()) {
            if (LOG.isInfoEnabled()) {
                LOG.info(
                        "extractStatistics(ModelNode response={}, String host={}, String server={}, String deploy={}) - String subdeployment={}",
                        response, host, server, deploy, subdeployment);
            }
            if (patternSubdeployment.matcher(subdeployment).matches()) {
                modelNodeSubdeployment = modelNodeResult.get("subdeployment")
                        .get(subdeployment);
                modelNodeSubsystem = modelNodeSubdeployment.get("subsystem");
                if (modelNodeSubsystem.has(subsystem)) {
                    LOG.info("Subsystem \"{}\" components: {}", subsystem,
                            modelNodeSubsystem.keys());
                    for (String subsystemComponentName : modelNodeSubsystem
                            .keys()) {
                        if (patternSubsystemComponents.matcher(
                                subsystemComponentName).matches()) {
                            modelNodeSubsystemComponent = modelNodeSubsystem
                                    .get(subsystemComponentName);
                            for (String appObjectName : modelNodeSubsystemComponent
                                    .keys()) {
                                if (patternAppObjectName.matcher(appObjectName)
                                        .matches()) {statistics.put("app.object.name", appObjectName);
                                    modelNodeAppObject = modelNodeSubsystemComponent
                                            .get(appObjectName);
                                    extractAppObjectStatistics(modelNodeAppObject, statistics);
                                }
                            }
                            //TODO: remove
//                            for (String stateful : modelNodeSubsystem
//                                    .get(subsystem)
//                                    .get("stateful-session-bean").keys()) {
//                                for (String method : modelNodeSubsystem
//                                        .get(subsystem)
//                                        .get("stateful-session-bean")
//                                        .get(stateful).get("methods").keys()) {
//                                    ModelNode methodNode = modelNodeSubsystem
//                                            .get(subsystem)
//                                            .get("stateful-session-bean")
//                                            .get(stateful).get("methods")
//                                            .get(method);
//                                    // TODO send statistics event!!!!
//                                    return;
//                                }
//                            }
                        }
                    }
                }
            }
        }
    }

    private void extractAppObjectStatistics(ModelNode modelNodeAppObject,
            Map<String, Object> statistics) {
        if(appObjectAttributeExclude){
            excludeAttributes( modelNodeAppObject,
             statistics);
        } else {
            includeAttributes(modelNodeAppObject,
                 statistics);
        }
    }

    private void includeAttributes(ModelNode modelNodeAppObject,
            Map<String, Object> statistics) {
        for(String appObjectAttributeName:modelNodeAppObject.keys()){
            
        }
        
    }

    private void excludeAttributes(ModelNode modelNodeAppObject,
            Map<String, Object> statistics) {
        for(String appObjectAttributeName:modelNodeAppObject.keys()){
            
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