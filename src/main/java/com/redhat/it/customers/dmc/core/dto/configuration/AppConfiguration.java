/**
 *
 */
package com.redhat.it.customers.dmc.core.dto.configuration;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;

import org.codehaus.jackson.annotate.JsonIgnore;

import com.redhat.it.customers.dmc.core.constants.Constants;
import com.redhat.it.customers.dmc.core.enums.MetricType;

/**
 * The Class Configuration.
 *
 * @author Andrea Battaglia
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "AppConfiguration", namespace = "dto.dmc.customers.it.redhat.com")
public class AppConfiguration extends AbstractDMRConfiguration {

    /** The regexpApps. */
    private String regexpApps;

    /** The regexp subdeployment. */
    private String regexpSubdeployment = Constants.REGEXP_CATCH_ALL.getValue();

    private String regexpSubsystemComponent = Constants.REGEXP_CATCH_ALL
            .getValue();

    /** The app object name regex. */
    private String regexpAppObjectName = Constants.REGEXP_CATCH_ALL.getValue();

    public AppConfiguration() {
        metricType = MetricType.APP;
    }

    /**
     * Gets the regexpApps.
     *
     * @return the regexpApps
     */
    public String getRegexpApps() {
        return regexpApps;
    }

    /**
     * Sets the regexpApps.
     *
     * @param regexpApps
     *            the regexpApps to set
     */
    public void setRegexpApps(String regexpApps) {
        this.regexpApps = regexpApps;
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
     * Gets the regex subsystem component.
     *
     * @return the regex subsystem component
     */
    public String getRegexpSubsystemComponent() {
        return regexpSubsystemComponent;
    }

    /**
     * Sets the regex subsystem component.
     *
     * @param regexpSubsystemComponent
     *            the new regex subsystem component
     */
    public void setRegexpSubsystemComponent(String regexpSubsystemComponent) {
        this.regexpSubsystemComponent = regexpSubsystemComponent;
    }

    /**
     * Gets the app object name regex.
     *
     * @return the regexpAppObjectName
     */
    public String getRegexpAppObjectName() {
        return regexpAppObjectName;
    }

    /**
     * Sets the app object name regex.
     *
     * @param regexpAppObjectName
     *            the regexpAppObjectName to set
     */
    public void setRegexpAppObjectName(String regexpAppObjectName) {
        this.regexpAppObjectName = regexpAppObjectName;
    }

    /**
     * @see java.lang.Object#toString()
     */
    @JsonIgnore
    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("AppConfiguration [regexpApps=");
        builder.append(regexpApps);
        builder.append(", regexpSubdeployment=");
        builder.append(regexpSubdeployment);
        builder.append(", regexpSubsystemComponent=");
        builder.append(regexpSubsystemComponent);
        builder.append(", regexpAppObjectName=");
        builder.append(regexpAppObjectName);
        builder.append(", id=");
        builder.append(id);
        builder.append(", metricType=");
        builder.append(metricType);
        builder.append(", hostname=");
        builder.append(hostname);
        builder.append(", port=");
        builder.append(port);
        builder.append(", username=");
        builder.append(username);
        builder.append(", password=");
        builder.append(password);
        builder.append(", realm=");
        builder.append(realm);
        builder.append(", regexpHostname=");
        builder.append(regexpHostname);
        builder.append(", regexpServer=");
        builder.append(regexpServer);
        builder.append(", scanInterval=");
        builder.append(scanInterval);
        builder.append(", start=");
        builder.append(start);
//        builder.append(", depth=");
//        builder.append(depth);
        builder.append("]");
        return builder.toString();
    }

}
