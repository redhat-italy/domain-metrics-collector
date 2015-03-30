/**
 * 
 */
package com.redhat.it.customers.dmc.core.dto.collector.qd.t;

import com.redhat.it.customers.dmc.core.dto.collector.qd.QueryData;
import com.redhat.it.customers.dmc.core.dto.collector.qdk.t.AbstractTransformedQueryDataKey;

/**
 * @author Andrea Battaglia (Red Hat)
 *
 */
public abstract class AbstractTransformedQueryData<K extends AbstractTransformedQueryDataKey, T>
        extends QueryData<K, T> {

}
