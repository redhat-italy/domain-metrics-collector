/**
 * 
 */
package com.redhat.it.customers.dmc.core.exceptions;

/**
 * @author Andrea Battaglia
 *
 */
public class StopException extends DMCException {

    /**
     * 
     */
    private static final long serialVersionUID = -3308472958849625825L;

    /**
     * @see com.redhat.it.customers.dmc.core.exceptions.DMCException#getErrorCode()
     */
    @Override
    public int getErrorCode() {
        return 0;
    }

}
