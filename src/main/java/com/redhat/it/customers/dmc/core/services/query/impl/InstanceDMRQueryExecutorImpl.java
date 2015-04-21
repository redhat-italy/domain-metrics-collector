/*
 *
 */
package com.redhat.it.customers.dmc.core.services.query.impl;

import java.util.ArrayList;
import java.util.List;

import org.jboss.as.cli.scriptsupport.CLI.Result;
import org.jboss.dmr.ModelNode;

import com.google.common.base.Joiner;
import com.redhat.it.customers.dmc.core.constants.Constants;
import com.redhat.it.customers.dmc.core.dto.collector.qd.raw.DMRRawQueryData;
import com.redhat.it.customers.dmc.core.dto.collector.qdk.raw.AppDMRRawQueryDataKey;
import com.redhat.it.customers.dmc.core.dto.collector.qdk.raw.InstanceDMRRawQueryDataKey;
import com.redhat.it.customers.dmc.core.exceptions.DMCQueryException;

/**
 * The Class InstanceDMRQueryExecutorImpl.
 *
 * @author Andrea Battaglia
 */
// @ThreadScoped
public class InstanceDMRQueryExecutorImpl extends AbstractDMRQueryExecutorImpl {

    private String subsystemComponentKey;
    private String subsystemComponentValue;
    private String subsystemComponentAttributeKey;
    private String subsystemComponentAttributeValue;
    private boolean includeRuntime;
    private boolean recursive;

    /**
     * @return the subsystemComponentKey
     */
    public String getSubsystemComponentKey() {
        return subsystemComponentKey;
    }

    /**
     * @param subsystemComponentKey
     *            the subsystemComponentKey to set
     */
    public void setSubsystemComponentKey(String subsystemComponentKey) {
        this.subsystemComponentKey = subsystemComponentKey;
    }

    /**
     * @return the subsystemComponentValue
     */
    public String getSubsystemComponentValue() {
        return subsystemComponentValue;
    }

    /**
     * @param subsystemComponentValue
     *            the subsystemComponentValue to set
     */
    public void setSubsystemComponentValue(String subsystemComponentValue) {
        this.subsystemComponentValue = subsystemComponentValue;
    }

    /**
     * @return the subsystemComponentAttributeKey
     */
    public String getSubsystemComponentAttributeKey() {
        return subsystemComponentAttributeKey;
    }

    /**
     * @param subsystemComponentAttributeKey
     *            the subsystemComponentAttributeKey to set
     */
    public void setSubsystemComponentAttributeKey(
            String subsystemComponentAttributeKey) {
        this.subsystemComponentAttributeKey = subsystemComponentAttributeKey;
    }

    /**
     * @return the subsystemComponentAttributeValue
     */
    public String getSubsystemComponentAttributeValue() {
        return subsystemComponentAttributeValue;
    }

    /**
     * @param subsystemComponentAttributeValue
     *            the subsystemComponentAttributeValue to set
     */
    public void setSubsystemComponentAttributeValue(
            String subsystemComponentAttributeValue) {
        this.subsystemComponentAttributeValue = subsystemComponentAttributeValue;
    }

    /**
     * @return the includeRuntime
     */
    public boolean isIncludeRuntime() {
        return includeRuntime;
    }

    /**
     * @param includeRuntime
     *            the includeRuntime to set
     */
    public void setIncludeRuntime(boolean includeRuntime) {
        this.includeRuntime = includeRuntime;
    }

    /**
     * @return the recursive
     */
    public boolean isRecursive() {
        return recursive;
    }

    /**
     * @param recursive
     *            the recursive to set
     */
    public void setRecursive(boolean recursive) {
        this.recursive = recursive;
    }

    @Override
    protected void analyzeServer(final long timestamp, final String host,
            final String server, final DMRRawQueryData rawData)
            throws DMCQueryException {
        StringBuilder query = null;
        List<String> cliQueryComponents = null;

        query = new StringBuilder();
        query.append(Constants.CLI_SEPARATOR.getValue());

        cliQueryComponents = new ArrayList<String>();
        /*
         * query path
         */
        cliQueryComponents.add("host" + Constants.EQUALS.getValue() + host);
        cliQueryComponents.add("server" + Constants.EQUALS.getValue() + server);

        cliQueryComponents.add("subsystem" + Constants.EQUALS.getValue()
                + getSubsystem());
        cliQueryComponents.add(getSubsystemComponentKey()
                + Constants.EQUALS.getValue() + getSubsystemComponentValue());
        if (getSubsystemComponentAttributeKey() != null) {
            cliQueryComponents.add(getSubsystemComponentAttributeKey()
                    + Constants.EQUALS.getValue()
                    + getSubsystemComponentAttributeValue());
        }
        query.append(Joiner.on(Constants.CLI_SEPARATOR.getValue()).join(
                cliQueryComponents));

        cliQueryComponents.clear();
        /*
         * query function and addons
         */
        query.append(":read-resource(");
        if (includeRuntime) {
            cliQueryComponents.add("include-runtime"
                    + Constants.EQUALS.getValue() + includeRuntime);
        }
        if (recursive) {
            cliQueryComponents.add("recursive" + Constants.EQUALS.getValue()
                    + recursive);
        }
        query.append(Joiner.on(", ").join(cliQueryComponents));
        query.append(")");

        cliQueryComponents = null;

        Result result = client.cmd(query.toString());

        if (result.isSuccess()) {
            ModelNode response = result.getResponse();

            rawData.put(new InstanceDMRRawQueryDataKey(configurationId,
                    timestamp, host, server, subsystem,
                    getSubsystemComponentKey(), getSubsystemComponentValue(),
                    getSubsystemComponentAttributeKey(),
                    getSubsystemComponentAttributeValue()), response
                    .get("result"));
        }

        // client.
        //
        //
        // modelNodeSubsystems = modelNodeSubdeployment.get("subsystem");
        // // search for requested subsystem
        // if (modelNodeSubsystems.has(subsystem)) {
        // // get requested subsystem
        // modelNodeSubsystem = modelNodeSubsystems.get(subsystem);
        // // search for subsystem components
        //
        // if (LOG.isDebugEnabled()) {
        // LOG.debug(modelNodeSubsystem.toJSONString(false));
        // }
        //
        // cleanRawData(modelNodeSubsystem);
        // rawData.put(new AppDMRRawQueryDataKey(configurationId,
        // timestamp, host, server, deploy, subdeploymentName,
        // subsystem), modelNodeSubsystem);
        // } else {
        // LOG.warn("requested subsystem {} not found.", subsystem);
        // }
    }
}
