package com.feelhub.web.authentification;

import com.feelhub.web.resources.authentification.AuthMethod;

public class AuthRequest {
	public static AuthRequest facebook(final String email) {
		final AuthRequest result = new AuthRequest();
		result.authMethod = AuthMethod.FACEBOOK;
		result.email = email;
		return result;
	}

	private AuthRequest() {

	}

	public AuthRequest(final String email, final String password, final boolean remember) {
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

	public AuthMethod getAuthMethod() {
		return authMethod;
	}

	public boolean isRemember() {
		return remember;
	}

	private AuthMethod authMethod = AuthMethod.FEELHUB;

	private String password;
	private String email;
	private boolean remember;
}
