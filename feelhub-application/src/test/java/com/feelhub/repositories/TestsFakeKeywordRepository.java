package com.feelhub.repositories;

import com.feelhub.domain.keyword.Keyword;
import com.feelhub.domain.thesaurus.FeelhubLanguage;
import com.feelhub.repositories.fakeRepositories.FakeKeywordRepository;
import org.junit.*;

import java.util.UUID;

import static org.hamcrest.Matchers.*;
import static org.junit.Assert.*;

public class TestsFakeKeywordRepository {

    @Before
    public void before() {
        fakeKeywordRepository = new FakeKeywordRepository();
    }

    @Test
    public void canLookUpWorld() {
        final Keyword world = new Keyword("", FeelhubLanguage.none(), UUID.randomUUID());
        fakeKeywordRepository.add(world);

        final Keyword foundWorld = fakeKeywordRepository.forValueAndLanguage("", FeelhubLanguage.none());

        assertThat(foundWorld, is(world));
    }

    private FakeKeywordRepository fakeKeywordRepository;
}
