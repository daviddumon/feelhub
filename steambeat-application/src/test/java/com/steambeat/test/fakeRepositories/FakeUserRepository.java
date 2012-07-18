package com.steambeat.test.fakeRepositories;

import com.google.common.base.Predicate;
import com.google.common.collect.Iterables;
import com.steambeat.domain.user.*;

import javax.annotation.Nullable;
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
