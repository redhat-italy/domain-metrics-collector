package com.redhat.it.customers.dmc.core.services.data.transformer.impl;

import java.io.StringWriter;
import java.io.Writer;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.TreeMap;

import javax.annotation.PostConstruct;
import javax.enterprise.inject.Instance;
import javax.inject.Inject;

import org.jboss.dmr.ModelNode;

import com.opencsv.CSVWriter;
import com.redhat.it.customers.dmc.core.constants.EmptyArrays;
import com.redhat.it.customers.dmc.core.dto.collector.qd.raw.AbstractRawQueryData;
import com.redhat.it.customers.dmc.core.dto.collector.qd.raw.DMRRawQueryData;
import com.redhat.it.customers.dmc.core.dto.collector.qd.t.AbstractTransformedQueryData;
import com.redhat.it.customers.dmc.core.dto.collector.qd.t.CSVTransformedQueryData;
import com.redhat.it.customers.dmc.core.dto.collector.qdk.raw.AppDMRRawQueryDataKey;
import com.redhat.it.customers.dmc.core.dto.collector.qdk.raw.DMRRawQueryDataKey;
import com.redhat.it.customers.dmc.core.dto.collector.qdk.t.CSVTransformedQueryDataKey;
import com.redhat.it.customers.dmc.core.enums.AppQueryKeyElementType;
import com.redhat.it.customers.dmc.core.enums.ExportFormatType;
import com.redhat.it.customers.dmc.core.enums.SupportedDMRSubsystemType;
import com.redhat.it.customers.dmc.core.services.data.transformer.impl.ejb3.AppDMRRawDataCSVTransformationHandler;
import com.redhat.it.customers.dmc.core.util.CSVFunctions;
import com.redhat.it.customers.dmc.core.util.JsonFunctions;

public class CSVAppDataTransformerImpl extends AbstractAppDataTransformerImpl {

    // private static final String[] EMPTY_STRING_ARRAY = new String[0];

    @Inject
    private JsonFunctions jsonFunctions;

    @Inject
    private Instance<AppDMRRawDataCSVTransformationHandler> transformationHandlerInstance;

    private String fieldSeparator;

    private final Map<SupportedDMRSubsystemType, AppDMRRawDataCSVTransformationHandler> transformationHandlers;

    {
        transformationHandlers = new TreeMap<>();
    }

    @PostConstruct
    private void init() {
        for (AppDMRRawDataCSVTransformationHandler csvTransformationHandler : transformationHandlerInstance)
            transformationHandlers.put(
                    csvTransformationHandler.getSupportedDMRSubsystemType(),
                    csvTransformationHandler);
    }

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
        AppDMRRawQueryDataKey key = null;
        // String[] keyArray = null;
        List<String[]> valueArray = null;
        CSVTransformedQueryDataKey transformedKey = null;

        data = (DMRRawQueryData) input;

        result = new CSVTransformedQueryData();

        for (Entry<DMRRawQueryDataKey, ModelNode> entry : data.entrySet()) {
            key = (AppDMRRawQueryDataKey) entry.getKey();
            transformedKey = new CSVTransformedQueryDataKey(
                    key.getConfigurationName(), key.getTimestamp());

            transformedKey.setKeyElement(
                    AppQueryKeyElementType.HOST.getValue(), key.getHost());
            transformedKey.setKeyElement(
                    AppQueryKeyElementType.SERVER.getValue(), key.getServer());
            transformedKey.setKeyElement(
                    AppQueryKeyElementType.DEPLOY.getValue(), key.getDeploy());
            transformedKey.setKeyElement(
                    AppQueryKeyElementType.SUBDEPLOY.getValue(),
                    key.getSubdeploy());
            transformedKey.setKeyElement(
                    AppQueryKeyElementType.SUBSYSTEM.getValue(),
                    key.getSubsystem());

            // keyArray = convertKey(key);
            SupportedDMRSubsystemType supportedDMRSubsystemType = SupportedDMRSubsystemType
                    .decode(key.getSubsystem());
            valueArray = transformationHandlers.get(supportedDMRSubsystemType)
                    .transformData(entry.getValue());
            result.put(transformedKey,
                    completeTransformation(transformedKey, valueArray));
            transformedKey = null;
            valueArray = null;
        }
        return result;
    }

    private List<String> completeTransformation(
            CSVTransformedQueryDataKey transformedKey, List<String[]> valueArray) {
        List<String> result = new ArrayList<>();
        // prepare base for csv row
        List<String> keyArray = new ArrayList<>();
        keyArray.add(transformedKey.getConfigurationName());
        keyArray.add(Long.toString(transformedKey.getTimestamp() / 1000L));
        for (Object value : transformedKey.getKeyElements().values())
            keyArray.add(value.toString());

        for (String[] raw : valueArray) {
            List<String> csvLineElements = new ArrayList<>(keyArray);
            csvLineElements.addAll(Arrays.asList(raw));
            result.add(CSVFunctions.getInstance().prepareCSVLine(
                    csvLineElements.toArray(EmptyArrays.EMPTY_STRING_ARRAY)));
        }

        return result;
    }
}
