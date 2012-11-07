package com.feelhub.repositories.fakeRepositories;

import com.feelhub.domain.user.*;
import com.google.common.base.*;
import com.google.common.collect.Iterables;

import java.util.UUID;

public class FakeUserRepository extends FakeRepository<User> implements UserRepository {

    @Override
    public User forSecret(final UUID secret) {
        try {
            return Iterables.find(getAll(), new Predicate<User>() {

                @Override
                public boolean apply(@Nullable final User input) {
                    return input.getSecret().equals(secret.toString());
                }
            });
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public User forEmail(final String email) {
        for (final User user : getAll()) {
            if (email.equals(user.getEmail())) {
                return user;
            }
        }
        return null;
    }
}
