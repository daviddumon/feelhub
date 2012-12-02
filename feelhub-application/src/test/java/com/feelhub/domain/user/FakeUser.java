package com.feelhub.domain.user;

public class FakeUser extends User {

    public FakeUser() {
    }

    @Override
    public void setPassword(final String password) {
        this.password = password;
    }

    @Override
    public boolean checkPassword(final String password) {
        return password.equals(getPassword());
    }
}
