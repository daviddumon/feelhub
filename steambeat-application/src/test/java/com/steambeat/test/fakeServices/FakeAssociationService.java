package com.steambeat.test.fakeServices;

import com.steambeat.application.AssociationService;
import com.steambeat.domain.analytics.Association;
import com.steambeat.domain.analytics.identifiers.uri.Uri;
import com.steambeat.test.testFactories.TestFactories;

public class FakeAssociationService extends AssociationService {
//todo : delete this class
    public FakeAssociationService() {
        super(null);
    }

    @Override
    public Association lookUp(final Uri uri) {
        return TestFactories.associations().newAssociation(uri);
    }
}
