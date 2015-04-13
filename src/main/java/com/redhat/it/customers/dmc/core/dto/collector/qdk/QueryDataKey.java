/**
 * 
 */
package com.redhat.it.customers.dmc.core.dto.collector.qdk;

/**
 * The Class QueryDataKey.
 *
 * @author Andrea Battaglia (Red Hat)
 */
public abstract class QueryDataKey {

    /** The configuration name. */
    private final String configurationName;

    /** The timestamp. */
    private final long timestamp;

    /**
     * Instantiates a new query data key.
     *
     * @param configurationName
     *            the configuration name
     * @param timestamp
     *            the timestamp
     */
    public QueryDataKey(final String configurationName, final long timestamp) {
        super();
        this.configurationName = configurationName;
        this.timestamp = timestamp;
    }

    /**
     * Gets the configuration name.
     *
     * @return the configuration name
     */
    public String getConfigurationName() {
        return configurationName;
    }

    /**
     * Gets the timestamp.
     *
     * @return the timestamp
     */
    public long getTimestamp() {
        return timestamp;
    }

    /*
     * (non-Javadoc)
     * 
     * @see java.lang.Object#hashCode()
     */
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime
                * result
                + ((configurationName == null) ? 0 : configurationName
                        .hashCode());
        return result;
    }

    /**
     * @see java.lang.Object#equals(java.lang.Object)
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        QueryDataKey other = (QueryDataKey) obj;
        if (configurationName == null) {
            if (other.configurationName != null)
                return false;
        } else if (!configurationName.equals(other.configurationName))
            return false;
        return true;
    }

    /**
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("QueryDataKey [\n    configurationName=");
        builder.append(configurationName);
        builder.append(", \n    timestamp=");
        builder.append(timestamp);
        builder.append("\n]");
        return builder.toString();
    }

}
