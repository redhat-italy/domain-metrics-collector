/**
 * 
 */
package com.redhat.it.customers.dmc.core.services.data.export;

import java.util.Map.Entry;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.redhat.it.customers.dmc.core.dto.collector.qd.t.AbstractTransformedQueryData;
import com.redhat.it.customers.dmc.core.dto.collector.qd.t.CSVTransformedQueryData;
import com.redhat.it.customers.dmc.core.dto.collector.qd.t.JsonTransformedQueryData;
import com.redhat.it.customers.dmc.core.dto.collector.qd.t.RawCSVTransformedQueryData;
import com.redhat.it.customers.dmc.core.enums.ExportDestinationType;
import com.redhat.it.customers.dmc.core.exceptions.DMCCloseException;
import com.redhat.it.customers.dmc.core.exceptions.DMCOpenException;

/**
 * @author Andrea Battaglia (Red Hat)
 *
 */
public class FileDataExporterImpl implements DataExporter {
    private Logger LOG;

    @Override
    public ExportDestinationType getExportDestinationType() {
        return ExportDestinationType.FILE;
    }

    /**
     * @see com.redhat.it.customers.dmc.core.interfaces.Openable#open()
     */
    @Override
    public void open() throws DMCOpenException {
        LOG = LoggerFactory.getLogger("exportLogger");
    }

    /**
     * @see com.redhat.it.customers.dmc.core.interfaces.Openable#close()
     */
    @Override
    public void close() throws DMCCloseException {
        LOG = null;
    }

    /**
     * @see com.redhat.it.customers.dmc.core.services.data.export.DataExporter#exportData(com.redhat.it.customers.dmc.core.dto.collector.qd.t.AbstractTransformedQueryData)
     */
    @Override
    public void exportData(AbstractTransformedQueryData data) {
        // if (data instanceof CSVTransformedQueryData) {
        // logCSVData((CSVTransformedQueryData) data);
        // } else if (data instanceof JsonTransformedQueryData) {
        // logJsonData((JsonTransformedQueryData) data);
        // } else if (data instanceof RawCSVTransformedQueryData) {
        // logRawCSVData((RawCSVTransformedQueryData) data);
        // }
        for (Object value : data.values())
            LOG.trace(value.toString());
    }

    private void logCSVData(CSVTransformedQueryData data) {
        // TODO Auto-generated method stub

        // LOG.trace();
    }

    private void logJsonData(JsonTransformedQueryData data) {
        // TODO Auto-generated method stub

        // LOG.trace();
    }

    private void logRawCSVData(RawCSVTransformedQueryData data) {
        // TODO Auto-generated method stub

        // LOG.trace();
    }

}
