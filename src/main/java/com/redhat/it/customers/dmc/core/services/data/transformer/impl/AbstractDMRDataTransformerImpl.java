/**
 * 
 */
package com.redhat.it.customers.dmc.core.services.data.transformer.impl;

import java.io.IOException;

import com.redhat.it.customers.dmc.core.dto.collector.qd.raw.AbstractRawQueryData;
import com.redhat.it.customers.dmc.core.services.data.transformer.DataTransformer;

/**
 * The Class AbstractAppDataTransformerImpl.
 *
 * @author Andrea Battaglia (Red Hat)
 */
public abstract class AbstractDMRDataTransformerImpl implements DataTransformer {

    /**
     * Instantiates a new abstract app data transformer impl.
     */
    public AbstractDMRDataTransformerImpl() {
    }
 
    @Override
    public String[] extractHeader(AbstractRawQueryData input)
            throws IOException {
        // TODO Auto-generated method stub
        return null;
    }
//
//    /**
//     * @see com.redhat.it.customers.dmc.core.services.data.transformer.DataTransformer
//     *      #transformData(com.redhat.it.customers.dmc.core.dto.collector.AbstractRawQueryData)
//     */
//    @Override
//    public AbstractTransformedQueryData transformData(AbstractRawQueryData input) {
//        // TODO Auto-generated method stub
//        return null;
//    }
}
