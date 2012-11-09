package com.feelhub.domain.alchemy;

import com.feelhub.domain.topic.*;
import com.feelhub.repositories.Repositories;
import com.feelhub.repositories.fakeRepositories.WithFakeRepositories;
import com.feelhub.test.TestFactories;
import org.junit.*;

import java.util.List;

import static org.hamcrest.Matchers.*;
import static org.junit.Assert.*;

public class TestsAlchemyManager {

    @Rule
    public WithFakeRepositories repositories = new WithFakeRepositories();

    @Before
    public void before() {
        alchemyManager = new AlchemyManager();
    }

    @Test
    public void canMergeAlchemyEntities() {
        final Topic newTopic = TestFactories.topics().newTopic();
        final Topic oldTopic = TestFactories.topics().newTopic();
        final TopicPatch topicPatch = new TopicPatch(newTopic.getId());
        topicPatch.addOldTopicId(oldTopic.getId());
        TestFactories.alchemy().newAlchemyEntityEntity(oldTopic.getId());

        alchemyManager.merge(topicPatch);

        final List<AlchemyEntity> alchemyEntities = Repositories.alchemyEntities().getAll();
        assertThat(alchemyEntities.get(0).getTopicId(), is(newTopic.getId()));
    }

    @Test
    public void canMergeAlchemyAnalysis() {
        final Topic newTopic = TestFactories.topics().newTopic();
        final Topic oldTopic = TestFactories.topics().newTopic();
        final TopicPatch topicPatch = new TopicPatch(newTopic.getId());
        topicPatch.addOldTopicId(oldTopic.getId());
        TestFactories.alchemy().newAlchemyAnalysis(oldTopic);

        alchemyManager.merge(topicPatch);

        final List<AlchemyAnalysis> alchemyAnalysisList = Repositories.alchemyAnalysis().getAll();
        assertThat(alchemyAnalysisList.get(0).getTopicId(), is(newTopic.getId()));
    }

    @Test
    public void removeDuplicateAlchemyEntities() {
        final Topic newTopic = TestFactories.topics().newTopic();
        final Topic oldTopic = TestFactories.topics().newTopic();
        final TopicPatch topicPatch = new TopicPatch(newTopic.getId());
        topicPatch.addOldTopicId(oldTopic.getId());
        TestFactories.alchemy().newAlchemyEntityEntity(oldTopic.getId());
        TestFactories.alchemy().newAlchemyEntityEntity(newTopic.getId());

        alchemyManager.merge(topicPatch);

        final List<AlchemyEntity> alchemyEntities = Repositories.alchemyEntities().getAll();
        assertThat(alchemyEntities.size(), is(1));
    }

    @Test
    public void removeDuplicateAlchemyAnalysis() {
        final Topic newTopic = TestFactories.topics().newTopic();
        final Topic oldTopic = TestFactories.topics().newTopic();
        final TopicPatch topicPatch = new TopicPatch(newTopic.getId());
        topicPatch.addOldTopicId(oldTopic.getId());
        TestFactories.alchemy().newAlchemyAnalysis(oldTopic);
        TestFactories.alchemy().newAlchemyAnalysis(newTopic);

        alchemyManager.merge(topicPatch);

        final List<AlchemyAnalysis> alchemyAnalysisList = Repositories.alchemyAnalysis().getAll();
        assertThat(alchemyAnalysisList.size(), is(1));
    }

    private AlchemyManager alchemyManager;
}
