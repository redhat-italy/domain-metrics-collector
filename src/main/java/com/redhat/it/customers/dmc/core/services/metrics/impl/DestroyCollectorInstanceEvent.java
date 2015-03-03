package com.redhat.it.customers.dmc.core.services.metrics.impl;

import com.redhat.it.customers.dmc.core.constants.MetricType;
import com.redhat.it.customers.dmc.core.services.metrics.MetricCollectorService;

/**
 * The Class DestroyCollectorInstanceEvent. Contains data transported by the
 * event fired by the collector instance to notify the
 * {@link MetricCollectorService} that tha instance stopped workibg by user call
 * and can be removed from heap.
 */
public class DestroyCollectorInstanceEvent {

    /** The metric type. */
    private MetricType metricType;
    
    /** The collector name. */
    private String collectorName;

    /**
     * Instantiates a new destroy collector instance event.
     *
     * @param metricType            the metric type
     * @param collectorName the collector name
     */
    public DestroyCollectorInstanceEvent(final MetricType metricType,
            final String collectorName) {
        super();
        this.metricType = metricType;
        this.collectorName = collectorName;
    }

    /**
     * Gets the metric type.
     *
     * @return the metric type
     */
    public MetricType getMetricType() {
        return metricType;
    }

    /**
     * Gets the collector name.
     *
     * @return the collector name
     */
    public String getCollectorName() {
        return collectorName;
    }
}
