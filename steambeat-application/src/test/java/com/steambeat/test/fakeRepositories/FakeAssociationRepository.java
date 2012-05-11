package com.steambeat.test.fakeRepositories;

import com.steambeat.domain.association.*;

public class FakeAssociationRepository extends FakeRepository<Association> implements AssociationRepository{

    @Override
    public Association forIdentifier(final Identifier identifier) {
        return null;
    }
}
