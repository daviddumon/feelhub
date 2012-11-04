package com.feelhub.web.authentification;

public class AnonymousUser extends WebUser {

	public AnonymousUser() {
		super(null, false);
	}

	@Override
	public String getFullname() {
		return "anonymous";
	}

	@Override
	public boolean isAnonymous() {
		return true;
	}
}
