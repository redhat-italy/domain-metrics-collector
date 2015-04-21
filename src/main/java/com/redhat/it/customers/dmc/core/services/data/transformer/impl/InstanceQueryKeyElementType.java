/*
 * 
 */
package com.redhat.it.customers.dmc.core.services.data.transformer.impl;

// TODO: Auto-generated Javadoc
/**
 * The Enum InstanceQueryKeyElementType.
 *
 * @author Andrea Battaglia (Red Hat)
 */
public enum InstanceQueryKeyElementType {

    /** The host. */
    HOST("host"),

    /** The server. */
    SERVER("server"),

    /** The subsystem. */
    SUBSYSTEM("subsystem"),

    /** The subdeploy. */
    SUBSYSTEM_COMPONENT("subsystemComponent"),

    SUBSYSTEM_COMPONENT_ATTRIBUTE("subsystemComponentAttribute");

    /** The value. */
    private String value;

    /**
     * Instantiates a new app query key element type.
     *
     * @param value
     *            the value
     */
    private InstanceQueryKeyElementType(String value) {
        this.value = value;

    }

    /**
     * Gets the value.
     *
     * @return the value
     */
    public String getValue() {
        return value;
    }
}
