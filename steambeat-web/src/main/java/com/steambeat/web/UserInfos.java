package com.steambeat.web;

import com.steambeat.domain.user.User;
import org.restlet.Request;

public class UserInfos  {

	public User getUser() {
		if(Request.getCurrent().getAttributes().containsKey("com.steambeat.user")) {
			return (User) Request.getCurrent().getAttributes().get("com.steambeat.user");
		}
		return null;
	}

	public boolean isAuthenticated() {
		if(Request.getCurrent().getAttributes().containsKey("com.steambeat.authentificated")) {
			return (Boolean) Request.getCurrent().getAttributes().get("com.steambeat.authentificated");
		}
		return false;
	}
}
