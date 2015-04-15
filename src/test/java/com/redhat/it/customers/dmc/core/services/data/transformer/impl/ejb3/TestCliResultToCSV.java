/**
 * 
 */
package com.redhat.it.customers.dmc.core.services.data.transformer.impl.ejb3;

import static org.junit.Assert.*;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

import org.jboss.dmr.ModelNode;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

/**
 * The Class TestCliResultToCSV.
 *
 * @author Andrea Battaglia (Red Hat)
 */
public class TestCliResultToCSV {

    /** The cli result. */
    private ModelNode cliResult;

    /**
     * Sets the up.
     *
     * @throws Exception
     *             the exception
     */
    @Before
    public void setUp() throws Exception {
        try (InputStream is = this.getClass().getResourceAsStream(
                "jsonExportResultExample.json")) {
            cliResult = ModelNode.fromJSONStream(is);
        } finally {
        }
    }

    /**
     * Tear down.
     *
     * @throws Exception
     *             the exception
     */
    @After
    public void tearDown() throws Exception {
        cliResult = null;
    }

    /**
     * Test convert.
     */
    @Test
    public void testConvert() {
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

        System.out.println("result:");
        for (String[] array : result) {
            System.out.println(Arrays.toString(array));
        }
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
