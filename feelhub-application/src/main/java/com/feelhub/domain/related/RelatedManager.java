package com.feelhub.domain.related;

import com.feelhub.domain.topic.TopicPatch;
import com.feelhub.repositories.Repositories;

import java.util.*;

public class RelatedManager {

    public void merge(final TopicPatch topicPatch) {
        for (final UUID oldTopicId : topicPatch.getOldTopicIds()) {
            final List<Related> relatedList = Repositories.related().containingTopicId(oldTopicId);
            if (!relatedList.isEmpty()) {
                migrateRelated(topicPatch.getNewTopicId(), oldTopicId, relatedList);
            }
        }
    }

    private void migrateRelated(final UUID newTopicId, final UUID oldTopicId, final List<Related> relatedList) {
        for (final Related related : relatedList) {
            checkFromId(newTopicId, oldTopicId, related);
            checkToId(newTopicId, oldTopicId, related);
        }
    }

    private void checkFromId(final UUID newTopicId, final UUID oldTopicId, final Related related) {
        if (related.getFromId().equals(oldTopicId)) {
            if (related.getToId().equals(newTopicId)) {
                Repositories.related().delete(related);
            } else {
                final Related relatedFound = Repositories.related().lookUp(newTopicId, related.getToId());
                if (relatedFound != null) {
                    relatedFound.addWeight(related.getWeight());
                    Repositories.related().delete(related);
                } else {
                    related.setFromId(newTopicId);
                }
            }
        }
    }

    private void checkToId(final UUID newTopicId, final UUID oldTopicId, final Related related) {
        if (related.getToId().equals(oldTopicId)) {
            if (related.getFromId().equals(newTopicId)) {
                Repositories.related().delete(related);
            } else {
                final Related relatedFound = Repositories.related().lookUp(related.getFromId(), newTopicId);
                if (relatedFound != null) {
                    relatedFound.addWeight(related.getWeight());
                    Repositories.related().delete(related);
                } else {
                    related.setToId(newTopicId);
                }
            }
        }
    }
}
