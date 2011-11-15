package com.steambeat.application;

import com.google.inject.Inject;
import com.steambeat.domain.subject.webpage.*;
import com.steambeat.repositories.Repositories;

public class AssociationService {

    @Inject
    public AssociationService(final CanonicalUriFinder finder) {
        this.finder = finder;
    }

    public Association lookUp(final String address) {
        final Uri uri = new Uri(address);
        Association association = Repositories.associations().get(uri.toString());
        if (association != null) {
            association.update(finder);
            return association;
        }
        association = new Association(uri, finder);
        Repositories.associations().add(association);
        return association;
    }

    private final CanonicalUriFinder finder;
}
