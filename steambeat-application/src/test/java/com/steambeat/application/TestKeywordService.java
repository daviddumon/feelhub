package com.steambeat.application;

import com.steambeat.domain.alchemy.AlchemyRequestEvent;
import com.steambeat.domain.eventbus.WithDomainEvent;
import com.steambeat.domain.illustration.*;
import com.steambeat.domain.keyword.*;
import com.steambeat.domain.reference.*;
import com.steambeat.domain.thesaurus.SteambeatLanguage;
import com.steambeat.domain.translation.FakeTranslator;
import com.steambeat.domain.uri.*;
import com.steambeat.repositories.Repositories;
import com.steambeat.repositories.fakeRepositories.WithFakeRepositories;
import com.steambeat.test.TestFactories;
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

        keywordService.lookUp("any", SteambeatLanguage.forString("any"));
    }

    @Test
    public void canCreateAKeywordWithLanguageNone() {
        final String value = "value";
        final SteambeatLanguage steambeatLanguage = SteambeatLanguage.none();

        final Keyword keyword = keywordService.createKeyword(value, steambeatLanguage);

        assertThat(keyword, notNullValue());
        assertThat(keyword.getValue(), is(value));
        assertThat(keyword.getLanguage(), is(steambeatLanguage));
        assertThat(Repositories.keywords().getAll().size(), is(1));
    }

    @Test
    public void canCreateAKeywordWithLanguageReference() {
        final String value = "value";
        final SteambeatLanguage steambeatLanguage = SteambeatLanguage.reference();

        final Keyword keyword = keywordService.createKeyword(value, steambeatLanguage);

        assertThat(keyword, notNullValue());
        assertThat(keyword.getValue(), is(value));
        assertThat(keyword.getLanguage(), is(steambeatLanguage));
        assertThat(Repositories.keywords().getAll().size(), is(1));
    }

    @Test
    public void createAndPersistAReferenceFirst() {
        final String value = "value";
        final SteambeatLanguage steambeatLanguage = SteambeatLanguage.none();

        keywordService.createKeyword(value, steambeatLanguage);

        final List<Reference> references = Repositories.references().getAll();
        final List<Keyword> keywords = Repositories.keywords().getAll();
        assertThat(keywords.size(), is(1));
        assertThat(references.size(), is(1));
    }

    @Test
    public void canCreateAKeywordWithReference() {
        final Reference reference = TestFactories.references().newReference();
        final String value = "value";
        final SteambeatLanguage steambeatLanguage = SteambeatLanguage.none();

        final Keyword keyword = keywordService.createKeyword(value, steambeatLanguage, reference.getId());

        assertThat(keyword, notNullValue());
        assertThat(keyword.getValue(), is(value));
        assertThat(keyword.getLanguage(), is(steambeatLanguage));
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
        final String value = "value";
        final SteambeatLanguage steambeatLanguage = SteambeatLanguage.forString("fr");

        final Keyword keyword = keywordService.createKeyword(value, steambeatLanguage);

        assertThat(keyword, notNullValue());
        assertThat(keyword.getValue(), is(value));
        assertThat(keyword.getLanguage(), is(steambeatLanguage));
        assertThat(Repositories.keywords().getAll().size(), is(2));
        assertThat(Repositories.keywords().getAll().get(0).getReferenceId(), is(Repositories.keywords().getAll().get(1).getReferenceId()));
    }

    @Test
    public void doNotCreateSameKeywordTwice() {
        final String value = "value";

        keywordService.createKeyword(value, SteambeatLanguage.forString("fr"));
        keywordService.createKeyword(value, SteambeatLanguage.forString("de"));

        assertThat(Repositories.keywords().getAll().size(), is(3));
        assertThat(Repositories.references().getAll().size(), is(1));
    }

    @Test
    public void lookUpExistingTranslation() {
        final Keyword referenceKeyword = TestFactories.keywords().newKeyword("valueenglish", SteambeatLanguage.reference());

        keywordService.createKeyword("value", SteambeatLanguage.forString("fr"));

        assertThat(Repositories.keywords().getAll().size(), is(2));
        assertThat(Repositories.keywords().getAll().get(0).getReferenceId(), is(referenceKeyword.getReferenceId()));
        assertThat(Repositories.keywords().getAll().get(1).getReferenceId(), is(referenceKeyword.getReferenceId()));
    }

    @Test
    public void makeReferenceInactiveOnTranslationError() {
        keywordService.createKeyword("exception", SteambeatLanguage.forString("fr"));

        assertThat(Repositories.keywords().getAll().size(), is(1));
        assertThat(Repositories.keywords().getAll().get(0).isTranslationNeeded(), is(true));
    }

    @Test
    public void canCreateUri() {
        final String value = "http://www.test.com";
        final SteambeatLanguage steambeatLanguage = SteambeatLanguage.reference();

        final Keyword keyword = keywordService.createKeyword(value, steambeatLanguage);

        assertThat(keyword, notNullValue());
        assertThat(keyword.getValue(), is(value));
        assertThat(keyword.getLanguage(), is(SteambeatLanguage.none()));
        assertThat(Repositories.keywords().getAll().size(), is(2));
    }

    @Test
    public void onUriExceptionCreateConcept() {
        final String value = "http://www.exception.com";
        final SteambeatLanguage steambeatLanguage = SteambeatLanguage.none();

        final Keyword keyword = keywordService.createKeyword(value, steambeatLanguage);

        assertThat(keyword, notNullValue());
        assertThat(keyword.getValue(), is(value));
        assertThat(keyword.getLanguage(), is(SteambeatLanguage.none()));
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
        final SteambeatLanguage steambeatLanguage = SteambeatLanguage.none();

        keywordService.createKeyword(value, steambeatLanguage);

        final ConceptIllustrationRequestEvent conceptIllustrationRequestEvent = bus.lastEvent(ConceptIllustrationRequestEvent.class);
        assertNull(conceptIllustrationRequestEvent);
    }

    @Test
    public void requestUriIllustration() {
        bus.capture(UriIllustrationRequestEvent.class);
        final String value = "http://www.test.com";
        final SteambeatLanguage steambeatLanguage = SteambeatLanguage.reference();

        final Keyword keyword = keywordService.createKeyword(value, steambeatLanguage);

        final UriIllustrationRequestEvent uriIllustrationRequestEvent = bus.lastEvent(UriIllustrationRequestEvent.class);
        assertThat(uriIllustrationRequestEvent, notNullValue());
        assertThat(uriIllustrationRequestEvent.getReferenceId(), is(keyword.getReferenceId()));
    }

    @Test
    public void requestAlchemy() {
        bus.capture(AlchemyRequestEvent.class);
        final String value = "http://www.test.com";
        final SteambeatLanguage steambeatLanguage = SteambeatLanguage.reference();

        final Keyword keyword = keywordService.createKeyword(value, steambeatLanguage);

        final AlchemyRequestEvent alchemyRequestEvent = bus.lastEvent(AlchemyRequestEvent.class);
        assertThat(alchemyRequestEvent, notNullValue());
        assertThat(alchemyRequestEvent.getUri().getReferenceId(), is(keyword.getReferenceId()));
    }

    @Test
    public void requestConceptIllustration() {
        bus.capture(ConceptIllustrationRequestEvent.class);
        final String value = "value";
        final SteambeatLanguage steambeatLanguage = SteambeatLanguage.none();

        final Keyword keyword = keywordService.createKeyword(value, steambeatLanguage);

        final ConceptIllustrationRequestEvent conceptIllustrationRequestEvent = bus.lastEvent(ConceptIllustrationRequestEvent.class);
        assertThat(conceptIllustrationRequestEvent.getReferenceId(), is(keyword.getReferenceId()));
    }

    private KeywordService keywordService;
}
