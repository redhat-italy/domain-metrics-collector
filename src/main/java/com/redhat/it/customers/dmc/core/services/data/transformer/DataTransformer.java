package com.redhat.it.customers.dmc.core.services.data.transformer;

import java.io.IOException;

import com.redhat.it.customers.dmc.core.dto.collector.qd.raw.AbstractRawQueryData;
import com.redhat.it.customers.dmc.core.dto.collector.qd.t.AbstractTransformedQueryData;

/**
 * The Interface DataTransformer.
 * 
 * @author Andrea Battaglia (Red Hat)
 */
public interface DataTransformer {

    /**
     * Extract header.
     *
     * @param input the input
     * @return the string[]
     * @throws IOException             Signals that an I/O exception has occurred.
     */
    String[] extractHeader(AbstractRawQueryData input) throws IOException;

    /**
     * Transform data.
     *
     * @param input            the input
     * @return the t
     */
    AbstractTransformedQueryData transformData(AbstractRawQueryData input);
}
