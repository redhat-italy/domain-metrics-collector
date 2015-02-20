/**
 * 
 */
package com.redhat.it.customers.dmc.core.exceptions;

/**
 * The Class ConfigurationException.
 *
 * @author Andrea Battaglia
 */
public abstract class ConfigurationException extends DMCException {

    /**
     * 
     */
    private static final long serialVersionUID = -587256157619375022L;

    public ConfigurationException(String message) {
        super(message);
    }
    
    /**
     * Instantiates a new configuration exception.
     *
     * @param configurationId
     *            the configuration id
     */
    public ConfigurationException(String message, Throwable cause) {
        super(message, cause);
    }

}
