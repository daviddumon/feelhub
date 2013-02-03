package com.feelhub.application.mail.factory;

import com.feelhub.application.mail.FeelhubMail;
import com.feelhub.application.mail.MailTemplate;
import com.feelhub.domain.user.User;
import com.google.common.collect.Maps;

import java.util.Map;

public class ValidationMailFactory {
    public FeelhubMail build(User user, String activationUri) {
        return new FeelhubMail(user.getEmail(), "Welcome to Feelhub !", textContent(user, activationUri), htmlContent(user, activationUri));
    }

    private String textContent(User user, String activationUri) {
        return new MailTemplate(ResourceUtils.resource("mail/activation-text.ftl"), MailTemplate.Format.TEXT).apply(validationData(user, activationUri));
    }

    private String htmlContent(User user, String activationUri) {
        return new MailTemplate(ResourceUtils.resource("mail/activation-html.ftl"), MailTemplate.Format.HTML).apply(validationData(user, activationUri));
    }


    private Map<String, Object> validationData(User user, String activationUri) {
        Map<String, Object> result = Maps.newHashMap();
        result.put("username", user.getFullname());
        result.put("activation_link", activationUri);
        return result;
    }

}
