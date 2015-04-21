/**
 *
 */
package com.redhat.it.customers.dmc.core.services.data.transformer.impl;

import com.redhat.it.customers.dmc.core.enums.MetricType;

/**
 * The Class AbstractAppDataTransformerImpl.
 *
 * @author Andrea Battaglia (Red Hat)
 */
public abstract class AbstractInstanceDataTransformerImpl<V> extends
        AbstractDMRDataTransformerImpl<V> {

    /**
     * @see com.redhat.it.customers.dmc.core.services.data.transformer.DataTransformer#getMetricType()
     */
    @Override
    public MetricType getMetricType() {
        return MetricType.INSTANCE;
    }
}
