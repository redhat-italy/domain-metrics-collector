package com.redhat.it.customers.dmc.core.exceptions;

/**
 * The Class CollectorNotFoundException.
 * 
 * @author Andrea Battaglia
 */
public class CollectorStoppedException extends ConfigurationException {


    /**
     * 
     */
    private static final long serialVersionUID = -9203089038506537724L;
    private final static String message = "Collector \"%s\" is in status STOPPED.";

    public CollectorStoppedException(String collectorId) {
        super(String.format(message, collectorId));
    }
    /**
     * Instantiates a new collector not found exception.
     *
     * @param message
     *            the message
     */
    public CollectorStoppedException(String collectorId,Throwable cause) {
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
