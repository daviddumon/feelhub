package com.feelhub.repositories.fakeRepositories;

import com.feelhub.domain.user.*;

import java.util.UUID;

public class FakeUserRepository extends FakeRepository<User> implements UserRepository {

    @Override
    public User get(final String id) {
        return get(UUID.fromString(id));
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

    @Override
    public User findBySocialNetwork(final SocialNetwork socialNetwork, final String id) {
        for (final User user : getAll()) {
            if (user.getSocialAuth(socialNetwork) != null) {
                final SocialAuth auth = user.getSocialAuth(socialNetwork);
                if (auth.getId().equals(id)) {
                    return user;
                }
            }
        }
        return null;
    }
}
