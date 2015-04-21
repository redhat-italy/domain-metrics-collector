/**
 *
 */
package com.redhat.it.customers.dmc.core.services.data.transformer.impl.ejb3;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import org.jboss.dmr.ModelNode;
import org.slf4j.Logger;

import com.redhat.it.customers.dmc.core.constants.EmptyArrays;
import com.redhat.it.customers.dmc.core.enums.SupportedDMRSubsystemType;
import com.redhat.it.customers.dmc.core.util.JsonFunctions;

/**
 * @author Andrea Battaglia (Red Hat)
 *
 */
public class GenericInstanceDMRRawDataCSVTransformationHandler implements
        InstanceDMRRawDataCSVTransformationHandler {

    @Inject
    private Logger LOG;

    @Inject
    private JsonFunctions jsonFunctions;

    /**
     * @see com.redhat.it.customers.dmc.core.services.data.transformer.impl.ejb3.InstanceDMRRawDataCSVTransformationHandler#getSupportedDMRSubsystemType()
     */
    @Override
    public SupportedDMRSubsystemType getSupportedDMRSubsystemType() {
        return null;
    }

    /**
     * @see com.redhat.it.customers.dmc.core.services.data.transformer.impl.ejb3.InstanceDMRRawDataCSVTransformationHandler#transformData(org.jboss.dmr.ModelNode)
     */
    @Override
    public List<String[]> transformData(ModelNode value) {
        List<String[]> result = null;
        result = new ArrayList<String[]>();
        try {
            result.add(jsonFunctions
                    .createFlatKeyValues(value.toJSONString(true)).values()
                    .toArray(EmptyArrays.EMPTY_STRING_ARRAY));
        } catch (IOException e) {
            LOG.warn("", e);
        }
        return result;
    }

}
