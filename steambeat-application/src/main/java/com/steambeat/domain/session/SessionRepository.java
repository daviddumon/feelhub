package com.steambeat.domain.session;

import com.steambeat.domain.Repository;
import com.steambeat.domain.user.User;

import java.util.List;

public interface SessionRepository extends Repository<Session> {

    List<Session> forUser(final User user);
}
