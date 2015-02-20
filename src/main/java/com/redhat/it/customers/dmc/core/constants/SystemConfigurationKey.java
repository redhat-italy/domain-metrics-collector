package com.redhat.it.customers.dmc.core.constants;

/**
 * The Enum SystemConfigurationKey.
 * 
 * @author Andrea Battaglia
 */
public enum SystemConfigurationKey {

    ROOT_PATH("core.root.path"),

    /** The export path. */
    CONFIGURATIONS_PATH("core.configurations.path"),

    /** The export path. */
    EXPORT_PATH("core.export.path"),

    /** The export file extension. */
    EXPORT_FILE_EXTENSION("core.export.file.extension"),

    /** The log path. */
    LOG_PATH("core.log.path"),

    /** The log file name. */
    LOG_FILE_NAME("core.log.file.name");

    private final String value;

    /**
     * Instantiates a new system configuration key.
     *
     * @param value
     *            the value
     */
    private SystemConfigurationKey(String value) {
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
