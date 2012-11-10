package com.feelhub.web.authentification;

import com.feelhub.domain.user.User;

public class UserInfos {

    public User getUser() {
        return CurrentUser.get().getUser();
    }

    public boolean isAnonymous() {
        return CurrentUser.get().isAnonymous();
    }

    public boolean isAuthenticated() {
        return CurrentUser.get().isAuthenticated();
    }

    public String getEmail() {
        return CurrentUser.get().getEmail();
    }

    public String getFullname() {
        return CurrentUser.get().getFullname();
    }
}
