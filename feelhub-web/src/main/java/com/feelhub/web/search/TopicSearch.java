package com.feelhub.web.search;

import com.feelhub.domain.thesaurus.FeelhubLanguage;
import com.feelhub.domain.topic.Topic;
import com.feelhub.repositories.SessionProvider;
import com.google.common.collect.Lists;
import com.google.inject.Inject;
import org.mongolink.domain.criteria.*;

import java.util.*;

public class TopicSearch implements Search<Topic> {

    @Inject
    public TopicSearch(final SessionProvider provider) {
        criteria = provider.get().createCriteria(Topic.class);
        criteria.add(Restrictions.notEquals("__discriminator", "WorldTopic"));
    }

    @Override
    public List<Topic> execute() {
        return criteria.list();
    }

    @Override
    public TopicSearch withSkip(final int skipValue) {
        criteria.skip(skipValue);
        return this;
    }

    @Override
    public TopicSearch withLimit(final int limitValue) {
        criteria.limit(limitValue);
        return this;
    }

    @Override
    public TopicSearch withSort(final String sortField, final Order sortOrder) {
        criteria.sort(sortField, sortOrder);
        return this;
    }

    @Override
    public TopicSearch withTopicId(final UUID topicId) {
        criteria.add(Restrictions.equals("_id", topicId));
        return this;
    }

    public TopicSearch withCurrentId(final UUID currentId) {
        criteria.add(Restrictions.equals("currentId", currentId));
        return this;
    }

    public TopicSearch withFeelings() {
        criteria.add(Restrictions.equals("hasFeelings", true));
        return this;
    }

    public TopicSearch withLanguages(final List<FeelhubLanguage> languages) {
        final List<String> codes = Lists.newArrayList();
        for (final FeelhubLanguage language : languages) {
            codes.add(language.getCode());
        }
        criteria.add(Restrictions.in("languageCode", codes));
        return this;
    }

    public TopicSearch withUserId(UUID userId) {
        criteria.add(Restrictions.equals("userId", userId));
        return this;
    }

    private final Criteria criteria;
}
