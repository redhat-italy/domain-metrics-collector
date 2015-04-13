/**
 * 
 */
package com.redhat.it.customers.dmc.core.dto.collector.qdk.t;

// TODO: Auto-generated Javadoc
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
     * @param host
     *            the host
     * @param server
     *            the server
     * @param fieldSeparator
     *            the field separator
     */
    public CSVTransformedQueryDataKey(String configurationName, long timestamp,
            String host, String server, String fieldSeparator) {
        super(configurationName, timestamp, host, server, fieldSeparator);
    }

}
