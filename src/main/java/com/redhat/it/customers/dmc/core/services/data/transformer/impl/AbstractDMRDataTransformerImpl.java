/**
 *
 */
package com.redhat.it.customers.dmc.core.services.data.transformer.impl;

import java.io.IOException;
import java.util.List;

import com.redhat.it.customers.dmc.core.dto.collector.qd.raw.AbstractRawQueryData;
import com.redhat.it.customers.dmc.core.dto.collector.qdk.t.AbstractTransformedQueryDataKey;
import com.redhat.it.customers.dmc.core.services.data.transformer.DataTransformer;

/**
 * The Class AbstractAppDataTransformerImpl.
 *
 * @author Andrea Battaglia (Red Hat)
 */
public abstract class AbstractDMRDataTransformerImpl<V> implements DataTransformer {

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

    protected abstract List<String> completeTransformation(
            AbstractTransformedQueryDataKey transformedKey,
            V valueArray);
    //
    // /**
    // * @see
    // com.redhat.it.customers.dmc.core.services.data.transformer.DataTransformer
    // *
    // #transformData(com.redhat.it.customers.dmc.core.dto.collector.AbstractRawQueryData)
    // */
    // @Override
    // public AbstractTransformedQueryData transformData(AbstractRawQueryData
    // input) {
    // // TODO Auto-generated method stub
    // return null;
    // }
}
