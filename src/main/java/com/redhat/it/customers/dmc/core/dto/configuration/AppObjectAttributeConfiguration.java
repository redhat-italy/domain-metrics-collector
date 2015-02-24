/**
 * 
 */
package com.redhat.it.customers.dmc.core.dto.configuration;

import java.util.Set;

/**
 * @author Andrea Battaglia
 *
 */
public class AppObjectAttributeConfiguration {


    /** The app object attributes. */
    private Set<String> appObjectAttributes;

    /** The app object attribute exclude. */
    private boolean appObjectAttributeExclude;
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
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("AppObjectAttributeConfiguration [appObjectAttributes=");
        builder.append(appObjectAttributes);
        builder.append(", appObjectAttributeExclude=");
        builder.append(appObjectAttributeExclude);
        builder.append("]");
        return builder.toString();
    }
    
    
}
