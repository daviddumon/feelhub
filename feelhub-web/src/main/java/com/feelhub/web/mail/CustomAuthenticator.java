package com.feelhub.web.mail;

import javax.mail.*;

public class CustomAuthenticator extends Authenticator {

    @Override
    protected PasswordAuthentication getPasswordAuthentication() {
        final String username = "register@feelhub.com";
        final String password = "c8wxYUKeFwSY";
        return new PasswordAuthentication(username, password);
    }
}
