package com.redhat.it.customers.dmc.core.exceptions;

/**
 * The Class CollectorNotFoundException.
 * 
 * @author Andrea Battaglia
 */
public class ConfigurationNotFoundException extends ConfigurationException {

    /**
     * 
     */
    private static final long serialVersionUID = 8599001349544896619L;

    private final static String message = "Configuration \"%s\" not found.";

    public ConfigurationNotFoundException(String collectorId) {
        super(String.format(message, collectorId));
    }

    /**
     * Instantiates a new collector not found exception.
     *
     * @param message
     *            the message
     */
    public ConfigurationNotFoundException(String collectorId, Throwable cause) {
        super(String.format(message, collectorId), cause);
    }

    /**
     * @see com.redhat.it.customers.dmc.core.exceptions.DMCException#getErrorCode()
     */
    @Override
    public int getErrorCode() {
        return 404;
    }

}