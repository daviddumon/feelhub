package com.feelhub.domain.user;

import com.feelhub.domain.Repository;

public interface UserRepository extends Repository<User> {

    public User forEmail(String email);
}
