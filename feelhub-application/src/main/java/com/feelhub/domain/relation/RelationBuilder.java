package com.feelhub.domain.relation;

import com.feelhub.domain.relation.media.Media;
import com.feelhub.domain.topic.Topic;
import com.feelhub.repositories.Repositories;
import com.google.inject.Inject;

public class RelationBuilder {

    @Inject
    public RelationBuilder(final RelationFactory relationFactory) {
        this.relationFactory = relationFactory;
    }

    public void connectTwoWays(final Topic from, final Topic to) {
        connectTwoWays(from, to, 0.0);
    }

    public void connectTwoWays(final Topic from, final Topic to, final double additionalWeight) {
        connectOneWay(from, to, additionalWeight);
        connectOneWay(to, from, additionalWeight);
    }

    private void connectOneWay(final Topic left, final Topic right, final double additionalWeight) {
        if (!left.equals(right)) {
            final Relation relation = Repositories.relations().lookUpRelated(left.getId(), right.getId());
            if (relation == null) {
                createNewRelation(left, right, additionalWeight);
            } else {
                relation.addWeight(1.0 + additionalWeight);
            }
        }
    }

    private void createNewRelation(final Topic from, final Topic to, final double additionalWeight) {
        if (to.getType().isMedia()) {
            createMediaRelation(from, to);
        } else {
            createRelatedRelation(from, to, additionalWeight);
        }
    }

    private void createMediaRelation(final Topic from, final Topic to) {
        final Media media = relationFactory.newMedia(from, to);
        Repositories.relations().add(media);
    }

    private void createRelatedRelation(final Topic from, final Topic to, final double additionalWeight) {
        final Relation relation = relationFactory.newRelated(from, to, additionalWeight);
        Repositories.relations().add(relation);
    }

    private final RelationFactory relationFactory;
}
