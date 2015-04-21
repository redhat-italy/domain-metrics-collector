/**
 *
 */
package com.redhat.it.customers.dmc.core.dto.collector.qdk.t;

import java.util.LinkedHashMap;
import java.util.Map;

import com.redhat.it.customers.dmc.core.dto.collector.qdk.QueryDataKey;

/**
 * @author Andrea Battaglia (Red Hat)
 *
 */
public abstract class AbstractTransformedQueryDataKey extends QueryDataKey {

    /** The key elements. */
    private final Map<String, Object> keyElements;

    // /** The host. */
    // private final String host;
    //
    // /** The server. */
    // private final String server;

    {
        keyElements = new LinkedHashMap<String, Object>();
    }

    /**
     * Instantiates a new abstract raw query data key.
     *
     * @param configurationName
     *            the configuration name
     * @param timestamp
     *            the timestamp
     * @param host
     *            the host
     * @param server
     *            the server
     */
    public AbstractTransformedQueryDataKey(String configurationName,
            long timestamp) {
        super(configurationName, timestamp);
    }

    public Object setKeyElement(final String keyElementName,
            Object keyElementValue) {
        return keyElements.put(keyElementName, keyElementValue);
    }

    public Map<String, Object> getKeyElements() {
        return keyElements;
    }

    public abstract String getKeyAsString();

    /**
     * @see java.lang.Object#hashCode()
     */
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = super.hashCode();
        result = (prime * result)
                + ((keyElements == null) ? 0 : keyElements.hashCode());
        return result;
    }

    /**
     * @see java.lang.Object#equals(java.lang.Object)
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!super.equals(obj)) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        AbstractTransformedQueryDataKey other = (AbstractTransformedQueryDataKey) obj;
        if (keyElements == null) {
            if (other.keyElements != null) {
                return false;
            }
        } else if (!keyElements.equals(other.keyElements)) {
            return false;
        }
        return true;
    }

    /**
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("AbstractTransformedQueryDataKey [\n    keyElements=");
        builder.append(keyElements);
        builder.append("\n]");
        return builder.toString();
    }


}
