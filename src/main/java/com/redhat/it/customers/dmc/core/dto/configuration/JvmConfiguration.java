package com.redhat.it.customers.dmc.core.dto.configuration;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;

import com.redhat.it.customers.dmc.core.constants.MetricType;

/**
 * The Class JvmConfiguration.
 * 
 * @author Andrea Battaglia
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "JvmConfiguration", namespace = "dto.dmc.customers.it.redhat.com")
public class JvmConfiguration extends Configuration {

    /**
     * Instantiates a new jvm configuration.
     */
    public JvmConfiguration() {
        metricType = MetricType.JVM;
    }

}
