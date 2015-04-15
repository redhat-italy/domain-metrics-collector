/**
 * 
 */
package com.redhat.it.customers.dmc.core.exceptions;

/**
 * The Class UnsupportedMetricException.
 *
 * @author Andrea Battaglia (Red Hat)
 */
public class UnsupportedMetricException extends DMCException {

    /** The Constant serialVersionUID. */
    private static final long serialVersionUID = 6050680495170831249L;

    /**
     * Instantiates a new unsupported metric exception.
     */
    public UnsupportedMetricException() {
        // TODO Auto-generated constructor stub
    }

    /**
     * Instantiates a new unsupported metric exception.
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
    public UnsupportedMetricException(String message, Throwable cause,
            boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
        // TODO Auto-generated constructor stub
    }

    /**
     * Instantiates a new unsupported metric exception.
     *
     * @param message
     *            the message
     * @param cause
     *            the cause
     */
    public UnsupportedMetricException(String message, Throwable cause) {
        super(message, cause);
        // TODO Auto-generated constructor stub
    }

    /**
     * Instantiates a new unsupported metric exception.
     *
     * @param message
     *            the message
     */
    public UnsupportedMetricException(String message) {
        super(message);
        // TODO Auto-generated constructor stub
    }

    /**
     * Instantiates a new unsupported metric exception.
     *
     * @param cause
     *            the cause
     */
    public UnsupportedMetricException(Throwable cause) {
        super(cause);
        // TODO Auto-generated constructor stub
    }

    /**
     * @see com.redhat.it.customers.dmc.core.exceptions.DMCException#getErrorCode()
     */
    @Override
    public int getErrorCode() {
        // TODO Auto-generated method stub
        return 0;
    }

}
