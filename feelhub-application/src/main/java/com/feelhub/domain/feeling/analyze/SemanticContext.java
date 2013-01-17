package com.feelhub.domain.feeling.analyze;

import com.feelhub.domain.relation.Related;
import com.feelhub.domain.tag.*;
import com.feelhub.domain.thesaurus.FeelhubLanguage;
import com.feelhub.domain.topic.Topic;
import com.feelhub.repositories.Repositories;
import com.google.common.collect.Maps;

import java.util.*;

public class SemanticContext {

    public SemanticContext(final Topic topic, final FeelhubLanguage language) {
        extractFor(topic.getId(), language);
    }

    public void extractFor(final UUID topicId, final FeelhubLanguage language) {
        final List<Related> relatedList = Repositories.relations().relatedForTopicId(topicId);
        for (final Related related : relatedList) {
            final List<Tag> tags = Repositories.tags().forTopicId(related.getToId());
            for (final Tag tag : tags) {
                for (UUID id : tag.getTopicsIdFor(language)) {
                    if (id.equals(related.getToId())) {
                        knownValues.put(tag.getId(), related.getToId());
                    }
                }
            }
        }
    }

    public Map<String, UUID> getKnownValues() {
        return knownValues;
    }

    Map<String, UUID> knownValues = Maps.newHashMap();
}
