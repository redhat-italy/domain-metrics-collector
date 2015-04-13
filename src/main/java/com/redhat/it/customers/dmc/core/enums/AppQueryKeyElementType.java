/**
 * 
 */
package com.redhat.it.customers.dmc.core.enums;

// TODO: Auto-generated Javadoc
/**
 * The Enum AppQueryKeyElementType.
 *
 * @author Andrea Battaglia (Red Hat)
 */
public enum AppQueryKeyElementType {

    /** The host. */
    HOST("host"),

    /** The server. */
    SERVER("server"),

    /** The deploy. */
    DEPLOY("deploy"),

    /** The subsystem. */
    SUBSYSTEM("subsystem"),

    /** The subdeploy. */
    SUBDEPLOY("subdeploy");

    /** The value. */
    private String value;

    /**
     * Instantiates a new app query key element type.
     *
     * @param value
     *            the value
     */
    private AppQueryKeyElementType(String value) {
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
