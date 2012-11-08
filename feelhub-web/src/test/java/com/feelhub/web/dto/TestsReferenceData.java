package com.feelhub.web.dto;

import com.feelhub.domain.feeling.SentimentValue;
import com.feelhub.domain.illustration.Illustration;
import com.feelhub.domain.keyword.Keyword;
import com.feelhub.domain.reference.Reference;
import com.feelhub.domain.thesaurus.FeelhubLanguage;
import com.feelhub.repositories.fakeRepositories.WithFakeRepositories;
import com.feelhub.test.TestFactories;
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
    public void referenceDataHasALanguageCode() {
        final FeelhubLanguage language = FeelhubLanguage.reference();

        final ReferenceData referenceData = new ReferenceData.Builder().language(language).build();

        assertThat(referenceData.getLanguageCode(), is(language.getCode()));
    }

    @Test
    public void languageCodeHasADefaultValue() {
        final ReferenceData referenceData = new ReferenceData.Builder().build();

        assertThat(referenceData.getLanguageCode(), is(FeelhubLanguage.none().getCode()));
    }

    @Test
    public void referenceDataHasASentimentValue() {
        final ReferenceData referenceData = new ReferenceData.Builder().sentimentValue(SentimentValue.good).build();

        assertThat(referenceData.getSentimentValue(), is(SentimentValue.good));
    }

    @Test
    public void sentimentHadADefaultValue() {
        final ReferenceData referenceData = new ReferenceData.Builder().build();

        assertThat(referenceData.getSentimentValue(), is(SentimentValue.none));
    }
}
