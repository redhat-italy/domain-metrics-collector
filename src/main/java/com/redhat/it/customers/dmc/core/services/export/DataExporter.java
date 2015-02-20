package com.redhat.it.customers.dmc.core.services.export;

import java.io.Closeable;

import com.redhat.it.customers.dmc.core.interfaces.Openable;

/**
 * The Interface DataExporterIF.
 * 
 * @author Andrea Battaglia
 */
public interface DataExporter extends Openable, Closeable {

    /**
     * Write data.
     *
     * @param rawData
     *            the raw data
     */
    void writeData(String[] rawData);

    /**
     * Sets the formatter configuration.
     *
     * @param separator
     *            the separator
     * @param quotechar
     *            the quotechar
     * @param escapechar
     *            the escapechar
     * @param lineEnd
     *            the line end
     */
    void setFormatterConfiguration(char separator, char quotechar,
            char escapechar, String lineEnd);

}