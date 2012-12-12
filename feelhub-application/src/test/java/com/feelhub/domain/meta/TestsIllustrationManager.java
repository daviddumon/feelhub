package com.feelhub.domain.meta;

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

public class TestsIllustrationManager {

    @Rule
    public WithFakeRepositories repositories = new WithFakeRepositories();

    @Before
    public void before() {
        final Injector injector = Guice.createInjector(new AbstractModule() {
            @Override
            protected void configure() {
            }
        });
        illustrationManager = injector.getInstance(IllustrationManager.class);
    }

    @Test
    public void canMigrateTwoExistingIllustrations() {
        final RealTopic first = TestFactories.topics().newCompleteRealTopic();
        TestFactories.illustrations().newIllustration(first.getId(), "link1");
        final RealTopic second = TestFactories.topics().newCompleteRealTopic();
        final Illustration illustrationToChange = TestFactories.illustrations().newIllustration(second.getId(), "link2");
        final TopicPatch topicPatch = new TopicPatch(first.getId());
        topicPatch.addOldTopicId(second.getId());

        illustrationManager.merge(topicPatch);

        assertThat(illustrationToChange.getTopicId(), is(first.getId()));
    }

    @Test
    public void canDeleteDuplicateIllustrations() {
        final RealTopic first = TestFactories.topics().newCompleteRealTopic();
        TestFactories.illustrations().newIllustration(first.getId(), "link1");
        final RealTopic second = TestFactories.topics().newCompleteRealTopic();
        TestFactories.illustrations().newIllustration(second.getId(), "link2");
        final TopicPatch topicPatch = new TopicPatch(first.getId());
        topicPatch.addOldTopicId(second.getId());

        illustrationManager.merge(topicPatch);

        final List<Illustration> illustrations = Repositories.illustrations().getAll();
        assertThat(illustrations.size(), is(1));
    }

    @Test
    public void doNotCreateIfAlreadyOneIllustrationExists() {
        final RealTopic first = TestFactories.topics().newCompleteRealTopic();
        final Illustration illustration = TestFactories.illustrations().newIllustration(first.getId(), "link1");
        final RealTopic second = TestFactories.topics().newCompleteRealTopic();
        final TopicPatch topicPatch = new TopicPatch(first.getId());
        topicPatch.addOldTopicId(second.getId());

        illustrationManager.merge(topicPatch);

        final List<Illustration> illustrations = Repositories.illustrations().getAll();
        assertThat(illustrations.size(), is(1));
        final Illustration foundIllustration = illustrations.get(0);
        assertThat(foundIllustration, is(illustration));
    }

    private IllustrationManager illustrationManager;
}
