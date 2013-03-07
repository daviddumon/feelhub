package com.feelhub.repositories.fakeRepositories;

import com.feelhub.repositories.Repositories;
import org.junit.rules.ExternalResource;

public class WithFakeRepositories extends ExternalResource {

    @Override
    protected void before() throws Throwable {
        Repositories.initialize(new FakeRepositories());
    }

    @Override
    protected void after() {
        Repositories.initialize(null);
    }
}
