/**
 * 
 */
package com.redhat.it.customers.dmc.core.dto.collector.qdk.raw;

import com.redhat.it.customers.dmc.core.dto.collector.qdk.QueryDataKey;

/**
 * The Class AbstractRawQueryDataKey.
 *
 * @author Andrea Battaglia (Red Hat)
 */
public abstract class AbstractRawQueryDataKey extends QueryDataKey {

    /** The host. */
    private final String host;
    
    /** The server. */
    private final String server;

    /**
     * Instantiates a new abstract raw query data key.
     *
     * @param configurationName the configuration name
     * @param timestamp the timestamp
     * @param host the host
     * @param server the server
     */
    public AbstractRawQueryDataKey(String configurationName, long timestamp,
            final String host, final String server) {
        super(configurationName, timestamp);
        this.host = host;
        this.server = server;
    }

    /**
     * Gets the host.
     *
     * @return the host
     */
    public String getHost() {
        return host;
    }

    /**
     * Gets the server.
     *
     * @return the server
     */
    public String getServer() {
        return server;
    }

    /**
     * @see com.redhat.it.customers.dmc.core.dto.collector.qdk.QueryDataKey#hashCode()
     */
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((host == null) ? 0 : host.hashCode());
        result = prime * result + ((server == null) ? 0 : server.hashCode());
        return result;
    }

    /**
     * @see com.redhat.it.customers.dmc.core.dto.collector.qdk.QueryDataKey#equals(java.lang.Object)
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        AbstractRawQueryDataKey other = (AbstractRawQueryDataKey) obj;
        if (host == null) {
            if (other.host != null)
                return false;
        } else if (!host.equals(other.host))
            return false;
        if (server == null) {
            if (other.server != null)
                return false;
        } else if (!server.equals(other.server))
            return false;
        return true;
    }

    /**
     * @see com.redhat.it.customers.dmc.core.dto.collector.qdk.QueryDataKey#toString()
     */
    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("AbstractRawQueryDataKey [\n    configurationName()=");
        builder.append(getConfigurationName());
        builder.append(", \n    timestamp()=");
        builder.append(getTimestamp());
        builder.append(", \n    host=");
        builder.append(host);
        builder.append(", \n    server=");
        builder.append(server);
        builder.append("\n]");
        return builder.toString();
    }
    
    

}
