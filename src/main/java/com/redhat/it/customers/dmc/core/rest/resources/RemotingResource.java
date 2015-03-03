package com.redhat.it.customers.dmc.core.rest.resources;

import java.io.IOException;
import java.net.URLDecoder;
import java.util.Arrays;

import javax.inject.Inject;
import javax.management.AttributeNotFoundException;
import javax.management.InstanceNotFoundException;
import javax.management.MBeanException;
import javax.management.MalformedObjectNameException;
import javax.management.ReflectionException;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.core.PathSegment;
import javax.ws.rs.core.Response;

import org.slf4j.Logger;

import com.redhat.it.customers.dmc.core.exceptions.DMCException;
import com.redhat.it.customers.dmc.core.services.connection.JMXFacade;

/**
 * The Class AdminService.
 * 
 * @author Andrea Battaglia
 */
@Path("/query")
public class RemotingResource {

    /** The log. */
    @Inject
    private Logger LOG;

    @Inject
    private JMXFacade jmxFacade;

    /**
     * 
     * es: username=admin;password=Pa$$w0rd
     * http://localhost:8087/dmc/query/127.0
     * .0.1::9999::admin::Pa$$w0rd::jboss.as
     * :subsystem=datasources,data-source=UNIC4_MPUSER
     * ,statistics=pool::AvailableCount
     * 
     * 
     * @param parameters
     * @return
     * @throws IOException
     * @throws DMCException
     * @throws ReflectionException
     * @throws MBeanException
     * @throws InstanceNotFoundException
     * @throws AttributeNotFoundException
     * @throws MalformedObjectNameException
     */
    @GET
    @Produces(MediaType.TEXT_PLAIN)
    @Path("{query}")
    public String query(@PathParam("query") String query)
            throws MalformedObjectNameException, AttributeNotFoundException,
            InstanceNotFoundException, MBeanException, ReflectionException,
            DMCException, IOException {
        String username = null;
        String password = null;
        String host = null;
        String port = null;
        String object = null;
        String attribute = null;
        String path = null;
        String value = null;

        String[] params = query.split("::");
        host = params[0];
        port = params[1];
        username = params[2];
        password = URLDecoder.decode(params[3]);
        object = params[4];
        attribute = params[5];
        path = (params.length == 7) ? params[6] : null;

        value = jmxFacade.execute(host, port, username, password, object,
                attribute, path);

        return value;
    }

    // @GET
    // @Path("{parameters}")
    // public Response query(@PathParam("parameters") PathSegment parameters) {
    // String queryPath = null;
    // MultivaluedMap<String, String> mvm = null;
    // String host = null;
    // String port = null;
    // String object = null;
    // String attribute = null;
    // String path = null;
    // String[] socket=queryPath.split("::");
    //
    // queryPath = parameters.getPath();
    // mvm = parameters.getMatrixParameters();
    // LOG.info("entries to update: {} - {}", queryPath, mvm);
    //
    // host = socket[0];
    // port = socket[1];
    // object = mvm.getFirst("object");
    // attribute = params[3];
    // path = (params.length == 5) ? params[4] : null;
    // LOG.info("entries to update: {} - {}", path, mvm);
    // return Response.ok().entity(path + " - " + mvm.toString()).build();
    // }
}
