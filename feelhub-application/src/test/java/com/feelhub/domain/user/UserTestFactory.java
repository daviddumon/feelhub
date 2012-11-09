package com.feelhub.domain.user;

import com.feelhub.repositories.Repositories;

public class UserTestFactory {

    public User createFakeUser(final String email) {
        final FakeUser user = new FakeUser();
        user.setEmail(email);
        user.setPassword("password");
        user.setFullname("full name");
        Repositories.users().add(user);
        return user;
    }

    public User createFakeUser(final String email, final String fullName) {
        final FakeUser user = new FakeUser();
        user.setEmail(email);
        user.setPassword("password");
        user.setFullname(fullName);
        Repositories.users().add(user);
        return user;
    }

    public User createFakeActiveUser(final String email) {
        final FakeUser user = new FakeUser();
        user.setEmail(email);
        user.setPassword("password");
        user.setFullname("full name");
        user.activate();
        Repositories.users().add(user);
        return user;
    }

    public User createActiveUser(final String email) {
        final User user = new User(email);
        user.setEmail(email);
        user.setPassword("password");
        user.setFullname("full name");
        user.activate();
        Repositories.users().add(user);
        return user;
    }
}