/**
 * 
 */
package com.redhat.it.customers.dmc.core.dto.collector.qdk.t;

/**
 * The Class CSVTransformedQueryDataKey.
 *
 * @author Andrea Battaglia (Red Hat)
 */
public class CSVTransformedQueryDataKey extends
        AbstractCSVTransformedQueryDataKey {

    /**
     * Instantiates a new CSV transformed query data key.
     *
     * @param configurationName
     *            the configuration name
     * @param timestamp
     *            the timestamp
     * @param fieldSeparator
     *            the field separator
     */
    public CSVTransformedQueryDataKey(String configurationName, long timestamp) {
        super(configurationName, timestamp);
    }

    /**
     * Instantiates a new CSV transformed query data key.
     *
     * @param configurationName
     *            the configuration name
     * @param timestamp
     *            the timestamp
     * @param fieldSeparator
     *            the field separator
     */
    public CSVTransformedQueryDataKey(String configurationName, long timestamp,
            char fieldSeparator) {
        super(configurationName, timestamp, fieldSeparator);
    }

}
