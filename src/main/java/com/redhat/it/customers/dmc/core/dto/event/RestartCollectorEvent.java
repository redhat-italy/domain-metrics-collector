/*
 * 
 */
package com.redhat.it.customers.dmc.core.dto.event;

import com.redhat.it.customers.dmc.core.dto.configuration.Configuration;

/**
 * The Class StartCollectorEvent.
 *
 * @author Andrea Battaglia
 */
public class RestartCollectorEvent {

    /** The configuration id. */
    private final Configuration configuration;

    /**
     * Instantiates a new start collector event.
     *
     * @param configuration
     *            the configuration id
     */
    public RestartCollectorEvent(Configuration configuration) {
        super();
        this.configuration = configuration;
    }

    /**
     * Gets the configuration id.
     *
     * @return the configuration id
     */
    public Configuration getConfiguration() {
        return configuration;
    }

    /**
     * @see java.lang.Object#toConfiguration()
     */
    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("RestartCollectorEvent [configurationId=");
        builder.append(configuration.getId());
        builder.append("]");
        return builder.toString();
    }

}
