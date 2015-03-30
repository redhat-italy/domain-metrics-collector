/**
 * 
 */
package com.redhat.it.customers.dmc.core.services.data.export;

import com.redhat.it.customers.dmc.core.services.export.DataExporter;


/**
 * @author Andrea Battaglia
 *
 */
public interface DataExportService {

    DataExporter getDataExporter(String id);

}
