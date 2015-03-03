/**
 * 
 */
package com.redhat.it.customers.dmc.core.rest.mapper;

import javax.inject.Inject;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

import org.slf4j.Logger;

import com.redhat.it.customers.dmc.core.exceptions.DMCException;

/**
 * @author Andrea Battaglia
 *
 */
@Provider
public class DMCExceptionMapper implements ExceptionMapper<DMCException> {

    @Inject
    private Logger LOG;

    /**
     * @see javax.ws.rs.ext.ExceptionMapper#toResponse(java.lang.Throwable)
     */
    @Override
    public Response toResponse(DMCException e) {
        LOG.error("", e);
        return Response
                .status(e.getErrorCode())
                .entity("An exception occurred: \"" + e.getMessage()
                        + ".\" See the log file for error details.").build();
    }

}