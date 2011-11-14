package com.steambeat.test.testFactories;

import com.steambeat.repositories.Repositories;
import com.steambeat.domain.subject.feed.*;

public class AssociationFactoryForTest {

    public Association newAssociation(final String address, final String canonical) {
        return new Association(new Uri(address), TestFactories.canonicalUriFinder().thatFind(new Uri(canonical)));
    }

    public Association newAssociation(final String uri) {
        final Association association = new Association(new Uri(uri), TestFactories.canonicalUriFinder());
        Repositories.associations().add(association);
        return association;
    }
}
