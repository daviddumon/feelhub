package com.steambeat.repositories.fakeRepositories;

import com.google.common.base.*;
import com.google.common.collect.Iterables;
import com.steambeat.domain.user.*;

import java.util.UUID;

public class FakeUserRepository extends FakeRepository<User> implements UserRepository {

    @Override
    public User forSecret(final UUID secret) {
        try {
            return Iterables.find(getAll(), new Predicate<User>() {

                @Override
                public boolean apply(@Nullable final User input) {
                    if (input.getSecret().equals(secret.toString())) {
                        return true;
                    } else {
                        return false;
                    }
                }
            });
        } catch (Exception e) {
            return null;
        }
    }
}
