package com.redhat.it.customers.dmc.core.services.data.transformer.impl.ejb3;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

import javax.enterprise.context.ApplicationScoped;

import org.jboss.dmr.ModelNode;

import com.redhat.it.customers.dmc.core.enums.SupportedDMRSubsystemType;

/**
 * The Class EJB3AppDMRRawDataTransformationHandler.
 *
 * @author Andrea Battaglia (Red Hat)
 */
@ApplicationScoped
public class EJB3AppDMRRawDataTransformationHandler implements
        AppDMRRawDataCSVTransformationHandler {

    /**
     * Gets the supported dmr subsystem type.
     *
     * @return the supported dmr subsystem type
     * @see com.redhat.it.customers.dmc.core.services.data.transformer.impl.ejb3.AppDMRRawDataCSVTransformationHandler
     *      #getSupportedDMRSubsystemType()
     */
    @Override
    public final SupportedDMRSubsystemType getSupportedDMRSubsystemType() {
        return SupportedDMRSubsystemType.EJB3;
    }

    /**
     * @see com.redhat.it.customers.dmc.core.services.data.transformer.impl.ejb3.AppDMRRawDataCSVTransformationHandler
     * #transformData(org.jboss.dmr.ModelNode)
     */
    @Override
    public List<String[]> transformData(ModelNode cliResult) {
        List<String[]> result = null;
        Set<String> subsystemComponentNames = null;
        ModelNode subsystemComponent = null;

        result = new ArrayList<>();

        subsystemComponentNames = cliResult.keys();
        for (String subsystemComponentName : subsystemComponentNames) {
            subsystemComponent = cliResult.get(subsystemComponentName);
            handleSubsystemComponent(result, subsystemComponentName,
                    subsystemComponent);
            subsystemComponent = null;
        }

        subsystemComponentNames = null;
        subsystemComponent = null;

        return result;
    }

    /**
     * Handle subsystem component.
     *
     * @param result
     *            the result
     * @param subsystemComponentName
     *            the subsystem component name
     * @param subsystemComponent
     *            the subsystem component
     */
    private void handleSubsystemComponent(List<String[]> result,
            String subsystemComponentName, ModelNode subsystemComponent) {
        Set<String> appObjectNames = null;
        ModelNode appObject = null;

        appObjectNames = subsystemComponent.keys();
        for (String appObjectName : appObjectNames) {
            appObject = subsystemComponent.get(appObjectName);
            handleAppObject(result, subsystemComponentName, appObjectName,
                    appObject);
            appObject = null;
        }
        appObjectNames = null;
    }

    /**
     * Handle app object.
     *
     * @param result
     *            the result
     * @param subsystemComponentName
     *            the subsystem component name
     * @param appObjectName
     *            the app object name
     * @param appObject
     *            the app object
     */
    private void handleAppObject(List<String[]> result,
            String subsystemComponentName, String appObjectName,
            ModelNode appObject) {
        ModelNode methods = null;
        Set<String> methodNames = null;
        ModelNode method = null;
        handleAppObjectAttributes(result, subsystemComponentName,
                appObjectName, appObject);
        methods = appObject.get("methods");
        methodNames = methods.keys();
        for (String methodName : methodNames) {
            method = methods.get(methodName);
            handleAppObjectComponents(result, subsystemComponentName,
                    appObjectName, methodName, method);
            method = null;
        }
        methods = null;
        methodNames = null;
    }

    /**
     * Handle app object attributes.
     *
     * @param result
     *            the result
     * @param subsystemComponentName
     *            the subsystem component name
     * @param appObjectName
     *            the app object name
     * @param appObject
     *            the app object
     */
    private void handleAppObjectAttributes(List<String[]> result,
            String subsystemComponentName, String appObjectName,
            ModelNode appObject) {
        Set<String> appObjectAttributeNames = null;
        Set<String> no = null;
        String value = null;
        String appObjectComponentName = null;

        appObjectAttributeNames = appObject.keys();
        no = new TreeSet<>(Arrays.asList("methods", "component-class-name",
                "declared-roles"));
        appObjectComponentName = "";

        for (String appObjectAttributeName : appObjectAttributeNames) {
            if (!no.contains(appObjectAttributeName)) {
                value = appObject.get(appObjectAttributeName).toString();
                addResultToList(result, subsystemComponentName, appObjectName,
                        appObjectComponentName, appObjectAttributeName, value);
                value = null;
            }
        }
        appObjectAttributeNames = null;
        no = null;
    }

    /**
     * Handle app object components.
     *
     * @param result
     *            the result
     * @param subsystemComponentName
     *            the subsystem component name
     * @param appObjectName
     *            the app object name
     * @param methodName
     *            the method name
     * @param method
     *            the method
     */
    private void handleAppObjectComponents(List<String[]> result,
            String subsystemComponentName, String appObjectName,
            String methodName, ModelNode method) {
        for (String methodAttributeName : method.keys())
            addResultToList(result, subsystemComponentName, appObjectName,
                    methodName, methodAttributeName,
                    method.get(methodAttributeName).toString());

    }

    /**
     * Adds the result to list.
     *
     * @param result
     *            the result
     * @param subsystemComponentName
     *            the subsystem component name
     * @param appObjectName
     *            the app object name
     * @param appObjectComponentName
     *            the app object component name
     * @param appObjectAttributeName
     *            the app object attribute name
     * @param value
     *            the value
     */
    private void addResultToList(List<String[]> result,
            String subsystemComponentName, String appObjectName,
            String appObjectComponentName, String appObjectAttributeName,
            String value) {
        result.add(new String[] { subsystemComponentName, appObjectName,
                appObjectComponentName, appObjectAttributeName, value });
    }
}
