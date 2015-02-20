/**
 * 
 */
package com.redhat.it.customers.dmc.core.exceptions;

/**
 * @author Andrea Battaglia
 *
 */
public class ConfigurationDeleteException extends ConfigurationException {

    /**
     * 
     */
    private static final long serialVersionUID = -4855179671053422292L;

    private final static String message = "Cannot delete configuration \"%s\" from file system.";

    public ConfigurationDeleteException(String collectorId) {
        super(String.format(message, collectorId));
    }

    /**
     * @param message
     */
    public ConfigurationDeleteException(String collectorId, Throwable cause) {
        super(String.format(message, collectorId), cause);
    }

    /**
     * @see com.redhat.it.customers.dmc.core.exceptions.DMCException#getErrorCode()
     */
    @Override
    public int getErrorCode() {
        return 500;
    }

}
