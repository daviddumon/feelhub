package com.feelhub.domain.relation;

import com.feelhub.domain.topic.Topic;

public class RelationFactory {

    public Related newRelated(final Topic from, final Topic to, final double additionalWeight) {
        return new Related(from.getId(), to.getId(), 1.0 + additionalWeight);
    }

    public Media newMedia(final Topic from, final Topic to) {
        return new Media(from.getId(), to.getId());
    }
}
