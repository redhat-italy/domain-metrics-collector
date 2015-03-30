package com.redhat.it.customers.dmc.core.services.data.export;

import com.redhat.it.customers.dmc.core.dto.collector.qd.t.AbstractTransformedQueryData;
import com.redhat.it.customers.dmc.core.interfaces.Openable;

/**
 * The Interface DataExporter.
 *
 * @author Andrea Battaglia (Red Hat)
 */
public interface DataExporter extends Openable{

    /**
     * Export data.
     *
     * @param data the data
     */
    void exportData(AbstractTransformedQueryData data);
}
