package com.feelhub.web.authentification;

import com.feelhub.domain.thesaurus.FeelhubLanguage;
import com.feelhub.domain.user.User;
import org.apache.commons.codec.digest.DigestUtils;

public class WebUser {

    public static WebUser anonymous() {
        return new AnonymousUser();
    }

    public WebUser(final User user, final boolean authenticated) {
        this.authenticated = authenticated;
        this.user = user;
    }

    public boolean isAuthenticated() {
        return authenticated;
    }

    public String getFullname() {
        return user.getFullname();
    }

    public FeelhubLanguage getLanguage() {
        return user.getLanguage();
    }

    public User getUser() {
        return user;
    }

    public boolean isAnonymous() {
        return false;
    }

    public String getEmail() {
        return user.getEmail();
    }

    public boolean bookmarkletShow() {
        return user.getBookmarkletShow();
    }

    public String getHashedEmail() {
        return DigestUtils.md5Hex(user.getEmail());
    }

    private final boolean authenticated;

    private final User user;
}
