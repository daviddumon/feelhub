package com.feelhub.application.mail.factory;

import com.feelhub.application.mail.*;
import com.feelhub.domain.user.User;
import com.google.common.collect.Maps;

import java.util.Map;

public class ValidationMailFactory {
    public FeelhubMail build(final User user, final String activationUri) {
        return new FeelhubMail(user.getEmail(), "Welcome to Feelhub !", textContent(user, activationUri), htmlContent(user, activationUri));
    }

    private String textContent(final User user, final String activationUri) {
        return new MailTemplate(ResourceUtils.resource("mail/activation-text.ftl"), MailTemplate.Format.TEXT).apply(validationData(user, activationUri));
    }

    private String htmlContent(final User user, final String activationUri) {
        return new MailTemplate(ResourceUtils.resource("mail/activation-html.ftl"), MailTemplate.Format.HTML).apply(validationData(user, activationUri));
    }


    private Map<String, Object> validationData(final User user, final String activationUri) {
        final Map<String, Object> result = Maps.newHashMap();
        result.put("username", user.getFullname());
        result.put("activation_link", activationUri);
        return result;
    }

}
