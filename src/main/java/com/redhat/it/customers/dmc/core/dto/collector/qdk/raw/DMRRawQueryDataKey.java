/**
 * 
 */
package com.redhat.it.customers.dmc.core.dto.collector.qdk.raw;

/**
 * The Class DMRRawQueryDataKey.
 *
 * @author Andrea Battaglia (Red Hat)
 */
public abstract class DMRRawQueryDataKey extends AbstractRawQueryDataKey {

    /**
     * Instantiates a new DMR raw query data key.
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
    public DMRRawQueryDataKey(String configurationName, long timestamp,
            String host, String server) {
        super(configurationName, timestamp, host, server);
        // TODO Auto-generated constructor stub
    }

}
