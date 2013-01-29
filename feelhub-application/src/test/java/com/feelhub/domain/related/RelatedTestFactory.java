package com.feelhub.domain.related;

import com.feelhub.repositories.Repositories;
import com.feelhub.test.TestFactories;

import java.util.UUID;

public class RelatedTestFactory {

    public Related newRelated() {
        return newRelated(TestFactories.topics().newCompleteRealTopic().getId(), TestFactories.topics().newCompleteRealTopic().getId());
    }

    public Related newRelated(final UUID fromId, final UUID toId) {
        return newRelated(fromId, toId, 1);
    }

    private Related newRelated(final UUID fromId, final UUID toId, final int weight) {
        final Related related = new Related(fromId, toId, 1.0);
        related.setWeight(weight);
        Repositories.related().add(related);
        return related;
    }

    public void newRelatedList(final int quantity) {
        newRelatedList(quantity, TestFactories.topics().newCompleteRealTopic().getId());
    }

    public void newRelatedList(final int quantity, final UUID fromid) {
        for (int i = 0; i < quantity; i++) {
            createARelated(fromid, i);
        }
    }

    private void createARelated(final UUID fromId, final int weight) {
        newRelated(fromId, TestFactories.topics().newCompleteRealTopic().getId(), weight);
    }
}
