package com.feelhub.domain.feeling.analyze;

import com.feelhub.domain.relation.Relation;
import com.feelhub.domain.tag.Tag;
import com.feelhub.domain.topic.Topic;
import com.feelhub.repositories.Repositories;
import com.google.common.collect.Lists;

import java.util.List;

public class SemanticContext {

    public SemanticContext(final Topic topic) {

    }

    public void extractFor(final Topic topic) {
        final List<Relation> relations = Repositories.relations().forTopicId(topic.getId());
        for (final Relation relation : relations) {
            final List<Tag> tags = Repositories.tags().forTopicId(relation.getToId());
            for (final Tag tag : tags) {
                if (!values.contains(tag.getId())) {
                    values.add(tag.getId());
                }
            }
        }
    }

    public List<String> getValues() {
        return values;
    }

    List<String> values = Lists.newArrayList();
}
