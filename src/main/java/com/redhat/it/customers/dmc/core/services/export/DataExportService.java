/**
 * 
 */
package com.redhat.it.customers.dmc.core.services.export;


/**
 * @author Andrea Battaglia
 *
 */
public interface DataExportService {

    DataExporter getDataExporter(String id);

}
