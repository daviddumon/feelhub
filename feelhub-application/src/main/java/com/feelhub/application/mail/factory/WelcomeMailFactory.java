package com.feelhub.application.mail.factory;

import com.feelhub.application.mail.FeelhubMail;
import com.feelhub.application.mail.SimpleTemplate;
import com.feelhub.domain.user.User;
import com.google.common.collect.Maps;

import java.util.Map;

public class WelcomeMailFactory {
    public FeelhubMail build(User user) {
        return new FeelhubMail(user.getEmail(), "Welcome to Feelhub !", content());
    }

    private String content() {
        return new SimpleTemplate(ResourceUtils.resource("mail/welcome.ftl")).apply(data());
    }

    private Map<String, Object> data() {
        return Maps.newHashMap();
    }
}
