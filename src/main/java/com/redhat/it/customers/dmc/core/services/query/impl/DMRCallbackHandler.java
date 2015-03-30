/**
 * 
 */
package com.redhat.it.customers.dmc.core.services.query.impl;

import java.io.IOException;

import javax.security.auth.callback.Callback;
import javax.security.auth.callback.CallbackHandler;
import javax.security.auth.callback.NameCallback;
import javax.security.auth.callback.PasswordCallback;
import javax.security.auth.callback.UnsupportedCallbackException;
import javax.security.sasl.RealmCallback;

/**
 * @author Andrea Battaglia
 *
 */
public class DMRCallbackHandler implements CallbackHandler {

    private String username;
    private String password;
    private String realm;

    /**
     * Instantiates a new DMR callback handler.
     *
     * @param username
     *            the username
     * @param password
     *            the password
     * @param realm
     *            the realm
     */
    public DMRCallbackHandler(String username, String password, String realm) {
        super();
        this.username = username;
        this.password = password;
        this.realm = realm;
    }

    /**
     * @see javax.security.auth.callback.CallbackHandler#handle(javax.security.auth.callback.Callback[])
     */
    @Override
    public void handle(Callback[] callbacks) throws IOException,
            UnsupportedCallbackException {
        for (Callback current : callbacks) {
            if (current instanceof NameCallback) {
                NameCallback ncb = (NameCallback) current;
                ncb.setName(username);
            } else if (current instanceof PasswordCallback) {
                PasswordCallback pcb = (PasswordCallback) current;
                pcb.setPassword(password.toCharArray());
            } else if (current instanceof RealmCallback) {
                RealmCallback rcb = (RealmCallback) current;
                rcb.setText(realm == null ? rcb.getDefaultText() : realm);
            } else {
                throw new UnsupportedCallbackException(current);
            }
        }
    }

}
