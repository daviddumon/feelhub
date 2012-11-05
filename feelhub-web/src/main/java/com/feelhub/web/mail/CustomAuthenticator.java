package com.feelhub.web.mail;

import javax.mail.*;

public class CustomAuthenticator extends Authenticator {

    @Override
    protected PasswordAuthentication getPasswordAuthentication() {
        final String username = "register@feelhub.com";
        final String password = "vDnOMpmAGSt0";
        return new PasswordAuthentication(username, password);
    }
}
