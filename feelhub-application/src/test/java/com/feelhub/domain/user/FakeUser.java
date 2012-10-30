package com.feelhub.domain.user;

public class FakeUser extends User {

    @Override
    public void setPassword(final String password) {
        this.password = password;
    }
}
