package com.feelhub.web.search.fake;

import com.feelhub.domain.topic.Topic;
import com.feelhub.repositories.SessionProvider;
import com.feelhub.web.search.*;
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
        return super.execute();
    }

    @Override
    public Search<Topic> withSkip(final int skipValue) {
        return super.withSkip(skipValue);
    }

    @Override
    public Search<Topic> withLimit(final int limitValue) {
        return super.withLimit(limitValue);
    }

    @Override
    public Search<Topic> withSort(final String sortField, final Order sortOrder) {
        return super.withSort(sortField, sortOrder);
    }

    @Override
    public Search<Topic> withTopicId(final UUID topicId) {
        return super.withTopicId(topicId);
    }
}
