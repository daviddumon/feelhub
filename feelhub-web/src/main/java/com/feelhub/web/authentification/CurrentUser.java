package com.feelhub.web.authentification;

public final class CurrentUser {

	private CurrentUser() {

	}

	public static WebUser get() {
		return currentUser.get();
	}

	public static void set(WebUser user) {
		currentUser.set(user);
	}

	private static final ThreadLocal<WebUser> currentUser = new ThreadLocal<WebUser>() {

	};
}
