package com.redhat.it.customers.dmc.core.dto.event;

/**
 * The Class StartCollectorEvent.
 * 
 * @author Andrea Battaglia
 */
public class StopCollectorEvent {
    
    /** The configuration id. */
    private final String configurationId;

    /**
     * Instantiates a new start collector event.
     *
     * @param configurationId the configuration id
     */
    public StopCollectorEvent(String configurationId) {
        super();
        this.configurationId = configurationId;
    }

    /**
     * Gets the configuration id.
     *
     * @return the configuration id
     */
    public String getConfigurationId() {
        return configurationId;
    }

    /**
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("StartCollectorEvent [configurationId=");
        builder.append(configurationId);
        builder.append("]");
        return builder.toString();
    }
    
    
}
