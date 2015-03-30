/**
 * 
 */
package com.redhat.it.customers.dmc.core.services.data.transformer.impl;

import java.io.IOException;

import org.jboss.dmr.ModelNode;

import com.redhat.it.customers.dmc.core.dto.collector.qd.raw.AbstractRawQueryData;
import com.redhat.it.customers.dmc.core.services.data.transformer.DataTransformer;

/**
 * The Class AbstractAppDataTransformerImpl.
 *
 * @author Andrea Battaglia (Red Hat)
 */
public abstract class AbstractJMXDataTransformerImpl implements DataTransformer {

    /**
     * Instantiates a new abstract app data transformer impl.
     */
    public AbstractJMXDataTransformerImpl() {
    }
    
    @Override
    public String[] extractHeader(AbstractRawQueryData input) throws IOException {
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
