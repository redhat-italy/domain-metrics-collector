package com.redhat.it.customers.dmc.core.enums;

import java.util.Map;
import java.util.TreeMap;

/**
 * The Enum SupportedDMRSubsystemType.
 * 
 * @author Andrea Battaglia (Red Hat)
 */
public enum SupportedDMRSubsystemType {

    /** The EJ b3. */
    EJB3("ejb3");

    private static final Map<String, SupportedDMRSubsystemType> map;

    /** The value. */
    private final String value;

    static {
        map = new TreeMap<>();
        for (SupportedDMRSubsystemType type : values()) {
            map.put(type.getValue(), type);
        }
    }

    public static SupportedDMRSubsystemType decode(String value) {
        try {
            return map.get(value);
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * Instantiates a new supported dmr subsystem type.
     *
     * @param value
     *            the value
     */
    private SupportedDMRSubsystemType(String value) {
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
