/**
 * 
 */
package com.redhat.it.customers.dmc.core.dto.collector.qdk;

/**
 * @author Andrea Battaglia (Red Hat)
 *
 */
public abstract class QueryDataKey {
    private long timestamp;
    
    public long getTimestamp() {
        return timestamp;
    }
    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }
    

}
