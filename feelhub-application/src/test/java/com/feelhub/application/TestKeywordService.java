package com.feelhub.application;

import com.feelhub.domain.alchemy.AlchemyRequestEvent;
import com.feelhub.domain.eventbus.WithDomainEvent;
import com.feelhub.domain.illustration.*;
import com.feelhub.domain.keyword.*;
import com.feelhub.domain.reference.*;
import com.feelhub.domain.thesaurus.FeelhubLanguage;
import com.feelhub.domain.translation.FakeTranslator;
import com.feelhub.domain.uri.*;
import com.feelhub.repositories.Repositories;
import com.feelhub.repositories.fakeRepositories.WithFakeRepositories;
import com.feelhub.test.TestFactories;
import org.junit.*;
import org.junit.rules.ExpectedException;

import java.util.List;

import static junit.framework.Assert.*;
import static org.hamcrest.MatcherAssert.*;
import static org.hamcrest.Matchers.*;

public class TestKeywordService {

    @Rule
    public WithDomainEvent bus = new WithDomainEvent();

    @Rule
    public WithFakeRepositories repositories = new WithFakeRepositories();

    @Rule
    public ExpectedException exception = ExpectedException.none();

    @Before
    public void before() {
        keywordService = new KeywordService(new ReferenceService(new ReferenceFactory()), new KeywordFactory(), new FakeTranslator(), new UriManager(new FakeUriResolver()));
    }

    @Test
    public void canLookUpAKeyword() {
        final Keyword keyword = TestFactories.keywords().newKeyword();

        final Keyword foundKeyword = keywordService.lookUp(keyword.getValue(), keyword.getLanguage());

        assertThat(foundKeyword, notNullValue());
    }

    @Test
    public void throwExceptionOnKeywordNotFound() {
        exception.expect(KeywordNotFound.class);

        keywordService.lookUp("any", FeelhubLanguage.forString("any"));
    }

    @Test
    public void canCreateAKeywordWithLanguageNone() {
        final String value = "Value";
        final FeelhubLanguage feelhubLanguage = FeelhubLanguage.none();

        final Keyword keyword = keywordService.createKeyword(value, feelhubLanguage);

        assertThat(keyword, notNullValue());
        assertThat(keyword.getValue(), is(value));
        assertThat(keyword.getLanguage(), is(feelhubLanguage));
        assertThat(Repositories.keywords().getAll().size(), is(1));
    }

    @Test
    public void canCreateAKeywordWithLanguageReference() {
        final String value = "Value";
        final FeelhubLanguage feelhubLanguage = FeelhubLanguage.reference();

        final Keyword keyword = keywordService.createKeyword(value, feelhubLanguage);

        assertThat(keyword, notNullValue());
        assertThat(keyword.getValue(), is(value));
        assertThat(keyword.getLanguage(), is(feelhubLanguage));
        assertThat(Repositories.keywords().getAll().size(), is(1));
    }

    @Test
    public void createAndPersistAReferenceFirst() {
        final String value = "value";
        final FeelhubLanguage feelhubLanguage = FeelhubLanguage.none();

        keywordService.createKeyword(value, feelhubLanguage);

        final List<Reference> references = Repositories.references().getAll();
        final List<Keyword> keywords = Repositories.keywords().getAll();
        assertThat(keywords.size(), is(1));
        assertThat(references.size(), is(1));
    }

    @Test
    public void canCreateAKeywordWithReference() {
        final Reference reference = TestFactories.references().newReference();
        final String value = "value";
        final FeelhubLanguage feelhubLanguage = FeelhubLanguage.none();

        final Keyword keyword = keywordService.createKeyword(value, feelhubLanguage, reference.getId());

        assertThat(keyword, notNullValue());
        assertThat(keyword.getValue(), is(value));
        assertThat(keyword.getLanguage(), is(feelhubLanguage));
        assertThat(Repositories.keywords().getAll().size(), is(1));
    }

