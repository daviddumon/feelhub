package com.feelhub.domain.translation;

import com.feelhub.domain.eventbus.*;
import com.feelhub.domain.topic.*;
import com.feelhub.repositories.fakeRepositories.WithFakeRepositories;
import com.feelhub.test.TestFactories;
import com.google.inject.*;
import org.junit.*;

import static org.fest.assertions.Assertions.*;

public class TestsTranslator {

    @Rule
    public WithFakeRepositories repositories = new WithFakeRepositories();

    @Rule
    public WithDomainEvent bus = new WithDomainEvent();

    @Before
    public void before() {
        final Injector injector = Guice.createInjector(new AbstractModule() {
            @Override
            protected void configure() {
                bind(Translator.class).to(FakeTranslator.class);
            }
        });
        translator = injector.getInstance(Translator.class);
    }

    @Test
    public void translateOnTranslationRequest() {
        final Topic topic = TestFactories.topics().newTopicWithoutReference(TopicType.Anniversary);
        final ReferenceTranslatioRequestEvent referenceTranslatioRequestEvent = new ReferenceTranslatioRequestEvent(topic);

        DomainEventBus.INSTANCE.post(referenceTranslatioRequestEvent);

        assertThat(topic.getDescriptions().size()).isEqualTo(2);
    }

    private Translator translator;
}
