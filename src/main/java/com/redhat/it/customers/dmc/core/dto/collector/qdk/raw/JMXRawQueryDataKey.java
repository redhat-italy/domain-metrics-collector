/**
 * 
 */
package com.redhat.it.customers.dmc.core.dto.collector.qdk.raw;

/**
 * The Class JMXRawQueryDataKey.
 *
 * @author Andrea Battaglia (Red Hat)
 */
public class JMXRawQueryDataKey extends AbstractRawQueryDataKey {

    /**
     * Instantiates a new JMX raw query data key.
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
    public JMXRawQueryDataKey(String configurationName, long timestamp,
            String host, String server) {
        super(configurationName, timestamp, host, server);
        // TODO Auto-generated constructor stub
    }

}
