package com.feelhub.repositories.fakeRepositories;

import com.feelhub.domain.user.*;

public class FakeUserRepository extends FakeRepository<User> implements UserRepository {

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
