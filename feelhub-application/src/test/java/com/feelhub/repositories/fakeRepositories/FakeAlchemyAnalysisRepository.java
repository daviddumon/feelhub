package com.feelhub.repositories.fakeRepositories;

import com.feelhub.domain.alchemy.*;
import com.google.common.base.Predicate;
import com.google.common.collect.*;

import java.util.*;

public class FakeAlchemyAnalysisRepository extends FakeRepository<AlchemyAnalysis> implements AlchemyAnalysisRepository {

    @Override
    public List<AlchemyAnalysis> forReferenceId(final UUID referenceId) {
        return Lists.newArrayList(Iterables.filter(getAll(), new Predicate<AlchemyAnalysis>() {

            @Override
            public boolean apply(final AlchemyAnalysis input) {
                return input.getReferenceId().equals(referenceId);
            }
        }));
    }
}
