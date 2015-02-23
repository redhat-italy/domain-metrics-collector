/**
 * 
 */
package com.redhat.it.customers.dmc.core.dto.configuration;

import java.util.Set;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;

import com.redhat.it.customers.dmc.core.constants.Constants;
import com.redhat.it.customers.dmc.core.constants.MetricType;

/**
 * The Class Configuration.
 *
 * @author Andrea Battaglia
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "AppConfiguration", namespace = "dto.dmc.customers.it.redhat.com")
public class AppConfiguration extends Configuration {

    /** The apps. */
    private Set<String> apps;

    /** The regexp subdeployment. */
    private String regexpSubdeployment=Constants.REGEXP_CATCHALL.getValue();

    /** The subsystem. */
    private String subsystem;

    /** The app object name regex. */
    private String regexAppObjectName=Constants.REGEXP_CATCHALL.getValue();

    /** The app object attributes. */
    private Set<String> appObjectAttributes;

    /** The app object attribute exclude. */
    private boolean appObjectAttributeExclude;

    /** The app object detail. */
    private String appObjectDetail;

    /** The app object detail attributes. */
    private Set<String> appObjectDetailAttributes;

    /** The app object detail attribute exclude. */
    private boolean appObjectDetailAttributeExclude;
    
    public AppConfiguration() {
        metricType=MetricType.APP;
    }

    /**
     * Gets the apps.
     *
     * @return the apps
     */
    public Set<String> getApps() {
        return apps;
    }

    /**
     * Sets the apps.
     *
     * @param apps
     *            the apps to set
     */
    public void setApps(Set<String> apps) {
        this.apps = apps;
    }

    /**
     * Gets the regexp subdeployment.
     *
     * @return the regexpSubdeployment
     */
    public String getRegexpSubdeployment() {
        return regexpSubdeployment;
    }

    /**
     * Sets the regexp subdeployment.
     *
     * @param regexpSubdeployment
     *            the regexpSubdeployment to set
     */
    public void setRegexpSubdeployment(String regexpSubdeployment) {
        this.regexpSubdeployment = regexpSubdeployment;
    }

    /**
     * Gets the subsystem.
     *
     * @return the subsystem
     */
    public String getSubsystem() {
        return subsystem;
    }

    /**
     * Sets the subsystem.
     *
     * @param subsystem
     *            the subsystem to set
     */
    public void setSubsystem(String subsystem) {
        this.subsystem = subsystem;
    }

    /**
     * Gets the app object name regex.
     *
     * @return the regexAppObjectName
     */
    public String getRegexAppObjectName() {
        return regexAppObjectName;
    }

    /**
     * Sets the app object name regex.
     *
     * @param regexAppObjectName
     *            the regexAppObjectName to set
     */
    public void setRegexAppObjectName(String regexAppObjectName) {
        this.regexAppObjectName = regexAppObjectName;
    }

    /**
     * Gets the app object attributes.
     *
     * @return the appObjectAttributes
     */
    public Set<String> getAppObjectAttributes() {
        return appObjectAttributes;
    }

    /**
     * Sets the app object attributes.
     *
     * @param appObjectAttributes
     *            the appObjectAttributes to set
     */
    public void setAppObjectAttributes(Set<String> appObjectAttributes) {
        this.appObjectAttributes = appObjectAttributes;
    }

    /**
     * Checks if is app object attribute exclude.
     *
     * @return the appObjectAttributeExclude
     */
    public boolean isAppObjectAttributeExclude() {
        return appObjectAttributeExclude;
    }

    /**
     * Sets the app object attribute exclude.
     *
     * @param appObjectAttributeExclude
     *            the appObjectAttributeExclude to set
     */
    public void setAppObjectAttributeExclude(boolean appObjectAttributeExclude) {
        this.appObjectAttributeExclude = appObjectAttributeExclude;
    }

    /**
     * Gets the app object detail.
     *
     * @return the appObjectDetail
     */
    public String getAppObjectDetail() {
        return appObjectDetail;
    }

    /**
     * Sets the app object detail.
     *
     * @param appObjectDetail
     *            the appObjectDetail to set
     */
    public void setAppObjectDetail(String appObjectDetail) {
        this.appObjectDetail = appObjectDetail;
    }

    /**
     * Gets the app object detail attributes.
     *
     * @return the appObjectDetailAttributes
     */
    public Set<String> getAppObjectDetailAttributes() {
        return appObjectDetailAttributes;
    }

    /**
     * Sets the app object detail attributes.
     *
     * @param appObjectDetailAttributes
     *            the appObjectDetailAttributes to set
     */
    public void setAppObjectDetailAttributes(
            Set<String> appObjectDetailAttributes) {
        this.appObjectDetailAttributes = appObjectDetailAttributes;
    }

    /**
     * Checks if is app object detail attribute exclude.
     *
     * @return the appObjectDetailAttributeExclude
     */
    public boolean isAppObjectDetailAttributeExclude() {
        return appObjectDetailAttributeExclude;
    }

    /**
     * Sets the app object detail attribute exclude.
     *
     * @param appObjectDetailAttributeExclude
     *            the appObjectDetailAttributeExclude to set
     */
    public void setAppObjectDetailAttributeExclude(
            boolean appObjectDetailAttributeExclude) {
        this.appObjectDetailAttributeExclude = appObjectDetailAttributeExclude;
    }

    /**
     * @see com.redhat.it.customers.dmc.core.dto.configuration.Configuration#toString()
     */
    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("AppConfiguration [apps=");
        builder.append(apps);
        builder.append(", regexpSubdeployment=");
        builder.append(regexpSubdeployment);
        builder.append(", subsystem=");
        builder.append(subsystem);
        builder.append(", regexAppObjectName=");
        builder.append(regexAppObjectName);
        builder.append(", appObjectAttributes=");
        builder.append(appObjectAttributes);
        builder.append(", appObjectAttributeExclude=");
        builder.append(appObjectAttributeExclude);
        builder.append(", appObjectDetail=");
        builder.append(appObjectDetail);
        builder.append(", appObjectDetailAttributes=");
        builder.append(appObjectDetailAttributes);
        builder.append(", appObjectDetailAttributeExclude=");
        builder.append(appObjectDetailAttributeExclude);
        builder.append("]");
        return builder.toString();
    }

}
