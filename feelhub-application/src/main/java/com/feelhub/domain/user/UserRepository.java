package com.feelhub.domain.user;

import com.feelhub.domain.Repository;
import com.google.common.base.Optional;

public interface UserRepository extends Repository<User> {

    public User get(String id);

    public Optional<User> forEmail(String email);

    public User findBySocialNetwork(SocialNetwork socialNetwork, String id);
}
