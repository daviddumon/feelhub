package com.steambeat.domain.keyword;

import com.steambeat.domain.eventbus.WithDomainEvent;
import com.steambeat.domain.thesaurus.SteambeatLanguage;
import com.steambeat.domain.reference.Reference;
import com.steambeat.test.*;
import com.steambeat.repositories.fakeRepositories.WithFakeRepositories;
import org.junit.*;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.junit.Assert.*;

public class TestsKeywordFactory {

    @Rule
    public SystemTime time = SystemTime.fixed();

    @Rule
    public WithDomainEvent bus = new WithDomainEvent();

    @Rule
    public WithFakeRepositories repositories = new WithFakeRepositories();

    @Before
    public void before() {
        keywordFactory = new KeywordFactory();
    }

    @Test
    public void canCreateAKeyword() {
        final String value = "value";
        final SteambeatLanguage steambeatLanguage = SteambeatLanguage.forString("english");
        final Reference reference = TestFactories.references().newReference();

        Keyword keyword = keywordFactory.createKeyword(value, steambeatLanguage, reference.getId());

        assertNotNull(keyword);
        assertThat(keyword.getValue(), is(value));
        assertThat(keyword.getLanguage(), is(steambeatLanguage));
        assertThat(keyword.getId(), notNullValue());
        assertThat(keyword.getReferenceId(), notNullValue());
        assertThat(keyword.getCreationDate(), is(time.getNow()));
        assertThat(keyword.getLastModificationDate(), is(time.getNow()));
    }

    @Test
    public void postEventOnKeywordCreation() {
        bus.capture(KeywordCreatedEvent.class);
        final String value = "value";
        final SteambeatLanguage steambeatLanguage = SteambeatLanguage.forString("english");
        final Reference reference = TestFactories.references().newReference();

        Keyword keyword = keywordFactory.createKeyword(value, steambeatLanguage, reference.getId());

        final KeywordCreatedEvent keywordCreatedEvent = bus.lastEvent(KeywordCreatedEvent.class);
        assertThat(keywordCreatedEvent, notNullValue());
        assertThat(keywordCreatedEvent.getKeyword(), is(keyword));
        assertThat(keywordCreatedEvent.getDate(), is(time.getNow()));
    }

    @Test
    public void canCreateKeywordsWithSameReference() {
        final Reference reference = TestFactories.references().newReference();

        Keyword keyword1 = keywordFactory.createKeyword("value1", SteambeatLanguage.reference(), reference.getId());
        Keyword keyword2 = keywordFactory.createKeyword("value2", SteambeatLanguage.reference(), reference.getId());

        assertThat(keyword1.getReferenceId(), is(keyword2.getReferenceId()));
    }

    private KeywordFactory keywordFactory;
}
