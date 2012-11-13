package com.feelhub.application;

import com.feelhub.domain.alchemy.AlchemyRequestEvent;
import com.feelhub.domain.eventbus.WithDomainEvent;
import com.feelhub.domain.illustration.UriIllustrationRequestEvent;
import com.feelhub.domain.keyword.BadValueException;
import com.feelhub.domain.keyword.uri.*;
import com.feelhub.domain.thesaurus.FeelhubLanguage;
import com.feelhub.domain.topic.Topic;
import com.feelhub.repositories.Repositories;
import com.feelhub.repositories.fakeRepositories.WithFakeRepositories;
import com.feelhub.test.TestFactories;
import com.google.inject.*;
import org.junit.*;
import org.junit.rules.ExpectedException;

import java.util.List;

import static org.fest.assertions.Assertions.assertThat;
import static org.junit.Assert.*;

public class TestsUriService {

    @Rule
    public WithDomainEvent bus = new WithDomainEvent();

    @Rule
    public WithFakeRepositories repositories = new WithFakeRepositories();

    @Rule
    public ExpectedException exception = ExpectedException.none();

    @Before
    public void before() {
        final Injector injector = Guice.createInjector(new AbstractModule() {
            @Override
            protected void configure() {
                bind(UriResolver.class).to(FakeUriResolver.class);
            }
        });
        uriService = injector.getInstance(UriService.class);
    }

    @Test
    public void canLookUpAnUri() {
        final Uri uri = TestFactories.keywords().newUri();

        final Uri uriFound = uriService.lookUp(uri.getValue());

        assertThat(uriFound).isNotNull();
    }

    @Test
    public void canThrowUriNotFound() {
        exception.expect(UriNotFound.class);

        uriService.lookUp("any");
    }

    @Test
    public void canCreateAnUri() {
        final Uri uri = uriService.createUri("http://www.google.fr");

        assertThat(uri).isNotNull();
        assertThat(uri.getValue()).isEqualTo("http://www.google.fr");
        assertThat(uri.getLanguage()).isEqualTo(FeelhubLanguage.none());
        assertThat(Repositories.keywords().getAll().size()).isEqualTo(2);
    }

    @Test
    public void onUriExceptionCreateConcept() {
        //final String value = "http://www.exception.com";
        //
        //final Keyword word = uriService.createUri(value);
        //
        //assertThat(word).isNotNull();
        //assertThat(word.getValue()).isEqualTo(value);
        //assertThat(word.getLanguage()).isEqualTo(FeelhubLanguage.none());
        //assertThat(Repositories.keywords().getAll().size()).isEqualTo(1);
        //fail();
    }

    @Test
    public void createAndPersistATopicFirst() {
        final String value = "http://www.google.fr";

        uriService.createUri(value);

        final List<Topic> topics = Repositories.topics().getAll();
        assertThat(topics.size()).isEqualTo(1);
    }

    @Test
    public void doNotCreateSameUriTwice() {
        final String value = "http://www.google.fr";

        uriService.createUri(value);
        uriService.createUri(value);

        assertThat(Repositories.keywords().getAll().size()).isEqualTo(2);
        assertThat(Repositories.topics().getAll().size()).isEqualTo(2);
    }

    @Test
    public void requestUriIllustration() {
        bus.capture(UriIllustrationRequestEvent.class);
        final String value = "value";

        final Uri uriCreated = uriService.createUri(value);

        final UriIllustrationRequestEvent uriIllustrationRequestEvent = bus.lastEvent(UriIllustrationRequestEvent.class);
        assertThat(uriIllustrationRequestEvent.getTopicId()).isEqualTo(uriCreated.getTopicId());
    }

    @Test
    public void lookUpThrowBadValueExceptionIfConceptTooSmall() {
        exception.expect(BadValueException.class);

        uriService.lookUp("");
    }

    @Test
    public void canLookupOrCreateAUri() {
        final Uri uri = TestFactories.keywords().newUri();

        final Uri uriFound = uriService.lookUpOrCreate(uri.getValue());

        assertThat(uriFound).isNotNull();
        assertThat(uriFound).isEqualTo(uri);
    }

    @Test
    public void canLookUpOrCreateWithoutExistingUri() {
        final Uri uriFound = uriService.lookUpOrCreate("http://www.google.fr");

        assertThat(uriFound).isNotNull();
    }

    @Test
    public void checkEmptyForLookUpOrCreate() {
        exception.expect(BadValueException.class);

        uriService.lookUpOrCreate("");
    }

    @Test
    public void requestAlchemy() {
        bus.capture(AlchemyRequestEvent.class);
        final String value = "http://www.test.com";

        final Uri uriCreated = uriService.createUri(value);

        final AlchemyRequestEvent alchemyRequestEvent = bus.lastEvent(AlchemyRequestEvent.class);
        assertThat(alchemyRequestEvent).isNotNull();
        assertThat(alchemyRequestEvent.getUri().getTopicId()).isEqualTo(uriCreated.getTopicId());
    }

    @Test
    public void lookUpIsCaseSensitiveForUri() {
        exception.expect(UriNotFound.class);
        final String value = "http://www.youtube.com/ALFED";
        TestFactories.keywords().newUri(value);

        uriService.lookUp("http://www.youtube.com/alfed");
    }

    private UriService uriService;
}
