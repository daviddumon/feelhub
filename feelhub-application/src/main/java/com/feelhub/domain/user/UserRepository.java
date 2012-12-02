package com.feelhub.domain.user;

import com.feelhub.domain.Repository;

public interface UserRepository extends Repository<User> {

    public User get(String id);

    public User forEmail(String email);

    public User findBySocialNetwork(SocialNetwork socialNetwork, String id);
}
