package com.feelhub.web;

import com.feelhub.domain.user.User;
import org.restlet.Request;

public class UserInfos {

    public User getUser() {
        if (Request.getCurrent().getAttributes().containsKey("com.feelhub.user")) {
            return (User) Request.getCurrent().getAttributes().get("com.feelhub.user");
        }
        return null;
    }

    public boolean isAuthenticated() {
        if (Request.getCurrent().getAttributes().containsKey("com.feelhub.authentificated")) {
            return (Boolean) Request.getCurrent().getAttributes().get("com.feelhub.authentificated");
        }
        return false;
    }
}
