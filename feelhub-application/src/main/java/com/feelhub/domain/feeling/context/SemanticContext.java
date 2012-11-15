package com.feelhub.domain.feeling.context;

import com.feelhub.domain.keyword.Keyword;
import com.feelhub.domain.relation.Relation;
import com.feelhub.domain.thesaurus.FeelhubLanguage;
import com.feelhub.repositories.Repositories;
import com.google.common.collect.Lists;

import java.util.List;

public class SemanticContext {

    public void extractFor(final String value, final FeelhubLanguage language) {
        final Keyword keyword = Repositories.keywords().forValueAndLanguage(value, language);
        final List<Relation> relations = Repositories.relations().forTopicId(keyword.getTopicId());
        for (Relation relation : relations) {
            final Keyword contextKeyword = Repositories.keywords().forTopicIdAndLanguage(relation.getToId(), language);
            if (contextKeyword != null) {
                values.add(contextKeyword.getValue());
            }
        }
    }

    public List<String> getValues() {
        return values;
    }

    List<String> values = Lists.newArrayList();
}
