package com.redhat.it.customers.dmc.core.dto.collector.qd;

import com.redhat.it.customers.dmc.core.dto.collector.qdk.QueryDataKey;

/**
 * The Class QueryData.
 *
 * @param <T>
 *            the generic type
 */
public abstract class QueryData<K extends QueryDataKey, T> {
    private K key;

    /** The data. */
    private T data;

    public K getDataKey() {
        return key;
    }

    public void setDataKey(K key) {
        this.key = key;
    }

    /**
     * @see com.redhat.it.customers.dmc.core.cdi.bindings.DMCQueryData#getData()
     */

    public T getData() {
        return data;
    }

    /**
     * @see com.redhat.it.customers.dmc.core.cdi.bindings.DMCQueryData#setData(T)
     */

    public void setData(T data) {
        this.data = data;
    }

    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((key == null) ? 0 : key.hashCode());
        return result;
    }

    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        QueryData other = (QueryData) obj;
        if (key == null) {
            if (other.key != null)
                return false;
        } else if (!key.equals(other.key))
            return false;
        return true;
    }

    /**
     * @see java.lang.Object#toString()
     */

    public String toString() {
        return "QueryData [data=" + data + "]";
    }

}
