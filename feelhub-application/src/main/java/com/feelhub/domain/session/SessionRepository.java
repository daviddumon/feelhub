package com.feelhub.domain.session;

import com.feelhub.domain.Repository;
import com.feelhub.domain.user.User;

import java.util.List;

public interface SessionRepository extends Repository<Session> {

    List<Session> forUser(final User user);
}
