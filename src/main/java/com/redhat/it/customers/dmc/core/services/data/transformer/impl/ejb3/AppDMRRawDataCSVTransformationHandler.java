package com.redhat.it.customers.dmc.core.services.data.transformer.impl.ejb3;

import java.util.List;

import org.jboss.dmr.ModelNode;

import com.redhat.it.customers.dmc.core.enums.SupportedDMRSubsystemType;

/**
 * The Interface AppDMRRawDataCSVTransformationHandler.
 * 
 * @author Andrea Battaglia (Red Hat)
 */
public interface AppDMRRawDataCSVTransformationHandler {

    /**
     * Gets the supported dmr subsystem type.
     *
     * @return the supported dmr subsystem type
     */
    SupportedDMRSubsystemType getSupportedDMRSubsystemType();

    List<String[]> transformData(ModelNode value);
}