    @Test
    public void canIdentifyUris() {
        assertTrue(KeywordService.isUri("http://www.google.com"));
        assertTrue(KeywordService.isUri("HTTP://www.google.com"));
        assertTrue(KeywordService.isUri("http://www.sub.google.com"));
        assertTrue(KeywordService.isUri("http://sub-test.google.com"));
        assertTrue(KeywordService.isUri("http://www.google.com/"));
        assertTrue(KeywordService.isUri("https://www.google.com"));
        assertTrue(KeywordService.isUri("https://www.google.com/"));
        assertTrue(KeywordService.isUri("www.google.com"));
        assertTrue(KeywordService.isUri("www.google.com/"));
        assertTrue(KeywordService.isUri("google.com"));
        assertTrue(KeywordService.isUri("google.com/"));
        assertTrue(KeywordService.isUri("http://google.com"));
        assertTrue(KeywordService.isUri("http://google.com/"));
        assertTrue(KeywordService.isUri("http://google.com/bin/#"));
        assertTrue(KeywordService.isUri("http://google.com/bin/#arf?id=true"));
        assertTrue(KeywordService.isUri("yala.fr"));
        assertTrue(KeywordService.isUri("yala.fr/"));
        assertTrue(KeywordService.isUri("www.%C3%A9l%C3%A9phant.com"));

        assertFalse(KeywordService.isUri("notanuri"));
        assertFalse(KeywordService.isUri("httpnotanuri"));
        assertFalse(KeywordService.isUri("http:notanuri"));
        assertFalse(KeywordService.isUri("http:/notanuri"));
        assertFalse(KeywordService.isUri("http://notanuri"));
        assertFalse(KeywordService.isUri("http://notanuri/"));
        assertFalse(KeywordService.isUri("http://notanuri/zala#lol"));
        assertFalse(KeywordService.isUri("notanuri.comm"));
        assertFalse(KeywordService.isUri(".com"));
        assertFalse(KeywordService.isUri(".fr"));
        assertFalse(KeywordService.isUri(".c"));
        assertFalse(KeywordService.isUri(".come"));
        assertFalse(KeywordService.isUri("ftp://www.google.com"));
        assertFalse(KeywordService.isUri("www.%C3%A9l%C3%A9phant."));
    }

    @Test
    public void translateIfNotReference() {
        final String value = "Value";
        final FeelhubLanguage feelhubLanguage = FeelhubLanguage.forString("fr");

        final Keyword keyword = keywordService.createKeyword(value, feelhubLanguage);

        assertThat(keyword, notNullValue());
        assertThat(keyword.getValue(), is(value));
        assertThat(keyword.getLanguage(), is(feelhubLanguage));
        assertThat(Repositories.keywords().getAll().size(), is(2));
        assertThat(Repositories.keywords().getAll().get(0).getReferenceId(), is(Repositories.keywords().getAll().get(1).getReferenceId()));
    }

    @Test
    public void doNotCreateSameKeywordTwice() {
        final String value = "Value";

        keywordService.createKeyword(value, FeelhubLanguage.forString("fr"));
        keywordService.createKeyword(value, FeelhubLanguage.forString("de"));

        assertThat(Repositories.keywords().getAll().size(), is(3));
        assertThat(Repositories.references().getAll().size(), is(1));
    }

    @Test
    public void lookUpExistingTranslation() {
        final Keyword referenceKeyword = TestFactories.keywords().newKeyword("Valueenglish", FeelhubLanguage.reference());

        keywordService.createKeyword("Value", FeelhubLanguage.forString("fr"));

        assertThat(Repositories.keywords().getAll().size(), is(2));
        assertThat(Repositories.keywords().getAll().get(0).getReferenceId(), is(referenceKeyword.getReferenceId()));
        assertThat(Repositories.keywords().getAll().get(1).getReferenceId(), is(referenceKeyword.getReferenceId()));
    }

    @Test
    public void makeReferenceInactiveOnTranslationError() {
        keywordService.createKeyword("Exception", FeelhubLanguage.forString("fr"));

        assertThat(Repositories.keywords().getAll().size(), is(1));
        assertThat(Repositories.keywords().getAll().get(0).isTranslationNeeded(), is(true));
    }

    @Test
    public void canCreateUri() {
        final String value = "http://www.test.com";
        final FeelhubLanguage feelhubLanguage = FeelhubLanguage.reference();

        final Keyword keyword = keywordService.createKeyword(value, feelhubLanguage);

        assertThat(keyword, notNullValue());
        assertThat(keyword.getValue(), is(value));
        assertThat(keyword.getLanguage(), is(FeelhubLanguage.none()));
        assertThat(Repositories.keywords().getAll().size(), is(2));
    }

    @Test
    public void onUriExceptionCreateConcept() {
        final String value = "http://www.exception.com";
        final FeelhubLanguage feelhubLanguage = FeelhubLanguage.none();

        final Keyword keyword = keywordService.createKeyword(value, feelhubLanguage);

        assertThat(keyword, notNullValue());
        assertThat(keyword.getValue(), is(value));
        assertThat(keyword.getLanguage(), is(FeelhubLanguage.none()));
        assertThat(Repositories.keywords().getAll().size(), is(1));
    }

