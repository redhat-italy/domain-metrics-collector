package com.redhat.it.customers.dmc.core.constants;

/**
 * The Enum Constants.
 * 
 * @author Andrea Battaglia
 */
public enum Constants {

    /** The default separator. */
    DEFAULT_SEPARATOR(";"),

    /** The default configurations path. */
    DEFAULT_CONFIGURATIONS_PATH("configs"),

    /** The default export dir. */
    DEFAULT_EXPORT_DIR("export"),

    /** The default export file extension. */
    DEFAULT_EXPORT_FILE_EXTENSION(".CSV"),
    /** The default log dir. */
    DEFAULT_LOG_DIR("log"),

    /** The default log file name. */
    DEFAULT_LOG_FILE_NAME("dmc.log"),

    /** The collector id mdc key. */
    COLLECTOR_ID_MDC_KEY("collectorId"),

    /** The export logger name. */
    TEMPLATE_LOGGER_NAME("templateLogger"),
    
    SERVICE_JMX_REMOTING_PREFIX("service:jmx:remoting-jmx://"),

    ;

    /** The value. */
    private final String value;

    /**
     * Instantiates a new constants.
     *
     * @param value
     *            the value
     */
    private Constants(String value) {
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
