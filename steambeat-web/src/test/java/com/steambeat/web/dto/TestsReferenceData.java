package com.steambeat.web.dto;

import com.steambeat.domain.illustration.Illustration;
import com.steambeat.domain.keyword.Keyword;
import com.steambeat.domain.reference.Reference;
import com.steambeat.domain.thesaurus.SteambeatLanguage;
import com.steambeat.repositories.fakeRepositories.WithFakeRepositories;
import com.steambeat.test.TestFactories;
import org.junit.*;

import static org.hamcrest.Matchers.*;
import static org.junit.Assert.*;

public class TestsReferenceData {

    @Rule
    public WithFakeRepositories repositories = new WithFakeRepositories();

    @Test
    public void referenceDataHasAReferenceId() {
        final Reference reference = TestFactories.references().newReference();

        final ReferenceData referenceData = new ReferenceData.Builder().referenceId(reference.getId()).build();

        assertThat(referenceData.getReferenceId(), is(reference.getId().toString()));
    }

    @Test
    public void referenceIdDefaultValueIsEmpty() {
        final ReferenceData referenceData = new ReferenceData.Builder().build();

        assertThat(referenceData.getReferenceId(), is(""));
    }

    @Test
    public void referenceDataHasAnIllustration() {
        final Illustration illustration = TestFactories.illustrations().newIllustration(TestFactories.references().newReference(), "mylink");

        final ReferenceData referenceData = new ReferenceData.Builder().illustration(illustration).build();

        assertThat(referenceData.getIllustrationLink(), is(illustration.getLink()));
    }

    @Test
    public void illustrationLinkDefaultValueIsEmpty() {
        final ReferenceData referenceData = new ReferenceData.Builder().build();

        assertThat(referenceData.getIllustrationLink(), is(""));
    }

    @Test
    public void referenceDataHasAKeyword() {
        final Keyword keyword = TestFactories.keywords().newKeyword("keyword");

        final ReferenceData referenceData = new ReferenceData.Builder().keyword(keyword).build();

        assertThat(referenceData.getKeywordValue(), is(keyword.getValue()));
    }

    @Test
    public void keywordValueDefaultValueIsEmpty() {
        final ReferenceData referenceData = new ReferenceData.Builder().build();

        assertThat(referenceData.getKeywordValue(), is(""));
    }

    @Test
    public void referenceDataHasALanguage() {
        final SteambeatLanguage language = SteambeatLanguage.reference();

        final ReferenceData referenceData = new ReferenceData.Builder().language(language).build();

        assertThat(referenceData.getLanguageCode(), is(language.getCode()));
    }

    @Test
    public void languageCodeHasADefaultValue() {
        final ReferenceData referenceData = new ReferenceData.Builder().build();

        assertThat(referenceData.getLanguageCode(), is(SteambeatLanguage.none().getCode()));
    }
}
