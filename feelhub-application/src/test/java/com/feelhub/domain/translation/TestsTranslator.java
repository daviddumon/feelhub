package com.feelhub.domain.translation;

import com.feelhub.domain.admin.*;
import com.feelhub.domain.eventbus.*;
import com.feelhub.domain.thesaurus.FeelhubLanguage;
import com.feelhub.domain.topic.real.*;
import com.feelhub.repositories.Repositories;
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
    private Translator translator;

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
        final RealTopic realTopic = TestFactories.topics().newRealTopicWithoutNames(RealTopicType.Anniversary);
        final ReferenceTranslationRequestEvent referenceTranslationRequestEvent = getEvent(realTopic);

        DomainEventBus.INSTANCE.post(referenceTranslationRequestEvent);

        assertThat(realTopic.getNames().size()).isEqualTo(1);
    }

    @Test
    public void addTagsForTranslation() {
        final RealTopic realTopic = TestFactories.topics().newRealTopicWithoutNames(RealTopicType.Anniversary);
        final ReferenceTranslationRequestEvent referenceTranslationRequestEvent = getEvent(realTopic);

        DomainEventBus.INSTANCE.post(referenceTranslationRequestEvent);

        assertThat(Repositories.tags().getAll().size()).isEqualTo(1);
        assertThat(Repositories.tags().getAll().get(0).getTopicsIdFor(FeelhubLanguage.reference())).contains(realTopic.getId());
    }

    @Test
    public void incrementApiCallsCounter() {
        final ReferenceTranslationRequestEvent referenceTranslationRequestEvent = getEvent(TestFactories.topics().newRealTopicWithoutNames(RealTopicType.Anniversary));

        translator.onTranslationRequest(referenceTranslationRequestEvent);

        ApiCallEvent event = bus.lastEvent(ApiCallEvent.class);
        assertThat(event).isNotNull();
        assertThat(event.getApi()).isEqualTo(Api.MicrosoftTranslate);
        assertThat(event.getIncrement()).isEqualTo(7);
    }

    private ReferenceTranslationRequestEvent getEvent(RealTopic realTopic) {
        return new ReferenceTranslationRequestEvent(realTopic, FeelhubLanguage.fromCode("fr"), "name-fr");
    }

}
