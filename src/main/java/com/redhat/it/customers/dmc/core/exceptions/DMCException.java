/**
 * 
 */
package com.redhat.it.customers.dmc.core.exceptions;

/**
 * The Class DMCException.
 *
 * @author Andrea Battaglia
 */
public abstract class DMCException extends Exception {

    /**
     * 
     */
    private static final long serialVersionUID = 1561698526891996871L;
    
    public abstract int getErrorCode();

    /**
     * Instantiates a new DMC exception.
     */
    public DMCException() {
        super();
        // TODO Auto-generated constructor stub
    }

    /**
     * Instantiates a new DMC exception.
     *
     * @param message
     *            the message
     * @param cause
     *            the cause
     * @param enableSuppression
     *            the enable suppression
     * @param writableStackTrace
     *            the writable stack trace
     */
    public DMCException(String message, Throwable cause,
            boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
        // TODO Auto-generated constructor stub
    }

    /**
     * Instantiates a new DMC exception.
     *
     * @param message
     *            the message
     * @param cause
     *            the cause
     */
    public DMCException(String message, Throwable cause) {
        super(message, cause);
        // TODO Auto-generated constructor stub
    }

    /**
     * Instantiates a new DMC exception.
     *
     * @param message
     *            the message
     */
    public DMCException(String message) {
        super(message);
        // TODO Auto-generated constructor stub
    }

    /**
     * Instantiates a new DMC exception.
     *
     * @param cause
     *            the cause
     */
    public DMCException(Throwable cause) {
        super(cause);
        // TODO Auto-generated constructor stub
    }

}
