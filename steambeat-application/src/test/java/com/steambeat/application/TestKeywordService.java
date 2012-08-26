package com.steambeat.application;

import com.steambeat.domain.eventbus.WithDomainEvent;
import com.steambeat.domain.keyword.*;
import com.steambeat.domain.reference.*;
import com.steambeat.domain.thesaurus.SteambeatLanguage;
import com.steambeat.repositories.Repositories;
import com.steambeat.repositories.fakeRepositories.WithFakeRepositories;
import com.steambeat.test.*;
import org.junit.*;
import org.junit.rules.ExpectedException;

import java.util.List;

import static org.hamcrest.MatcherAssert.*;
import static org.hamcrest.Matchers.*;

public class TestKeywordService {

    @Rule
    public WithFakeRepositories repositories = new WithFakeRepositories();

    @Rule
    public ExpectedException exception = ExpectedException.none();

    @Rule
    public WithDomainEvent bus = new WithDomainEvent();

    @Rule
    public SystemTime time = SystemTime.fixed();

    @Before
    public void before() {
        keywordService = new KeywordService(new KeywordFactory(), new ReferenceService(new ReferenceFactory()));
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
    public void canCreateAKeyword() {
        final String value = "value";
        final SteambeatLanguage steambeatLanguage = SteambeatLanguage.forString("none");

        final Keyword keyword = keywordService.createKeyword(value, steambeatLanguage);

        assertThat(keyword, notNullValue());
        assertThat(keyword.getValue(), is(value));
        assertThat(keyword.getLanguage(), is(steambeatLanguage));
        assertThat(Repositories.keywords().getAll().size(), is(1));
    }

    @Test
    public void createAndPersistAReferenceFirst() {
        final String value = "value";
        final SteambeatLanguage steambeatLanguage = SteambeatLanguage.forString("none");

        keywordService.createKeyword(value, steambeatLanguage);

        final List<Reference> references = Repositories.references().getAll();
        final List<Keyword> keywords = Repositories.keywords().getAll();
        assertThat(keywords.size(), is(1));
        assertThat(references.size(), is(1));
    }

    @Test
    public void postEventOnKeywordCreation() {
        bus.capture(KeywordCreatedEvent.class);
        final String value = "value";
        final SteambeatLanguage steambeatLanguage = SteambeatLanguage.forString("english");

        final Keyword keyword = keywordService.createKeyword(value, steambeatLanguage);

        final KeywordCreatedEvent keywordCreatedEvent = bus.lastEvent(KeywordCreatedEvent.class);
        assertThat(keywordCreatedEvent, notNullValue());
        assertThat(keywordCreatedEvent.getKeyword(), is(keyword));
        assertThat(keywordCreatedEvent.getDate(), is(time.getNow()));
    }

    private KeywordService keywordService;
}
