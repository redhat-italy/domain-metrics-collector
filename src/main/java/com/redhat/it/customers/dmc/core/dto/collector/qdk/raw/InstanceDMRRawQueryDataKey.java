package com.redhat.it.customers.dmc.core.dto.collector.qdk.raw;

/**
 * The Class InstanceDMRRawQueryDataKey.
 * 
 * @author Andrea Battaglia (Red Hat)
 */
public class InstanceDMRRawQueryDataKey extends DMRRawQueryDataKey {

    /**
     * Instantiates a new instance dmr raw query data key.
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
    public InstanceDMRRawQueryDataKey(String configurationName, long timestamp,
            String host, String server) {
        super(configurationName, timestamp, host, server);
        // TODO Auto-generated constructor stub
    }

}
