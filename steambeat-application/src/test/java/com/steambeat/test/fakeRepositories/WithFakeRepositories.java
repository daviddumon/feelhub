package com.steambeat.test.fakeRepositories;

import com.steambeat.repositories.Repositories;
import org.junit.rules.ExternalResource;

public class WithFakeRepositories extends ExternalResource {

    @Override
    protected void before() throws Throwable {
        Repositories.initialize(new FakeMongoRepositories());
    }

    @Override
    protected void after() {
        Repositories.initialize(null);
    }
}
