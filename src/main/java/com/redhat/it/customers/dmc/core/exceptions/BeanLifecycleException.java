/**
 * 
 */
package com.redhat.it.customers.dmc.core.exceptions;

/**
 * @author Andrea Battaglia
 *
 */
public class BeanLifecycleException extends RuntimeException {

    /**
     * 
     */
    private static final long serialVersionUID = 5193006347670194307L;

    /**
     * 
     */
    public BeanLifecycleException() {
        // TODO Auto-generated constructor stub
    }

    /**
     * @param message
     */
    public BeanLifecycleException(String message) {
        super(message);
        // TODO Auto-generated constructor stub
    }

    /**
     * @param cause
     */
    public BeanLifecycleException(Throwable cause) {
        super(cause);
        // TODO Auto-generated constructor stub
    }

    /**
     * @param message
     * @param cause
     */
    public BeanLifecycleException(String message, Throwable cause) {
        super(message, cause);
        // TODO Auto-generated constructor stub
    }

    /**
     * @param message
     * @param cause
     * @param enableSuppression
     * @param writableStackTrace
     */
    public BeanLifecycleException(String message, Throwable cause,
            boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
        // TODO Auto-generated constructor stub
    }

}
