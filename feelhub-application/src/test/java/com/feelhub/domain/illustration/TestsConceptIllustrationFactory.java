package com.feelhub.domain.illustration;

import com.feelhub.domain.bingsearch.FakeBingLink;
import com.feelhub.domain.eventbus.*;
import com.feelhub.domain.topic.Topic;
import com.feelhub.repositories.Repositories;
import com.feelhub.repositories.fakeRepositories.*;
import com.feelhub.test.TestFactories;
import org.junit.*;

import java.util.List;

import static org.hamcrest.MatcherAssert.*;
import static org.hamcrest.Matchers.*;

public class TestsConceptIllustrationFactory {

    @Rule
    public WithFakeRepositories repositories = new WithFakeRepositories();

    @Rule
    public WithDomainEvent bus = new WithDomainEvent();

    @Before
    public void before() {
        new ConceptIllustrationFactory(new FakeBingLink(), new FakeSessionProvider());
    }

    @Test
    public void canCreateIllustration() {
        final Topic topic = TestFactories.topics().newTopic();
        final String value = "value";
        final ConceptIllustrationRequestEvent conceptIllustrationRequestEvent = new ConceptIllustrationRequestEvent(topic.getId(), value);

        DomainEventBus.INSTANCE.post(conceptIllustrationRequestEvent);

        final List<Illustration> illustrations = Repositories.illustrations().getAll();
        assertThat(illustrations.size(), is(1));
        assertThat(illustrations.get(0).getTopicId(), is(conceptIllustrationRequestEvent.getTopicId()));
        assertThat(illustrations.get(0).getLink(), is(value + "link"));
    }

    @Test
    public void checkForExistingIllustration() {
        final Topic topic = TestFactories.topics().newTopic();
        final String value = "value";
        final ConceptIllustrationRequestEvent conceptIllustrationRequestEvent = new ConceptIllustrationRequestEvent(topic.getId(), value);
        TestFactories.illustrations().newIllustration(topic);

        DomainEventBus.INSTANCE.post(conceptIllustrationRequestEvent);

        final List<Illustration> illustrations = Repositories.illustrations().getAll();
        assertThat(illustrations.size(), is(1));
    }
}
