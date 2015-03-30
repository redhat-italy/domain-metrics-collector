/**
 * 
 */
package com.redhat.it.customers.dmc.core.exceptions;

/**
 * @author Andrea Battaglia
 *
 */
public class DMCStartException extends DMCException {

    /**
     * 
     */
    private static final long serialVersionUID = 6861632911495350788L;

    /**
     * @see com.redhat.it.customers.dmc.core.exceptions.DMCException#getErrorCode()
     */
    @Override
    public int getErrorCode() {
        return 0;
    }

}
