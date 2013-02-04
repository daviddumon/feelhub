package com.feelhub.application.mail.factory;

import com.feelhub.application.mail.*;
import com.feelhub.domain.user.User;
import com.google.common.collect.Maps;

import java.util.Map;

public class WelcomeMailFactory {
    public FeelhubMail build(User user) {
        return new FeelhubMail(user.getEmail(), "Welcome to Feelhub !", textContent(), htmlContent());
    }

    private String textContent() {
        return new MailTemplate(ResourceUtils.resource("mail/welcome-text.ftl"), MailTemplate.Format.TEXT).apply(data());
    }

    private String htmlContent() {
        return new MailTemplate(ResourceUtils.resource("mail/welcome-html.ftl"), MailTemplate.Format.HTML).apply(data());
    }

    private Map<String, Object> data() {
        return Maps.newHashMap();
    }
}
