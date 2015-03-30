package com.redhat.it.customers.dmc.core.rest.resources;

import javax.inject.Inject;
import javax.inject.Singleton;
import javax.ws.rs.GET;
import javax.ws.rs.Path;

import org.slf4j.Logger;

import com.redhat.it.customers.dmc.core.services.collector.MetricCollectorService;
import com.redhat.it.customers.dmc.core.services.data.export.DataExportService;

@Path("/test")
public class RestResource {
    @Inject
    private Logger LOG;

    @GET
    @Path("/hello")
    public String helloWorld() {
        return "hello!!!";
    }
}
