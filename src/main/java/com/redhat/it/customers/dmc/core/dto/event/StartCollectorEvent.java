package com.redhat.it.customers.dmc.core.dto.event;

import com.redhat.it.customers.dmc.core.dto.configuration.Configuration;

/**
 * The Class StartCollectorEvent.
 * 
 * @author Andrea Battaglia
 */
public class StartCollectorEvent {

    /** The configuration id. */
    private final Configuration configuration;

    /**
     * Instantiates a new start collector event.
     *
     * @param configurationId
     *            the configuration id
     */
    public StartCollectorEvent(Configuration configuration) {
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
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("StartCollectorEvent [configurationId=");
        builder.append(configuration.getId());
        builder.append("]");
        return builder.toString();
    }

}
