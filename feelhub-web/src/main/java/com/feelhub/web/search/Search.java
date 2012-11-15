package com.feelhub.web.search;

import java.util.*;

public interface Search<T> {

    List<T> execute();

    Search<T> withSkip(int skip);

    Search<T> withLimit(int limit);

    Search<T> withSort(String sortField, int sortOrder);

    Search<T> withTopicId(final UUID topicId);

    int NATURAL_ORDER = 1;
    int REVERSE_ORDER = -1;
}
