package com.steambeat.domain.user;

import com.steambeat.domain.Repository;

import java.util.UUID;

public interface UserRepository extends Repository<User> {

    public User forSecret(final UUID secret);
}
