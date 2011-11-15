package com.steambeat.test.fakeServices;

import com.steambeat.application.AssociationService;
import com.steambeat.domain.subject.webpage.*;
import com.steambeat.test.testFactories.TestFactories;

public class FakeAssociationService extends AssociationService {

    public FakeAssociationService() {
        super(null);
    }

    @Override
    public Association lookUp(final String address) {
        if (address.startsWith("http://404url")) {
            throw new WebPageException();
        }
        if (address.startsWith("http://slate.fr")) {
            return TestFactories.associations().newAssociation(address, address.replace("http://slate.fr", "http://www.slate.fr"));
        }
        if (address.startsWith("lemonde.fr")) {
            return TestFactories.associations().newAssociation(address, "http://www.lemonde.fr");
        }
        if (address.startsWith("http://lemonde.fr")) {
            return TestFactories.associations().newAssociation(address, "http://www.lemonde.fr");
        }
        return TestFactories.associations().newAssociation(address);
    }

}
