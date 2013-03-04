package com.feelhub.repositories.fakeRepositories;

import com.feelhub.domain.user.*;
import com.google.common.base.Optional;

import java.util.UUID;

public class FakeUserRepository extends FakeRepository<User> implements UserRepository {

    @Override
    public User get(final String id) {
        return get(UUID.fromString(id));
    }

    @Override
    public Optional<User> forEmail(final String email) {
        for (final User user : getAll()) {
            if (email.equals(user.getEmail())) {
                return Optional.of(user);
            }
        }
        return Optional.absent();
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
