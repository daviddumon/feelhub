package com.feelhub.web.authentification;

public class UserDetails {
	private final String email;
	private final String password;
	private final boolean remember;

	public UserDetails(final String email, final String password, final boolean remember) {
		this.email = email;
		this.password = password;
		this.remember = remember;
	}

	public String getEmail() {
		return email;
	}

	public String getPassword() {
		return password;
	}

	public boolean isRemember() {
		return remember;
	}
}
