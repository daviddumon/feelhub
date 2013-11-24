package com.feelhub.web.authentification;

import com.feelhub.domain.user.User;

public class UserInfos {

    public User getUser() {
        return currentUser().getUser();
    }

    public boolean isAnonymous() {
        return currentUser().isAnonymous();
    }

    public boolean isAuthenticated() {
        return currentUser().isAuthenticated();
    }

    public String getEmail() {
        return currentUser().getEmail();
    }

    public String getHashedEmail() {
        return currentUser().getHashedEmail();
    }

    public String getFullname() {
        return currentUser().getFullname();
    }

    public String getLanguageCode() {
        return currentUser().getLanguage().getCode();
    }

    private WebUser currentUser() {
        return CurrentUser.get();
    }
}
