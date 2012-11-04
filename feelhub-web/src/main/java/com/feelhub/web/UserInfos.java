package com.feelhub.web;

import com.feelhub.web.authentification.CurrentUser;
import com.feelhub.web.authentification.WebUser;

public class UserInfos {

    public WebUser getUser() {
        return CurrentUser.get();
    }

	public boolean isAnonymous() {
		return CurrentUser.get().isAnonymous();
	}

	public boolean isAuthenticated() {
		return CurrentUser.get().isAuthenticated();
	}
}
