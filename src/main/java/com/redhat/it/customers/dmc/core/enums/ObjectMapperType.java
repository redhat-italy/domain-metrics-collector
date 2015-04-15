package com.redhat.it.customers.dmc.core.enums;

import org.codehaus.jackson.map.ObjectMapper;

/**
 * The Enum ObjectMapperType.
 * 
 * @see ObjectMapper
 * 
 * @author Andrea Battaglia (Red Hat)
 */
public enum ObjectMapperType {

    /** The default. */
    DEFAULT(new ObjectMapper()) {
        /**
         * @see com.redhat.it.customers.dmc.core.enums.ObjectMapperType
         *      #customizeMapper()
         */
        @Override
        protected void customizeMapper() {
        }
    };

    /** The object mapper. */
    private final ObjectMapper objectMapper;

    /**
     * Instantiates a new object mapper type.
     *
     * @param objectMapper
     *            the object mapper
     */
    private ObjectMapperType(final ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    /**
     * Customize mapper.
     */
    protected abstract void customizeMapper();

    /**
     * Gets the object mapper.
     *
     * @return the object mapper
     */
    public ObjectMapper getObjectMapper() {
        return objectMapper;
    }
}
