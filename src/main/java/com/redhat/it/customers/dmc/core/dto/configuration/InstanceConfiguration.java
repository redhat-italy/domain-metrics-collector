package com.redhat.it.customers.dmc.core.dto.configuration;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;

import com.redhat.it.customers.dmc.core.constants.MetricType;

// TODO: Auto-generated Javadoc
/**
 * The Class InstanceConfiguration.
 * @author Andrea Battaglia
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "InstanceConfiguration", namespace = "dto.dmc.customers.it.redhat.com")
public class InstanceConfiguration extends Configuration {

    /**
     * Instantiates a new instance configuration.
     */
    public InstanceConfiguration() {
        metricType = MetricType.INSTANCE;
    }
}
