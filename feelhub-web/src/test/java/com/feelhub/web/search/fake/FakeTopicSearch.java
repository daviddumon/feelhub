package com.feelhub.web.search.fake;

import com.feelhub.domain.thesaurus.FeelhubLanguage;
import com.feelhub.domain.topic.Topic;
import com.feelhub.repositories.*;
import com.feelhub.web.search.*;
import com.google.common.base.Predicate;
import com.google.common.collect.*;
import com.google.inject.Inject;
import org.mongolink.domain.criteria.Order;

import java.util.*;

public class FakeTopicSearch extends TopicSearch {

    @Inject
    public FakeTopicSearch(final SessionProvider provider) {
        super(provider);
    }

    @Override
    public List<Topic> execute() {
        return topics;
    }

    @Override
    public FakeTopicSearch withSkip(final int skipValue) {
        topics = Lists.newArrayList(Iterables.skip(topics, skipValue));
        return this;
    }

    @Override
    public FakeTopicSearch withLimit(final int limitValue) {
        topics = Lists.newArrayList(Iterables.limit(topics, limitValue));
        return this;
    }

    @Override
    public FakeTopicSearch withSort(final String sortField, final Order sortOrder) {
        return this;
    }

    @Override
    public FakeTopicSearch withTopicId(final UUID topicId) {
        topics = Lists.newArrayList(Iterables.filter(topics, new Predicate<Topic>() {

            @Override
            public boolean apply(final Topic topic) {
                if (topic.getCurrentId().equals(topicId)) {
                    return true;
                }
                return false;
            }
        }));
        return this;
    }

    @Override
    public FakeTopicSearch withFeelings() {
        topics = Lists.newArrayList(Iterables.filter(topics, new Predicate<Topic>() {

            @Override
            public boolean apply(final Topic topic) {
                if (topic.getHasFeelings()) {
                    return true;
                }
                return false;
            }
        }));
        return this;
    }

    @Override
    public FakeTopicSearch withLanguages(final List<FeelhubLanguage> languages) {
        return this;
    }

    List<Topic> topics = Repositories.topics().getAll();
}
