package com.redhat.it.customers.dmc.core.constants;

import com.redhat.it.customers.dmc.core.enums.ExportDestinationType;
import com.redhat.it.customers.dmc.core.enums.ExportFormatType;

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
    DEFAULT_EXPORT_FILE_EXTENSION(".csv"),
    /** The default log dir. */
    DEFAULT_LOG_DIR("log"),

    /** The default log file name. */
    DEFAULT_LOG_FILE_NAME("dmc.log"),

    /** The collector id mdc key. */
    COLLECTOR_ID_MDC_KEY("collectorId"),

    /** The export logger name. */
    TEMPLATE_LOGGER_NAME("templateLogger"),

    /** The service jmx remoting prefix. */
    SERVICE_JMX_REMOTING_PREFIX("service:jmx:remoting-jmx://"),

    /** The regexp catchall. */
    REGEXP_CATCH_ALL(".+"),

    /** The default app object depth. */
    DEFAULT_APP_OBJECT_DEPTH("0"),

    /** The default host. */
    DEFAULT_HOST("localhost"),

    /** The default port. */
    DEFAULT_PORT("9999"),

    /** The default scan interval. */
    DEFAULT_SCAN_INTERVAL("5"),

    /** The configuration file extension. */
    CONFIGURATION_FILE_EXTENSION(".json"),

    DEFAULT_EXPORT_DESTINATION_TYPE(ExportDestinationType.FILE.name()),

    DEFAULT_EXPORT_FORMAT_TYPE(ExportFormatType.JSON.name()),

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
