package com.redhat.it.customers.dmc.core.cdi.bindings;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import javax.inject.Qualifier;

/**
 * The Interface ConfigurationId.
 * 
 * @author Andrea Battaglia
 */
@Qualifier
@Retention(RetentionPolicy.RUNTIME)
@Target({ ElementType.FIELD, ElementType.TYPE, ElementType.METHOD })
public @interface ConfigurationId {

    /**
     * Value.
     *
     * @return the string
     */
    String configurationId();
}
