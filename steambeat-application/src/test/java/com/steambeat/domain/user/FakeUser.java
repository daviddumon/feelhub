package com.steambeat.domain.user;

public class FakeUser extends User {

    @Override
    public void setPassword(final String password) {
        this.password = password;
    }
}
