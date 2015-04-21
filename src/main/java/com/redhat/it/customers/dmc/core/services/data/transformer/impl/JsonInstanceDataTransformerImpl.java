/*
 *
 */
package com.redhat.it.customers.dmc.core.services.data.transformer.impl;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Map.Entry;

import org.codehaus.jackson.JsonNode;
import org.codehaus.jackson.node.ObjectNode;
import org.jboss.dmr.ModelNode;

import com.redhat.it.customers.dmc.core.dto.collector.qd.raw.AbstractRawQueryData;
import com.redhat.it.customers.dmc.core.dto.collector.qd.raw.DMRRawQueryData;
import com.redhat.it.customers.dmc.core.dto.collector.qd.t.AbstractTransformedQueryData;
import com.redhat.it.customers.dmc.core.dto.collector.qd.t.JsonTransformedQueryData;
import com.redhat.it.customers.dmc.core.dto.collector.qdk.raw.DMRRawQueryDataKey;
import com.redhat.it.customers.dmc.core.dto.collector.qdk.raw.InstanceDMRRawQueryDataKey;
import com.redhat.it.customers.dmc.core.dto.collector.qdk.t.AbstractTransformedQueryDataKey;
import com.redhat.it.customers.dmc.core.dto.collector.qdk.t.JsonTransformedQueryDataKey;
import com.redhat.it.customers.dmc.core.enums.ExportFormatType;
import com.redhat.it.customers.dmc.core.enums.ObjectMapperType;

/**
 * The Class JsonInstanceDataTransformerImpl.
 */
public class JsonInstanceDataTransformerImpl extends
        AbstractInstanceDataTransformerImpl<String> {

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
        JsonTransformedQueryDataKey transformedKey = null;
        rawData = (DMRRawQueryData) input;
        transformed = new JsonTransformedQueryData();
        for (Entry<DMRRawQueryDataKey, ModelNode> entry : rawData.entrySet()) {
            InstanceDMRRawQueryDataKey key = (InstanceDMRRawQueryDataKey) entry
                    .getKey();
            transformedKey = new JsonTransformedQueryDataKey(
                    key.getConfigurationName(), key.getTimestamp());

            transformedKey.setKeyElement(
                    InstanceQueryKeyElementType.HOST.getValue(), key.getHost());
            transformedKey.setKeyElement(
                    InstanceQueryKeyElementType.SERVER.getValue(),
                    key.getServer());
            transformedKey.setKeyElement(
                    InstanceQueryKeyElementType.SUBSYSTEM.getValue(),
                    key.getSubsystem());
            transformedKey.setKeyElement(key.getSubsystemComponentKey(),
                    key.getSubsystemComponentValue());
            transformedKey.setKeyElement(
                    key.getSubsystemComponentAttributeKey(),
                    key.getSubsystemComponentAttributeValue());

            transformed.put(
                    transformedKey,
                    completeTransformation(transformedKey,
                            entry.getValue().toJSONString(true)).get(0));
            key = null;
        }
        rawData = null;
        return transformed;
    }

    @Override
    protected List<String> completeTransformation(
            AbstractTransformedQueryDataKey transformedKey, String value) {
        String result = null;
        ObjectNode jsonValue = null;
        ObjectNode objectNode = null;

        try {
            objectNode = ObjectMapperType.DEFAULT.getObjectMapper()
                    .createObjectNode();

            objectNode.put("configurationId",
                    transformedKey.getConfigurationName());
            objectNode.put("timestamp", transformedKey.getTimestamp());
            for (Entry<String, Object> entry : transformedKey.getKeyElements()
                    .entrySet()) {
                objectNode.put(entry.getKey(), entry.getValue().toString());
            }
            jsonValue = (ObjectNode) ObjectMapperType.DEFAULT.getObjectMapper()
                    .readTree(value);
            objectNode.putAll(jsonValue);
            result=objectNode.toString();
        } catch (IOException e) {
            result = "";
        } finally {
            jsonValue = null;
            objectNode = null;
        }

        return Arrays.asList(result);
    }
}
