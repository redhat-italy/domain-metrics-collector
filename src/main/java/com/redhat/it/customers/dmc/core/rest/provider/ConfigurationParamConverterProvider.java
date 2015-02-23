package com.redhat.it.customers.dmc.core.rest.provider;

import java.lang.annotation.Annotation;
import java.lang.reflect.Type;

import javax.ws.rs.ext.ParamConverter;
import javax.ws.rs.ext.ParamConverterProvider;
import javax.ws.rs.ext.Provider;

import com.redhat.it.customers.dmc.core.dto.configuration.Configuration;
import com.redhat.it.customers.dmc.core.rest.paramconverter.ConfigurationParamConverter;

@Provider
public class ConfigurationParamConverterProvider implements
        ParamConverterProvider {

    @Override
    public <T> ParamConverter<T> getConverter(Class<T> rawType,
            Type genericType, Annotation[] annotations) {
        if (rawType.equals(Configuration.class)) {
            return (ParamConverter) new ConfigurationParamConverter();
        } else {
            return null;
        }
    }

}