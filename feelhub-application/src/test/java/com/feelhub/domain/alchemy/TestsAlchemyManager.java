package com.feelhub.domain.alchemy;

import com.feelhub.domain.topic.TopicPatch;
import com.feelhub.domain.topic.usable.real.RealTopic;
import com.feelhub.repositories.Repositories;
import com.feelhub.repositories.fakeRepositories.WithFakeRepositories;
import com.feelhub.test.TestFactories;
import com.google.inject.*;
import org.junit.*;

import java.util.List;

import static org.fest.assertions.Assertions.*;

public class TestsAlchemyManager {

    @Rule
    public WithFakeRepositories repositories = new WithFakeRepositories();

    @Before
    public void before() {
        final Injector injector = Guice.createInjector(new AbstractModule() {
            @Override
            protected void configure() {
            }
        });
        alchemyManager = injector.getInstance(AlchemyManager.class);
    }

    @Test
    public void canMergeAlchemyEntities() {
        final RealTopic newRealTopic = TestFactories.topics().newCompleteRealTopic();
        final RealTopic oldRealTopic = TestFactories.topics().newCompleteRealTopic();
        final TopicPatch topicPatch = new TopicPatch(newRealTopic.getId());
        topicPatch.addOldTopicId(oldRealTopic.getId());
        TestFactories.alchemy().newAlchemyEntityEntity(oldRealTopic.getId());

        alchemyManager.merge(topicPatch);

        final List<AlchemyEntity> alchemyEntities = Repositories.alchemyEntities().getAll();
        assertThat(alchemyEntities.get(0).getTopicId()).isEqualTo(newRealTopic.getId());
    }

    @Test
    public void canMergeAlchemyAnalysis() {
        final RealTopic newRealTopic = TestFactories.topics().newCompleteRealTopic();
        final RealTopic oldRealTopic = TestFactories.topics().newCompleteRealTopic();
        final TopicPatch topicPatch = new TopicPatch(newRealTopic.getId());
        topicPatch.addOldTopicId(oldRealTopic.getId());
        TestFactories.alchemy().newAlchemyAnalysis(oldRealTopic);

        alchemyManager.merge(topicPatch);

        final List<AlchemyAnalysis> alchemyAnalysisList = Repositories.alchemyAnalysis().getAll();
        assertThat(alchemyAnalysisList.get(0).getTopicId()).isEqualTo(newRealTopic.getId());
    }

    @Test
    public void removeDuplicateAlchemyEntities() {
        final RealTopic newRealTopic = TestFactories.topics().newCompleteRealTopic();
        final RealTopic oldRealTopic = TestFactories.topics().newCompleteRealTopic();
        final TopicPatch topicPatch = new TopicPatch(newRealTopic.getId());
        topicPatch.addOldTopicId(oldRealTopic.getId());
        TestFactories.alchemy().newAlchemyEntityEntity(oldRealTopic.getId());
        TestFactories.alchemy().newAlchemyEntityEntity(newRealTopic.getId());

        alchemyManager.merge(topicPatch);

        final List<AlchemyEntity> alchemyEntities = Repositories.alchemyEntities().getAll();
        assertThat(alchemyEntities.size()).isEqualTo(1);
    }

    @Test
    public void removeDuplicateAlchemyAnalysis() {
        final RealTopic newRealTopic = TestFactories.topics().newCompleteRealTopic();
        final RealTopic oldRealTopic = TestFactories.topics().newCompleteRealTopic();
        final TopicPatch topicPatch = new TopicPatch(newRealTopic.getId());
        topicPatch.addOldTopicId(oldRealTopic.getId());
        TestFactories.alchemy().newAlchemyAnalysis(oldRealTopic);
        TestFactories.alchemy().newAlchemyAnalysis(newRealTopic);

        alchemyManager.merge(topicPatch);

        final List<AlchemyAnalysis> alchemyAnalysisList = Repositories.alchemyAnalysis().getAll();
        assertThat(alchemyAnalysisList.size()).isEqualTo(1);
    }

    private AlchemyManager alchemyManager;
}
