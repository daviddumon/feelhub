package com.feelhub.web.search;

import org.mongolink.domain.criteria.Order;

import java.util.*;

public interface Search<T> {

    List<T> execute();

    Search<T> withSkip(int skipValue);

    Search<T> withLimit(int limitValue);

    Search<T> withSort(String sortField, Order sortOrder);

    Search<T> withTopicId(final UUID topicId);
}
