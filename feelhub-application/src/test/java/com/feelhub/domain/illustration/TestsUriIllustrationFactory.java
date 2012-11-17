package com.feelhub.domain.illustration;

import com.feelhub.domain.eventbus.*;
import com.feelhub.domain.keyword.Keyword;
import com.feelhub.domain.scraper.*;
import com.feelhub.domain.topic.Topic;
import com.feelhub.repositories.*;
import com.feelhub.repositories.fakeRepositories.*;
import com.feelhub.test.TestFactories;
import com.google.inject.*;
import org.junit.*;

import java.util.List;

import static org.hamcrest.MatcherAssert.*;
import static org.hamcrest.Matchers.*;

public class TestsUriIllustrationFactory {

    @Rule
    public WithFakeRepositories repositories = new WithFakeRepositories();

    @Rule
    public WithDomainEvent bus = new WithDomainEvent();

    @Before
    public void before() {
        final Injector injector = Guice.createInjector(new AbstractModule() {
            @Override
            protected void configure() {
                bind(Scraper.class).to(FakeScraper.class);
                bind(SessionProvider.class).to(FakeSessionProvider.class);
            }
        });
        injector.getInstance(UriIllustrationFactory.class);
    }

    @Test
    public void canCreateIllustration() {
        final Keyword keyword = TestFactories.keywords().newWord();
        final UriIllustrationRequestEvent uriIllustrationRequestEvent = new UriIllustrationRequestEvent(keyword.getTopicId(), keyword.getValue());

        DomainEventBus.INSTANCE.post(uriIllustrationRequestEvent);

        final List<Illustration> illustrations = Repositories.illustrations().getAll();
        assertThat(illustrations.size(), is(1));
        assertThat(illustrations.get(0).getTopicId(), is(uriIllustrationRequestEvent.getTopicId()));
        assertThat(illustrations.get(0).getLink(), is("fakeillustration"));
    }


    @Test
    public void checkForExistingIllustration() {
        final Topic topic = TestFactories.topics().newTopic();
        final String value = "value";
        final UriIllustrationRequestEvent uriIllustrationRequestEvent = new UriIllustrationRequestEvent(topic.getId(), value);
        TestFactories.illustrations().newIllustration(topic.getId());

        DomainEventBus.INSTANCE.post(uriIllustrationRequestEvent);

        final List<Illustration> illustrations = Repositories.illustrations().getAll();
        assertThat(illustrations.size(), is(1));
    }
}
