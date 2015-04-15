/**
 * 
 */
package com.redhat.it.customers.dmc.core.dto.collector.qdk.t;

// TODO: Auto-generated Javadoc
/**
 * The Class RawCSVTransformedQueryDataKey.
 *
 * @author Andrea Battaglia (Red Hat)
 */
public class RawCSVTransformedQueryDataKey extends
        AbstractCSVTransformedQueryDataKey {

    /**
     * Instantiates a new raw csv transformed query data key.
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
    public RawCSVTransformedQueryDataKey(String configurationName,
            long timestamp, String host, String server) {
        super(configurationName, timestamp);
    }

}
