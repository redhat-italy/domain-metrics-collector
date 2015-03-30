/**
 * 
 */
package com.redhat.it.customers.dmc.core.services.data.export.impl;

import javax.annotation.PostConstruct;
import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.context.Initialized;
import javax.enterprise.event.Observes;
import javax.inject.Inject;

import org.slf4j.Logger;

import com.redhat.it.customers.dmc.core.dto.configuration.Configuration;
import com.redhat.it.customers.dmc.core.services.configuration.ConfigurationService;
import com.redhat.it.customers.dmc.core.services.data.export.DataExportService;
import com.redhat.it.customers.dmc.core.services.export.DataExporter;

/**
 * @author Andrea Battaglia
 *
 */
@ApplicationScoped
public class DataExportServiceImpl implements DataExportService {

    @Inject
    private Logger LOG;

    @Inject
    private ConfigurationService configurationService;

    void init(@Observes @Initialized(ApplicationScoped.class) Object event) {
        LOG.debug("Starting");
    }

    @PostConstruct
    private void init() {
        // LOG.info("I AM " + this.getClass().getSimpleName());
    }

    @Override
    public DataExporter getDataExporter(String configurationId) {
//        Configuration configuration = null;
//        String filename = null;
//        configuration = configurationService.getConfiguration(configurationId);
//        filename = configuration.getFileName();
//        if (filename == null)
//            filename = configurationService
//                    .getConfigurationFilesPath()
//                    .resolve(
//                            configurationId
//                                    + configurationService
//                                            .getExportFileExtension())
//                    .toString();
//        DataExporter dataExporter = new DataExporterImpl(configuration.getId(),
//                filename);
//        return dataExporter;
        return null;
    }
}
