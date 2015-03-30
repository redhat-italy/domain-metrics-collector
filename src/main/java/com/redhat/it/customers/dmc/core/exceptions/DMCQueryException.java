/**
 * 
 */
package com.redhat.it.customers.dmc.core.exceptions;

/**
 * @author Andrea Battaglia (Red Hat)
 *
 */
public class DMCQueryException extends DMCException {

    /**
     * 
     */
    private static final long serialVersionUID = 24109664278892162L;

    /**
     * 
     */
    public DMCQueryException() {
        // TODO Auto-generated constructor stub
    }

    /**
     * @param message
     * @param cause
     * @param enableSuppression
     * @param writableStackTrace
     */
    public DMCQueryException(String message, Throwable cause,
            boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
        // TODO Auto-generated constructor stub
    }

    /**
     * @param message
     * @param cause
     */
    public DMCQueryException(String message, Throwable cause) {
        super(message, cause);
        // TODO Auto-generated constructor stub
    }

    /**
     * @param message
     */
    public DMCQueryException(String message) {
        super(message);
        // TODO Auto-generated constructor stub
    }

    /**
     * @param cause
     */
    public DMCQueryException(Throwable cause) {
        super(cause);
        // TODO Auto-generated constructor stub
    }

    /* (non-Javadoc)
     * @see com.redhat.it.customers.dmc.core.exceptions.DMCException#getErrorCode()
     */
    @Override
    public int getErrorCode() {
        // TODO Auto-generated method stub
        return 0;
    }

}
