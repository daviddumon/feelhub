package com.feelhub.domain.related;

import com.feelhub.domain.topic.Topic;

public class RelatedFactory {

    public Related newRelated(final Topic from, final Topic to, final double additionalWeight) {
        return new Related(from.getId(), to.getId(), 1.0 + additionalWeight);
    }
}
