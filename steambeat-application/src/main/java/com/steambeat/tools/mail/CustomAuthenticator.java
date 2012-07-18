package com.steambeat.tools.mail;

import javax.mail.*;

public class CustomAuthenticator extends Authenticator {

    @Override
    protected PasswordAuthentication getPasswordAuthentication() {
        final String username = "register@steambeat.com";
        final String password = "c8wxYUKeFwSY";
        return new PasswordAuthentication(username, password);
    }
}
