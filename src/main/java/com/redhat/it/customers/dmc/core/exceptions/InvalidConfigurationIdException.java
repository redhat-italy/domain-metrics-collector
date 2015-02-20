/**
 * 
 */
package com.redhat.it.customers.dmc.core.exceptions;

/**
 * @author Andrea Battaglia
 *
 */
public class InvalidConfigurationIdException extends ConfigurationException {
    /**
     * 
     */
    private static final long serialVersionUID = 1883421837307920124L;
    
    private final static String message = "Configuration \"%s\" not found.";

    /**
     * @param message
     */
    public InvalidConfigurationIdException(String collectorId) {
        super(String.format(message, collectorId));
    }

    /**
     * @param message
     * @param cause
     */
    public InvalidConfigurationIdException(String collectorId, Throwable cause) {
        super(String.format(message, collectorId), cause);
    }

    /**
     * @see com.redhat.it.customers.dmc.core.exceptions.DMCException#getErrorCode()
     */
    @Override
    public int getErrorCode() {
        return 412;
    }

}
