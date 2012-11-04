package com.feelhub.web.authentification;

import com.feelhub.domain.user.User;

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

	public User getUser() {
		return user;
	}

	public boolean isAnonymous() {
		return false;
	}

	public String getEmail() {
		return user.getEmail();
	}

	public String getLanguageCode() {
		return user.getLanguageCode();
	}

	private final boolean authenticated;
	private User user;
}
