package com.feelhub.domain.related;

import com.feelhub.domain.eventbus.WithDomainEvent;
import com.feelhub.domain.topic.TopicPatch;
import com.feelhub.domain.topic.real.RealTopic;
import com.feelhub.repositories.Repositories;
import com.feelhub.repositories.fakeRepositories.WithFakeRepositories;
import com.feelhub.test.TestFactories;
import com.google.inject.*;
import org.junit.*;

import java.util.List;

import static org.hamcrest.MatcherAssert.*;
import static org.hamcrest.Matchers.*;

public class TestsRelatedManager {

    @Rule
    public WithDomainEvent bus = new WithDomainEvent();

    @Rule
    public WithFakeRepositories repositories = new WithFakeRepositories();

    @Before
    public void before() {
        final Injector injector = Guice.createInjector(new AbstractModule() {
            @Override
            protected void configure() {
            }
        });
        relatedManager = injector.getInstance(RelatedManager.class);
    }

    @Test
    public void canChangeRelatedTopics() {
        final RealTopic realTopic1 = TestFactories.topics().newCompleteRealTopic();
        final RealTopic realTopic2 = TestFactories.topics().newCompleteRealTopic();
        final RealTopic realTopic3 = TestFactories.topics().newCompleteRealTopic();
        final Related related1 = TestFactories.related().newRelated(realTopic2.getId(), realTopic3.getId());
        final Related related2 = TestFactories.related().newRelated(realTopic3.getId(), realTopic2.getId());
        final TopicPatch topicPatch = new TopicPatch(realTopic1.getId());
        topicPatch.addOldTopicId(realTopic2.getId());

        relatedManager.merge(topicPatch);

        assertThat(related1.getFromId(), is(realTopic1.getId()));
        assertThat(related1.getToId(), is(realTopic3.getId()));
        assertThat(related2.getFromId(), is(realTopic3.getId()));
        assertThat(related2.getToId(), is(realTopic1.getId()));
    }

    @Test
    public void canRemoveAutoRelated() {
        final RealTopic realTopic1 = TestFactories.topics().newCompleteRealTopic();
        final RealTopic realTopic2 = TestFactories.topics().newCompleteRealTopic();
        TestFactories.related().newRelated(realTopic1.getId(), realTopic2.getId());
        TestFactories.related().newRelated(realTopic2.getId(), realTopic1.getId());
        final TopicPatch topicPatch = new TopicPatch(realTopic1.getId());
        topicPatch.addOldTopicId(realTopic2.getId());

        relatedManager.merge(topicPatch);

        assertThat(Repositories.related().getAll().size(), is(0));
    }

    @Test
    public void canMergeDuplicate() {
        final RealTopic realTopic1 = TestFactories.topics().newCompleteRealTopic();
        final RealTopic realTopic2 = TestFactories.topics().newCompleteRealTopic();
        final RealTopic realTopic3 = TestFactories.topics().newCompleteRealTopic();
        TestFactories.related().newRelated(realTopic1.getId(), realTopic2.getId());
        TestFactories.related().newRelated(realTopic3.getId(), realTopic2.getId());
        final TopicPatch topicPatch = new TopicPatch(realTopic1.getId());
        topicPatch.addOldTopicId(realTopic3.getId());

        relatedManager.merge(topicPatch);

        final List<Related> relatedList = Repositories.related().getAll();
        assertThat(relatedList.size(), is(1));
        assertThat(relatedList.get(0).getWeight(), is(2.0));
    }

    private RelatedManager relatedManager;
}
