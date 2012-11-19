package com.feelhub.domain.feeling.context;

import com.feelhub.domain.relation.Relation;
import com.feelhub.domain.tag.Tag;
import com.feelhub.domain.thesaurus.FeelhubLanguage;
import com.feelhub.repositories.Repositories;
import com.google.common.collect.Lists;

import java.util.List;

public class SemanticContext {

    public void extractFor(final String value, final FeelhubLanguage language) {
        final Tag tag = Repositories.keywords().forValueAndLanguage(value, language);
        final List<Relation> relations = Repositories.relations().forTopicId(tag.getTopicId());
        for (Relation relation : relations) {
            final Tag contextTag = Repositories.keywords().forTopicIdAndLanguage(relation.getToId(), language);
            if (contextTag != null) {
                values.add(contextTag.getValue());
            }
        }
    }

    public List<String> getValues() {
        return values;
    }

    List<String> values = Lists.newArrayList();
}
