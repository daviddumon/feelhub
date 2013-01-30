package com.feelhub.web.mail;

import javax.mail.*;

public class CustomAuthenticator extends Authenticator {

    @Override
    protected PasswordAuthentication getPasswordAuthentication() {
        final String username = "feelhub";
        final String password = "dn6BYsSdGSYB";
        return new PasswordAuthentication(username, password);
    }
}
