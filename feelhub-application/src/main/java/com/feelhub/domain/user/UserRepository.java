package com.feelhub.domain.user;

import com.feelhub.domain.Repository;

import java.util.UUID;

public interface UserRepository extends Repository<User> {

    public User forSecret(final UUID secret);

	public User forEmail(String email);
}
