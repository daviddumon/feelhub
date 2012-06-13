package com.steambeat.test.testFactories;

import com.steambeat.domain.user.User;
import com.steambeat.repositories.Repositories;

public class UserFactoryForTest {

    public User createUser(final String email) {
        final User user = new User();
        user.setEmail(email);
        user.setPassword("password");
        Repositories.users().add(user);
        return user;
    }
}
