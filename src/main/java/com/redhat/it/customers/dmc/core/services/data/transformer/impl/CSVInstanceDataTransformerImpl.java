/*
 *
 */
package com.redhat.it.customers.dmc.core.services.data.transformer.impl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map.Entry;

import javax.inject.Inject;

import org.jboss.dmr.ModelNode;

import com.redhat.it.customers.dmc.core.constants.EmptyArrays;
import com.redhat.it.customers.dmc.core.dto.collector.qd.raw.AbstractRawQueryData;
import com.redhat.it.customers.dmc.core.dto.collector.qd.raw.DMRRawQueryData;
import com.redhat.it.customers.dmc.core.dto.collector.qd.t.AbstractTransformedQueryData;
import com.redhat.it.customers.dmc.core.dto.collector.qd.t.CSVTransformedQueryData;
import com.redhat.it.customers.dmc.core.dto.collector.qdk.raw.DMRRawQueryDataKey;
import com.redhat.it.customers.dmc.core.dto.collector.qdk.raw.InstanceDMRRawQueryDataKey;
import com.redhat.it.customers.dmc.core.dto.collector.qdk.t.AbstractTransformedQueryDataKey;
import com.redhat.it.customers.dmc.core.dto.collector.qdk.t.CSVTransformedQueryDataKey;
import com.redhat.it.customers.dmc.core.enums.ExportFormatType;
import com.redhat.it.customers.dmc.core.enums.SupportedDMRSubsystemType;
import com.redhat.it.customers.dmc.core.services.data.transformer.impl.ejb3.InstanceDMRRawDataCSVTransformationHandler;
import com.redhat.it.customers.dmc.core.util.CSVFunctions;
import com.redhat.it.customers.dmc.core.util.JsonFunctions;

public class CSVInstanceDataTransformerImpl extends
        AbstractInstanceDataTransformerImpl<List<String[]>> {

    // private static final String[] EMPTY_STRING_ARRAY = new String[0];

    @Inject
    private JsonFunctions jsonFunctions;

    @Inject
    private InstanceDMRRawDataCSVTransformationHandler transformationHandler;

    private String fieldSeparator;

    @Override
    public ExportFormatType getExportFormatType() {
        return ExportFormatType.CSV;
    }

    public String getFieldSeparator() {
        return fieldSeparator;
    }

    public void setFieldSeparator(String fieldSeparator) {
        this.fieldSeparator = fieldSeparator;
    }

    @Override
    public AbstractTransformedQueryData transformData(AbstractRawQueryData input) {
        CSVTransformedQueryData result = null;
        DMRRawQueryData data = null;
        InstanceDMRRawQueryDataKey key = null;
        // String[] keyArray = null;
        List<String[]> valueArray = null;
        CSVTransformedQueryDataKey transformedKey = null;

        data = (DMRRawQueryData) input;

        result = new CSVTransformedQueryData();

        for (Entry<DMRRawQueryDataKey, ModelNode> entry : data.entrySet()) {
            key = (InstanceDMRRawQueryDataKey) entry.getKey();
            transformedKey = new CSVTransformedQueryDataKey(
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

            // keyArray = convertKey(key);
            SupportedDMRSubsystemType supportedDMRSubsystemType = SupportedDMRSubsystemType
                    .decode(key.getSubsystem());
            valueArray = transformationHandler.transformData(entry.getValue());
            result.put(transformedKey,
                    completeTransformation(transformedKey, valueArray));
            transformedKey = null;
            valueArray = null;
        }
        return result;
    }

    @Override
    protected List<String> completeTransformation(
            AbstractTransformedQueryDataKey transformedKey,
            List<String[]> valueArray) {
        List<String> result = new ArrayList<>();
        // prepare base for csv row
        List<String> keyArray = new ArrayList<>();
        keyArray.add(transformedKey.getConfigurationName());
        keyArray.add(Long.toString(transformedKey.getTimestamp() / 1000L));
        for (Object value : transformedKey.getKeyElements().values()) {
            keyArray.add(value.toString());
        }

        for (String[] raw : valueArray) {
            List<String> csvLineElements = new ArrayList<>(keyArray);
            csvLineElements.addAll(Arrays.asList(raw));
            result.add(CSVFunctions.getInstance().prepareCSVLine(
                    csvLineElements.toArray(EmptyArrays.EMPTY_STRING_ARRAY)));
        }

        return result;
    }
}
