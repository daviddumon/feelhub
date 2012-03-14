package com.steambeat.test.fakeRepositories;

import com.google.common.base.Predicate;
import com.google.common.collect.Iterables;
import com.steambeat.domain.subject.*;
import com.steambeat.domain.subject.steam.Steam;

public class FakeSubjectMongoRepository extends FakeRepository<Subject> implements SubjectRepository {

    @Override
    public Steam getSteam() {
        return (Steam) Iterables.find(getAll(), new Predicate<Subject>() {

            @Override
            public boolean apply(final Subject subject) {
                return subject instanceof Steam;
            }
        });
    }
}
