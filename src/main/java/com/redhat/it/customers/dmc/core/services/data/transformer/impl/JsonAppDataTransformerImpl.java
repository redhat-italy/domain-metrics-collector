package com.redhat.it.customers.dmc.core.services.data.transformer.impl;

import java.util.Map.Entry;

import org.jboss.dmr.ModelNode;

import com.redhat.it.customers.dmc.core.dto.collector.qd.raw.AbstractRawQueryData;
import com.redhat.it.customers.dmc.core.dto.collector.qd.raw.DMRRawQueryData;
import com.redhat.it.customers.dmc.core.dto.collector.qd.t.AbstractTransformedQueryData;
import com.redhat.it.customers.dmc.core.dto.collector.qd.t.JsonTransformedQueryData;
import com.redhat.it.customers.dmc.core.dto.collector.qdk.raw.AppDMRRawQueryDataKey;
import com.redhat.it.customers.dmc.core.dto.collector.qdk.raw.DMRRawQueryDataKey;
import com.redhat.it.customers.dmc.core.dto.collector.qdk.t.JsonTransformedQueryDataKey;
import com.redhat.it.customers.dmc.core.enums.AppQueryKeyElementType;
import com.redhat.it.customers.dmc.core.enums.ExportFormatType;

/**
 * The Class JsonAppDataTransformerImpl.
 */
public class JsonAppDataTransformerImpl extends AbstractAppDataTransformerImpl {

    @Override
    public ExportFormatType getExportFormatType() {
        return ExportFormatType.JSON;
    }

    /**
     * @see com.redhat.it.customers.dmc.core.services.data.transformer.DataTransformer
     *      #transformData(com.redhat.it.customers.dmc.core.dto.collector.qd.raw.AbstractRawQueryData)
     */
    @Override
    public AbstractTransformedQueryData transformData(
            final AbstractRawQueryData input) {
        DMRRawQueryData rawData = null;
        JsonTransformedQueryData transformed = null;
        JsonTransformedQueryDataKey key = null;
        rawData = (DMRRawQueryData) input;
        transformed = new JsonTransformedQueryData();
        for (Entry<DMRRawQueryDataKey, ModelNode> entry : rawData.entrySet()) {
            AppDMRRawQueryDataKey rawQueryDataKey = (AppDMRRawQueryDataKey) entry
                    .getKey();
            key = new JsonTransformedQueryDataKey(
                    rawQueryDataKey.getConfigurationName(),
                    rawQueryDataKey.getTimestamp());
            key.setKeyElement(AppQueryKeyElementType.HOST.getValue(),
                    rawQueryDataKey.getHost());
            key.setKeyElement(AppQueryKeyElementType.SERVER.getValue(),
                    rawQueryDataKey.getServer());
            key.setKeyElement(AppQueryKeyElementType.DEPLOY.getValue(),
                    rawQueryDataKey.getHost());
            key.setKeyElement(AppQueryKeyElementType.SUBSYSTEM.getValue(),
                    rawQueryDataKey.getSubsystem());
            key.setKeyElement(AppQueryKeyElementType.SUBDEPLOY.getValue(),
                    rawQueryDataKey.getSubdeploy());
            transformed.put(key,
                    entry.getValue().toJSONString(true));
            rawQueryDataKey = null;
        }
        rawData = null;
        return transformed;
    }


}
