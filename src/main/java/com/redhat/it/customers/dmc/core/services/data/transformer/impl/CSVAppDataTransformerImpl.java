package com.redhat.it.customers.dmc.core.services.data.transformer.impl;

import com.redhat.it.customers.dmc.core.dto.collector.qd.raw.AbstractRawQueryData;
import com.redhat.it.customers.dmc.core.dto.collector.qd.t.AbstractTransformedQueryData;
import com.redhat.it.customers.dmc.core.enums.ExportFormatType;

public class CSVAppDataTransformerImpl extends AbstractAppDataTransformerImpl {

    public CSVAppDataTransformerImpl() {
        // TODO Auto-generated constructor stub
    }

    @Override
    public ExportFormatType getExportFormatType() {
        return ExportFormatType.CSV;
    }

    @Override
    public AbstractTransformedQueryData transformData(
            AbstractRawQueryData input) {
        // TODO Auto-generated method stub
        return null;
    }

}
