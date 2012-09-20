package com.steambeat.repositories.fakeRepositories;

import com.google.common.base.Predicate;
import com.google.common.collect.*;
import com.steambeat.domain.session.*;
import com.steambeat.domain.user.User;

import java.util.List;

public class FakeSessionRepository extends FakeRepository<Session> implements SessionRepository {

    @Override
    public List<Session> forUser(final User user) {
        return Lists.newArrayList(Iterables.filter(getAll(), new Predicate<Session>() {

            @Override
            public boolean apply(final Session input) {
                return input.getEmail().equalsIgnoreCase(user.getEmail());
            }
        }));
    }
}
