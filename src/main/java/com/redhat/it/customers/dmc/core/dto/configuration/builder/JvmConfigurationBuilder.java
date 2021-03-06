// CHECKSTYLE:OFF
/**
 * Source code generated by Fluent Builders Generator
 * Do not modify this file
 * See generator home page at: http://code.google.com/p/fluent-builders-generator-eclipse-plugin/
 */

package com.redhat.it.customers.dmc.core.dto.configuration.builder;

import com.redhat.it.customers.dmc.core.dto.configuration.Configuration;
import com.redhat.it.customers.dmc.core.dto.configuration.JvmConfiguration;

/**
 * The Class ConfigurationBuilder.
 * 
 * @author Andrea Battaglia
 */
public class JvmConfigurationBuilder extends
        ConfigurationBuilderBase<JvmConfigurationBuilder> {

    private final JvmConfiguration configuration;

    /**
     * Configuration.
     *
     * @return the configuration builder
     */
    public static JvmConfigurationBuilder configuration() {
        return new JvmConfigurationBuilder();
    }

    /**
     * Instantiates a new configuration builder.
     */
    public JvmConfigurationBuilder() {
        configuration = new JvmConfiguration();
    }

    /**
     * Builds the.
     *
     * @return the configuration
     */
    public Configuration build() {
        return getInstance();
    }

    @Override
    public Configuration getInstance() {
        return configuration;
    }
}
