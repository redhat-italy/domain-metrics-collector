package com.redhat.it.customers.dmc.core.rest.paramconverter;

import java.io.IOException;

import javax.inject.Inject;
import javax.ws.rs.ext.ParamConverter;

import org.codehaus.jackson.map.ObjectMapper;
import org.slf4j.Logger;

import com.redhat.it.customers.dmc.core.dto.configuration.Configuration;
import com.redhat.it.customers.dmc.core.enums.ObjectMapperType;

public class ConfigurationParamConverter implements
        ParamConverter<Configuration> {

    private static final ObjectMapper converter = ObjectMapperType.DEFAULT
            .getObjectMapper();

    @Inject
    private Logger LOG;

    @Override
    public Configuration fromString(String value) {
        try {
            return converter.readValue(value, Configuration.class);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public String toString(Configuration value) {
        try {
            return converter.writeValueAsString(value);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}
