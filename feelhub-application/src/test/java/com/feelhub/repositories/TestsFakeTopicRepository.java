package com.feelhub.repositories;

import com.feelhub.repositories.fakeRepositories.FakeTopicRepository;
import org.junit.Before;

public class TestsFakeTopicRepository {

    @Before
    public void before() {
        final FakeTopicRepository fakeKeywordRepository = new FakeTopicRepository();
    }


}
