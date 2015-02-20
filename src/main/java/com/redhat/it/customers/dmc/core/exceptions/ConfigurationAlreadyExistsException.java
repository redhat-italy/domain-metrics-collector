/**
 * 
 */
package com.redhat.it.customers.dmc.core.exceptions;


/**
 * @author Andrea Battaglia
 *
 */
public class ConfigurationAlreadyExistsException extends ConfigurationException {

    /**
     * 
     */
    private static final long serialVersionUID = 6643361115375840379L;
    private final static String message = "Configuration \"%s\" already exists.";

    public ConfigurationAlreadyExistsException(String collectorId) {
        super(String.format(message, collectorId));
    }

    /**
     * Instantiates a new collector not found exception.
     *
     * @param message
     *            the message
     */
    public ConfigurationAlreadyExistsException(String collectorId, Throwable cause) {
        super(String.format(message, collectorId),cause);
    }
    
    /**
     * @see com.redhat.it.customers.dmc.core.exceptions.DMCException#getErrorCode()
     */
    @Override
    public int getErrorCode() {
        return 415;
    }

}
