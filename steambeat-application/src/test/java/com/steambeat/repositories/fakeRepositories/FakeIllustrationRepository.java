package com.steambeat.repositories.fakeRepositories;

import com.google.common.base.Predicate;
import com.google.common.collect.*;
import com.steambeat.domain.illustration.*;

import java.util.*;

public class FakeIllustrationRepository extends FakeRepository<Illustration> implements IllustrationRepository {

    @Override
    public List<Illustration> forReferenceId(final UUID referenceId) {
        if (referenceId == null) {
            throw new NullPointerException();
        }
        return Lists.newArrayList(Iterables.filter(getAll(), new Predicate<Illustration>() {

            @Override
            public boolean apply(final Illustration input) {
                return input.getReferenceId().equals(referenceId);
            }
        }));
    }
}
