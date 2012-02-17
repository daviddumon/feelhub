package com.steambeat.test.testFactories;

import com.steambeat.domain.analytics.Association;
import com.steambeat.domain.analytics.identifiers.uri.Uri;
import com.steambeat.repositories.Repositories;

import java.util.UUID;

public class AssociationFactoryForTest {

    public Association newAssociation(final Uri uri) {
        final Association association = new Association(uri, UUID.randomUUID());
        Repositories.associations().add(association);
        return association;
    }
}