    @Test
    public void steamIsNotAnUri() {
        assertFalse(KeywordService.isUri(""));
    }

    @Test
    public void doNotRequestIllustrationForSteam() {
        bus.capture(ConceptIllustrationRequestEvent.class);
        final String value = "";
        final FeelhubLanguage feelhubLanguage = FeelhubLanguage.none();

        keywordService.createKeyword(value, feelhubLanguage);

        final ConceptIllustrationRequestEvent conceptIllustrationRequestEvent = bus.lastEvent(ConceptIllustrationRequestEvent.class);
        assertNull(conceptIllustrationRequestEvent);
    }

    @Test
    public void requestUriIllustration() {
        bus.capture(UriIllustrationRequestEvent.class);
        final String value = "http://www.test.com";
        final FeelhubLanguage feelhubLanguage = FeelhubLanguage.reference();

        final Keyword keyword = keywordService.createKeyword(value, feelhubLanguage);

        final UriIllustrationRequestEvent uriIllustrationRequestEvent = bus.lastEvent(UriIllustrationRequestEvent.class);
        assertThat(uriIllustrationRequestEvent, notNullValue());
        assertThat(uriIllustrationRequestEvent.getReferenceId(), is(keyword.getReferenceId()));
    }

    @Test
    public void requestAlchemy() {
        bus.capture(AlchemyRequestEvent.class);
        final String value = "http://www.test.com";
        final FeelhubLanguage feelhubLanguage = FeelhubLanguage.reference();

        final Keyword keyword = keywordService.createKeyword(value, feelhubLanguage);

        final AlchemyRequestEvent alchemyRequestEvent = bus.lastEvent(AlchemyRequestEvent.class);
        assertThat(alchemyRequestEvent, notNullValue());
        assertThat(alchemyRequestEvent.getUri().getReferenceId(), is(keyword.getReferenceId()));
    }

    @Test
    public void requestConceptIllustration() {
        bus.capture(ConceptIllustrationRequestEvent.class);
        final String value = "value";
        final FeelhubLanguage feelhubLanguage = FeelhubLanguage.none();

        final Keyword keyword = keywordService.createKeyword(value, feelhubLanguage);

        final ConceptIllustrationRequestEvent conceptIllustrationRequestEvent = bus.lastEvent(ConceptIllustrationRequestEvent.class);
        assertThat(conceptIllustrationRequestEvent.getReferenceId(), is(keyword.getReferenceId()));
    }

    @Test
    public void lookUpIsCaseInsensitiveForConcept() {
        final String value = "Concept";
        final Keyword keyword = TestFactories.keywords().newKeyword(value);

        final Keyword foundKeyword = keywordService.lookUp("ConCEPT", keyword.getLanguage());

        assertThat(foundKeyword, is(keyword));
    }

    @Test
    public void lookUpIsCaseSensitiveForUri() {
        exception.expect(KeywordNotFound.class);
        final String value = "http://www.youtube.com/ALFED";
        final Keyword keyword = TestFactories.keywords().newKeyword(value);

        keywordService.lookUp("http://www.youtube.com/alfed", keyword.getLanguage());
    }

    @Test
    public void createAKeywordNormalizeTheValue() {
        final String value = "concepT";

        keywordService.createKeyword(value, FeelhubLanguage.reference());

        final Keyword foundKeyword = Repositories.keywords().getAll().get(0);
        assertThat(foundKeyword.getValue(), is("Concept"));
    }

    @Test
    public void lookUpOrCreateThrowBadValueExceptionIfConceptTooSmall() {
        exception.expect(BadValueException.class);

        keywordService.lookUpOrCreate("", FeelhubLanguage.reference().getCode());
    }

    @Test
    public void lookUpThrowBadValueExceptionIfConceptTooSmall() {
        exception.expect(BadValueException.class);

        keywordService.lookUp("", FeelhubLanguage.reference());
    }

    @Test
    public void correctlyNormalizeMultiWords() {
        final String value = "la mort";

        keywordService.createKeyword(value, FeelhubLanguage.reference());

        final Keyword foundKeyword = Repositories.keywords().getAll().get(0);
        assertThat(foundKeyword.getValue(), is("La Mort"));
    }

    private KeywordService keywordService;
}
