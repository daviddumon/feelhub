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
    public void canLookUpSteam() {
        final Keyword steam = new Keyword("", FeelhubLanguage.none(), UUID.randomUUID());
        fakeKeywordRepository.add(steam);

        final Keyword foundSteam = fakeKeywordRepository.forValueAndLanguage("", FeelhubLanguage.none());

        assertThat(foundSteam, is(steam));
    }

    private FakeKeywordRepository fakeKeywordRepository;
}
