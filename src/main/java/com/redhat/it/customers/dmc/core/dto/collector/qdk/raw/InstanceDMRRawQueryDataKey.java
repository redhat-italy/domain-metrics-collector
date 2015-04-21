/*
 *
 */
package com.redhat.it.customers.dmc.core.dto.collector.qdk.raw;

/**
 * The Class InstanceDMRRawQueryDataKey.
 *
 * @author Andrea Battaglia (Red Hat)
 */
public class InstanceDMRRawQueryDataKey extends DMRRawQueryDataKey {
    private String subsystem;
    private String subsystemComponentKey;
    private String subsystemComponentValue;
    private String subsystemComponentAttributeKey;
    private String subsystemComponentAttributeValue;

    /**
     * Instantiates a new instance dmr raw query data key.
     *
     * @param configurationName
     *            the configuration name
     * @param timestamp
     *            the timestamp
     * @param host
     *            the host
     * @param server
     *            the server
     * @param subsystem
     *            the subsystem
     * @param subsystemComponent
     *            the subsystem component
     * @param subsystemComponentAttribute
     *            the subsystem component attribute
     */
    public InstanceDMRRawQueryDataKey(String configurationName, long timestamp,
            String host, String server, final String subsystem,
            final String subsystemComponentKey,
            final String subsystemComponentValue,
            final String subsystemComponentAttributeKey,
            final String subsystemComponentAttributeValue) {
        super(configurationName, timestamp, host, server);
        this.subsystem = subsystem;
        this.subsystemComponentKey = subsystemComponentKey;
        this.subsystemComponentValue = subsystemComponentValue;
        this.subsystemComponentAttributeKey = subsystemComponentAttributeKey;
        this.subsystemComponentAttributeValue = subsystemComponentAttributeValue;
    }

    /**
     * Gets the subsystem.
     *
     * @return the subsystem
     */
    public String getSubsystem() {
        return subsystem;
    }

    /**
     * @return the subsystemComponentKey
     */
    public String getSubsystemComponentKey() {
        return subsystemComponentKey;
    }

    /**
     * @return the subsystemComponentValue
     */
    public String getSubsystemComponentValue() {
        return subsystemComponentValue;
    }

    /**
     * @return the subsystemComponentAttributeKey
     */
    public String getSubsystemComponentAttributeKey() {
        return subsystemComponentAttributeKey;
    }

    /**
     * @return the subsystemComponentAttributeValue
     */
    public String getSubsystemComponentAttributeValue() {
        return subsystemComponentAttributeValue;
    }

    /**
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("InstanceDMRRawQueryDataKey [\n    toString()=");
        builder.append(super.toString());
        builder.append(", \n    subsystem=");
        builder.append(subsystem);
        builder.append(", \n    subsystemComponentKey=");
        builder.append(subsystemComponentKey);
        builder.append(", \n    subsystemComponentValue=");
        builder.append(subsystemComponentValue);
        builder.append(", \n    subsystemComponentAttributeKey=");
        builder.append(subsystemComponentAttributeKey);
        builder.append(", \n    subsystemComponentAttributeValue=");
        builder.append(subsystemComponentAttributeValue);
        builder.append("\n]");
        return builder.toString();
    }

}
