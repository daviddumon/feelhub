package com.feelhub.domain.alchemy;

import com.feelhub.domain.topic.TopicPatch;
import com.feelhub.repositories.Repositories;

import java.util.*;

public class AlchemyManager {

    public void merge(final TopicPatch topicPatch) {
        mergeAlchemyEntities(topicPatch);
        removeDuplicateAlchemyEntities(topicPatch.getNewTopicId());
        mergeAlchemyAnalysis(topicPatch);
        removeDuplicateAlchemyAnalysis(topicPatch.getNewTopicId());
    }

    private void mergeAlchemyEntities(final TopicPatch topicPatch) {
        for (final UUID oldTopicId : topicPatch.getOldTopicIds()) {
            final List<AlchemyEntity> entities = Repositories.alchemyEntities().forTopicId(oldTopicId);
            if (!entities.isEmpty()) {
                for (final AlchemyEntity entity : entities) {
                    entity.setNewTopicId(topicPatch.getNewTopicId());
                }
            }
        }
    }

    private void removeDuplicateAlchemyEntities(final UUID newTopicId) {
        final List<AlchemyEntity> entities = Repositories.alchemyEntities().forTopicId(newTopicId);
        if (entities.size() > 1) {
            for (int i = 1; i < entities.size(); i++) {
                Repositories.alchemyEntities().delete(entities.get(i));
            }
        }
    }

    private void mergeAlchemyAnalysis(final TopicPatch topicPatch) {
        for (final UUID oldTopicId : topicPatch.getOldTopicIds()) {
            final List<AlchemyAnalysis> alchemyAnalysisList = Repositories.alchemyAnalysis().forTopicId(oldTopicId);
            if (!alchemyAnalysisList.isEmpty()) {
                for (final AlchemyAnalysis analysis : alchemyAnalysisList) {
                    analysis.setNewTopicId(topicPatch.getNewTopicId());
                }
            }
        }
    }

    private void removeDuplicateAlchemyAnalysis(final UUID newTopicId) {
        final List<AlchemyAnalysis> alchemyAnalysisList = Repositories.alchemyAnalysis().forTopicId(newTopicId);
        if (alchemyAnalysisList.size() > 1) {
            for (int i = 1; i < alchemyAnalysisList.size(); i++) {
                Repositories.alchemyAnalysis().delete(alchemyAnalysisList.get(i));
            }
        }
    }
}
