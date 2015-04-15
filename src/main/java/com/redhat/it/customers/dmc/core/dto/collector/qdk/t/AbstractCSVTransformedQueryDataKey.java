/**
 * 
 */
package com.redhat.it.customers.dmc.core.dto.collector.qdk.t;

import com.google.common.base.Joiner;
import com.redhat.it.customers.dmc.core.util.CSVFunctions;

// TODO: Auto-generated Javadoc
/**
 * The Class CSVTransformedQueryDataKey.
 *
 * @author Andrea Battaglia (Red Hat)
 */
public abstract class AbstractCSVTransformedQueryDataKey extends
        AbstractTransformedQueryDataKey {

    /** The field separator. */
    private final char fieldSeparator;

    public AbstractCSVTransformedQueryDataKey(String configurationName,
            long timestamp) {
        super(configurationName, timestamp);
        this.fieldSeparator = CSVFunctions.DEFAULT_SEPARATOR;
    }

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
            long timestamp, char fieldSeparator) {
        super(configurationName, timestamp);
        this.fieldSeparator = fieldSeparator;
    }

    public char getFieldSeparator() {
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