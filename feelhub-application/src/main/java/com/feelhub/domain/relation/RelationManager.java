package com.feelhub.domain.relation;

import com.feelhub.domain.topic.TopicPatch;
import com.feelhub.repositories.Repositories;

import java.util.*;

public class RelationManager {

    public void merge(final TopicPatch topicPatch) {
        for (final UUID oldTopicId : topicPatch.getOldTopicIds()) {
            final List<Relation> relations = Repositories.relations().containingTopicId(oldTopicId);
            if (!relations.isEmpty()) {
                migrateRelations(topicPatch.getNewTopicId(), oldTopicId, relations);
            }
        }
    }

    private void migrateRelations(final UUID newTopicId, final UUID oldTopicId, final List<Relation> relations) {
        for (final Relation relation : relations) {
            checkFromId(newTopicId, oldTopicId, relation);
            checkToId(newTopicId, oldTopicId, relation);
        }
    }

    private void checkFromId(final UUID newTopicId, final UUID oldTopicId, final Relation relation) {
        if (relation.getFromId().equals(oldTopicId)) {
            if (relation.getToId().equals(newTopicId)) {
                Repositories.relations().delete(relation);
            } else {
                final Relation relationFound = Repositories.relations().lookUp(newTopicId, relation.getToId());
                if (relationFound != null) {
                    relationFound.addWeight(relation.getWeight());
                    Repositories.relations().delete(relation);
                } else {
                    relation.setFromId(newTopicId);
                }
            }
        }
    }

    private void checkToId(final UUID newTopicId, final UUID oldTopicId, final Relation relation) {
        if (relation.getToId().equals(oldTopicId)) {
            if (relation.getFromId().equals(newTopicId)) {
                Repositories.relations().delete(relation);
            } else {
                final Relation relationFound = Repositories.relations().lookUp(relation.getFromId(), newTopicId);
                if (relationFound != null) {
                    relationFound.addWeight(relation.getWeight());
                    Repositories.relations().delete(relation);
                } else {
                    relation.setToId(newTopicId);
                }
            }
        }
    }
}
