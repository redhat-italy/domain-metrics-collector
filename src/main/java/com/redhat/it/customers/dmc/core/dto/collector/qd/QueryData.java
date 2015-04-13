package com.redhat.it.customers.dmc.core.dto.collector.qd;

import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import com.redhat.it.customers.dmc.core.dto.collector.qdk.QueryDataKey;

/**
 * The Class QueryData.
 *
 * @param <T>
 *            the generic type
 */
public abstract class QueryData<K extends QueryDataKey, T> {
    private Map<K, T> data;

    {
        data = new LinkedHashMap<K, T>();
    }

    /**
     * @return
     * @see java.util.Map#isEmpty()
     */
    public boolean isEmpty() {
        return data.isEmpty();
    }

    /**
     * @param key
     * @param value
     * @return
     * @see java.util.Map#put(java.lang.Object, java.lang.Object)
     */
    public T put(K key, T value) {
        return data.put(key, value);
    }

    /**
     * 
     * @see java.util.Map#clear()
     */
    public void clear() {
        data.clear();
    }

    /**
     * @return
     * @see java.util.Map#keySet()
     */
    public Set<K> keySet() {
        return data.keySet();
    }

    /**
     * @return
     * @see java.util.Map#values()
     */
    public Collection<T> values() {
        return data.values();
    }

    /**
     * @return
     * @see java.util.Map#entrySet()
     */
    public Set<Entry<K, T>> entrySet() {
        return data.entrySet();
    }

    /**
     * @see java.lang.Object#hashCode()
     */
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((data == null) ? 0 : data.hashCode());
        return result;
    }

    /**
     * @see java.lang.Object#equals(java.lang.Object)
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        QueryData other = (QueryData) obj;
        if (data == null) {
            if (other.data != null)
                return false;
        } else if (!data.equals(other.data))
            return false;
        return true;
    }

    /**
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("QueryData [\n    rawData=");
        builder.append(data);
        builder.append("\n]");
        return builder.toString();
    }

}
