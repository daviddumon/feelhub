package com.steambeat.repositories;

import com.steambeat.domain.keyword.Keyword;
import com.steambeat.domain.thesaurus.SteambeatLanguage;
import com.steambeat.repositories.fakeRepositories.FakeKeywordRepository;
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
        final Keyword steam = new Keyword("", SteambeatLanguage.none(), UUID.randomUUID());
        fakeKeywordRepository.add(steam);

        final Keyword foundSteam = fakeKeywordRepository.forValueAndLanguage("", SteambeatLanguage.none());

        assertThat(foundSteam, is(steam));
    }

    private FakeKeywordRepository fakeKeywordRepository;
}
