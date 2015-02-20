/**
 * 
 */
package com.redhat.it.customers.dmc.core.dto.collector;

import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;

import javax.enterprise.context.RequestScoped;

import com.redhat.it.customers.dmc.core.constants.CollectorWorkerStatusAction;

/**
 * The Class CollectorActionResult.
 *
 * @author Andrea Battaglia
 */
@RequestScoped
public class CollectorActionResult {

    /** The action. */
    private CollectorWorkerStatusAction action;

    /** The ok. */
    private final Set<String> success;

    /** The ko. */
    private final Map<String, String> fail;

    public CollectorActionResult() {
        success=new LinkedHashSet<String>();
        fail=new LinkedHashMap<String,String>();
    }

    /**
     * Gets the action.
     *
     * @return the action
     */
    public CollectorWorkerStatusAction getAction() {
        return action;
    }

    /**
     * Sets the action.
     *
     * @param action
     *            the new action
     */
    public void setAction(CollectorWorkerStatusAction action) {
        this.action = action;
    }

    /**
     * Gets the success.
     *
     * @return the success
     */
    public Set<String> getSuccess() {
        return success;
    }

    /**
     * Gets the fail.
     *
     * @return the fail
     */
    public Map<String, String> getFail() {
        return fail;
    }

    /**
     * Adds the success.
     *
     * @param e
     *            the e
     * @return true, if successful
     * @see java.util.Set#add(java.lang.Object)
     */
    public boolean addSuccess(String e) {
        return success.add(e);
    }

    /**
     * Put fail.
     *
     * @param key
     *            the key
     * @param value
     *            the value
     * @return the string
     */
    public String putFail(String key, String value) {
        return fail.put(key, value);
    }

}
