package com.steambeat.domain;

import java.util.List;

public interface Repository<T extends Entity> {

    List<T> getAll();

    void add(T x);

    void clear();

    T get(Object id);

    boolean exists(Object id);
}
