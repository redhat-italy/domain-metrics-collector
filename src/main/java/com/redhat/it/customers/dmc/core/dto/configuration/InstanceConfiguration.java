/*
 *
 */
package com.redhat.it.customers.dmc.core.dto.configuration;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;

import com.redhat.it.customers.dmc.core.enums.MetricType;

/**
 * The Class InstanceConfiguration.
 *
 * @author Andrea Battaglia
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "InstanceConfiguration", namespace = "dto.dmc.customers.it.redhat.com")
public class InstanceConfiguration extends AbstractDMRConfiguration {

    private String subsystemComponentKey;
    private String subsystemComponentValue;
    private String subsystemComponentAttributeKey;
    private String subsystemComponentAttributeValue;
    private Boolean includeRuntime;
    private Boolean recursive;

    {
        includeRuntime = Boolean.TRUE;
        recursive = Boolean.TRUE;
    }

    /**
     * Instantiates a new instance configuration.
     */
    public InstanceConfiguration() {
        metricType = MetricType.INSTANCE;
    }

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
    public Boolean isIncludeRuntime() {
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
    public Boolean isRecursive() {
        return recursive;
    }

    /**
     * @param recursive
     *            the recursive to set
     */
    public void setRecursive(boolean recursive) {
        this.recursive = recursive;
    }

}
