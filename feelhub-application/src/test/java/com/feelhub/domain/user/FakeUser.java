package com.feelhub.domain.user;

import java.util.UUID;

public class FakeUser extends User {

	public FakeUser() {
		super(UUID.randomUUID().toString());
	}

	@Override
    public void setPassword(final String password) {
        this.password = password;
    }
}
