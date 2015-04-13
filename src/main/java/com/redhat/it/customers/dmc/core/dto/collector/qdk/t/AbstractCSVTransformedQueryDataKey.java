/**
 * 
 */
package com.redhat.it.customers.dmc.core.dto.collector.qdk.t;

import com.google.common.base.Joiner;

// TODO: Auto-generated Javadoc
/**
 * The Class CSVTransformedQueryDataKey.
 *
 * @author Andrea Battaglia (Red Hat)
 */
public abstract class AbstractCSVTransformedQueryDataKey extends
        AbstractTransformedQueryDataKey {

    /** The field separator. */
    private final String fieldSeparator;

    /**
     * Instantiates a new CSV transformed query data key.
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
    public AbstractCSVTransformedQueryDataKey(String configurationName,
            long timestamp, String host, String server, String fieldSeparator) {
        super(configurationName, timestamp);
        this.fieldSeparator = fieldSeparator;
    }

    /**
     * Gets the field separator.
     *
     * @return the field separator
     */
    public String getFieldSeparator() {
        return fieldSeparator;
    }

    /**
     * @see com.redhat.it.customers.dmc.core.dto.collector.qdk.t.AbstractTransformedQueryDataKey#getKeyAsString()
     */
    @Override
    public String getKeyAsString() {
        return Joiner.on(fieldSeparator).join(getKeyElements().values());
    }
}