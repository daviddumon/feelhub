package com.feelhub.web.search;

import org.mongolink.domain.criteria.Order;

import java.util.*;

public interface Search<T> {

    List<T> execute();

    Search<T> withSkip(int skip);

    Search<T> withLimit(int limit);

    Search<T> withSort(String sortField, Order sortOrder);

    Search<T> withTopicId(final UUID topicId);
}
