package com.feelhub.repositories.fakeRepositories;

import com.feelhub.domain.session.*;
import com.feelhub.domain.user.User;
import com.google.common.base.Predicate;
import com.google.common.collect.*;

import java.util.List;

public class FakeSessionRepository extends FakeRepository<Session> implements SessionRepository {

    @Override
    public List<Session> forUser(final User user) {
        return Lists.newArrayList(Iterables.filter(getAll(), new Predicate<Session>() {

            @Override
            public boolean apply(final Session input) {
                return input.getUserId().equalsIgnoreCase(user.getEmail());
            }
        }));
    }
}
