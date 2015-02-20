package com.redhat.it.customers.dmc.core.exceptions;

/**
 * The Class CollectorNotFoundException.
 * 
 * @author Andrea Battaglia
 */
public class CollectorNotFoundException extends ConfigurationException {

    /** The Constant serialVersionUID. */
    private static final long serialVersionUID = -3464597954504808840L;

    private final static String message = "Collector \"%s\" not found.";

    public CollectorNotFoundException(String collectorId) {
        super(String.format(message, collectorId));
    }
    /**
     * Instantiates a new collector not found exception.
     *
     * @param message
     *            the message
     */
    public CollectorNotFoundException(String collectorId, Throwable cause) {
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