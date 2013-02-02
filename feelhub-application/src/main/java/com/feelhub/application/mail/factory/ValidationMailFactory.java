package com.feelhub.application.mail.factory;

import com.feelhub.application.mail.FeelhubMail;
import com.feelhub.application.mail.SimpleTemplate;
import com.feelhub.domain.user.User;
import com.google.common.collect.Maps;

import java.util.Map;

public class ValidationMailFactory {
    public FeelhubMail build(User user, String activationUri) {
        return new FeelhubMail(user.getEmail(), "Welcome to Feelhub !", content(user, activationUri));
    }

    private String content(User user, String activationUri) {
        return new SimpleTemplate(ResourceUtils.resource("mail/activation.ftl")).apply(validationData(user, activationUri));
    }

    private Map<String, Object> validationData(User user, String activationUri) {
        Map<String,Object> result = Maps.newHashMap();
        result.put("username", user.getFullname());
        result.put("activation_link",activationUri);
        return result;
    }

}
