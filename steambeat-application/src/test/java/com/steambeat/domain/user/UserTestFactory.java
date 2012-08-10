package com.steambeat.domain.user;

import com.steambeat.repositories.Repositories;

public class UserTestFactory {

    public User createUser(final String email) {
        final User user = new User();
        user.setEmail(email);
        user.setPassword("password");
        user.setFullname("full name");
        Repositories.users().add(user);
        return user;
    }

    public User createUser(final String email, final String fullName) {
        final User user = new User();
        user.setEmail(email);
        user.setPassword("password");
        user.setFullname(fullName);
        Repositories.users().add(user);
        return user;
    }

    public User createActiveUser(final String email) {
        final User user = new User();
        user.setEmail(email);
        user.setPassword("password");
        user.setFullname("full name");
        user.activate();
        Repositories.users().add(user);
        return user;
    }

    public User createActiveUser(final String email, final String fullName) {
        final User user = new User();
        user.setEmail(email);
        user.setPassword("password");
        user.setFullname(fullName);
        user.activate();
        Repositories.users().add(user);
        return user;
    }
}
