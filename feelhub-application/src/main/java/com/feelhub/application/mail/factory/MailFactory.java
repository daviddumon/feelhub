package com.feelhub.application.mail.factory;

import com.feelhub.application.mail.FeelhubMail;
import com.feelhub.domain.user.User;

public final class MailFactory {

    private MailFactory() {

    }

    public static FeelhubMail validation(final User user, final String activationUri) {
        return new ValidationMailFactory().build(user, activationUri);
    }

    public static FeelhubMail welcome(final User user) {
        return new WelcomeMailFactory().build(user);
    }
}
