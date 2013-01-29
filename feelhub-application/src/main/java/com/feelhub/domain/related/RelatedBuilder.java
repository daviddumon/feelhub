package com.feelhub.domain.related;

import com.feelhub.domain.topic.Topic;
import com.feelhub.repositories.Repositories;
import com.google.inject.Inject;

public class RelatedBuilder {

    @Inject
    public RelatedBuilder(final RelatedFactory relatedFactory) {
        this.relatedFactory = relatedFactory;
    }

    public void connectTwoWays(final Topic from, final Topic to) {
        connectTwoWays(from, to, 0.0);
    }

    public void connectTwoWays(final Topic from, final Topic to, final double additionalWeight) {
        if (!from.equals(to)) {
            connectOneWay(from, to, additionalWeight);
            connectOneWay(to, from, additionalWeight);
        }
    }

    private void connectOneWay(final Topic from, final Topic to, final double additionalWeight) {
        final Related related = Repositories.related().lookUp(from.getId(), to.getId());
        if (related == null) {
            createRelatedRelation(from, to, additionalWeight);
        } else {
            related.addWeight(1.0 + additionalWeight);
        }
    }

    private void createRelatedRelation(final Topic from, final Topic to, final double additionalWeight) {
        final Related related = relatedFactory.newRelated(from, to, additionalWeight);
        Repositories.related().add(related);
    }

    private final RelatedFactory relatedFactory;
}
