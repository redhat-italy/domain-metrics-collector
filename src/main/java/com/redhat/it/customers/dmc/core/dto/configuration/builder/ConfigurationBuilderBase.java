package com.redhat.it.customers.dmc.core.dto.configuration.builder;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import com.redhat.it.customers.dmc.core.constants.Constants;
import com.redhat.it.customers.dmc.core.dto.configuration.Configuration;

abstract class ConfigurationBuilderBase<T extends ConfigurationBuilderBase<T>> {

    public static final String DEFAULT_HOST = "localhost";
    public static final int DEFAULT_PORT = 9999;
    public static final int DEFAULT_SCAN_INTERVAL = 120;

    protected abstract Configuration getInstance();

    @SuppressWarnings("unchecked")
    public T withId(String aValue) {
        getInstance().setId(checkStringValueWithException(aValue));

        return (T) this;
    }

    @SuppressWarnings("unchecked")
    public T withHostname(String aValue) {
        getInstance().setHostname(checkStringValue(aValue) == null ? DEFAULT_HOST
                : aValue);

        return (T) this;
    }

    @SuppressWarnings("unchecked")
    public T withPort(Integer aValue) {
        int value = checkIntegerValue(aValue);
        getInstance().setPort(value == 0 ? DEFAULT_PORT : value);

        return (T) this;
    }

    @SuppressWarnings("unchecked")
    public T withUsername(String aValue) {
        getInstance().setUsername(checkStringValue(aValue));

        return (T) this;
    }

    @SuppressWarnings("unchecked")
    public T withPassword(String aValue) {
        getInstance().setPassword(checkStringValue(aValue));

        return (T) this;
    }

    @SuppressWarnings("unchecked")
    public T withRealm(String aValue) {
        getInstance().setRealm(checkStringValue(aValue));

        return (T) this;
    }

    @SuppressWarnings("unchecked")
    public T withRegexpHostname(String aValue) {
        getInstance().setRegexpHostname(checkStringValue(aValue));

        return (T) this;
    }

    @SuppressWarnings("unchecked")
    public T withRegexpServer(String aValue) {
        getInstance().setRegexpServer(checkStringValue(aValue));

        return (T) this;
    }

    @SuppressWarnings("unchecked")
    public T withApps(String aValue) {
        String value = checkStringValue(aValue);
        Set<String> appsSet = new HashSet<String>();
        if (value != null) {
            appsSet.addAll(Arrays.asList(value
            //
                    .split(Constants.DEFAULT_SEPARATOR.getValue())));
        }
        // .split(", *"));
        //
        return (T) this;
    }

    @SuppressWarnings("unchecked")
    public T withScanInterval(Integer aValue) {
        int value = checkIntegerValue(aValue);
        getInstance().setScanInterval(value == 0 ? DEFAULT_SCAN_INTERVAL : value);

        return (T) this;
    }

    @SuppressWarnings("unchecked")
    public T withStart(boolean aValue) {
        getInstance().setStart(aValue);

        return (T) this;
    }

    private static final int checkIntegerValue(Integer aValue) {
        if ((aValue == null) || (aValue == 0)) {
            return 0;
        }
        return aValue;
    }

    private static final String checkStringValue(String aValue) {
        if ((aValue == null) || aValue.equals("")) {
            return null;
        }
        return aValue;
    }

    private static final String checkStringValueWithException(String aValue) {
        if ((aValue == null) || aValue.equals("")) {
            throw new IllegalArgumentException(
                    "null or empty values not admitted");
        }
        return aValue;
    }

}
