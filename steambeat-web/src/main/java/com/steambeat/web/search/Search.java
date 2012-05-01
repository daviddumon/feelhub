package com.steambeat.web.search;

import java.util.List;

@SuppressWarnings("unchecked")
public interface Search<T> {

    List<T> execute();

    Search<T> withSkip(int skip);

    Search<T> withLimit(int limit);

    Search<T> withSort(String sortField, int sortOrder);

    int NATURAL_ORDER = 1;
    int REVERSE_ORDER = -1;
}
