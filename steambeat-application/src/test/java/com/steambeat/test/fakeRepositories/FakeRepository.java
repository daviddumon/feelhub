package com.steambeat.test.fakeRepositories;

import com.google.common.base.Predicate;
import com.google.common.collect.*;
import com.steambeat.domain.*;

import java.util.List;

public class FakeRepository<T extends Entity> implements Repository<T> {

    @Override
    public List<T> getAll() {
        return list;
    }

    @Override
    public void add(final T element) {
        list.add(element);
    }

    @Override
    public void clear() {
        list.clear();
    }

    @Override
    public T get(final Object id) {
        try {
            return Iterables.find(getAll(), parId(id));
        } catch (final Exception e) {
            return null;
        }
    }

    private Predicate<T> parId(final Object id) {
        final Predicate<T> parId = new Predicate<T>() {

            @Override
            public boolean apply(final T element) {
                return element.getId().equals(id);
            }

        };
        return parId;
    }

    @Override
    public boolean exists(final Object id) {
        return get(id) != null;
    }

    private final List<T> list = Lists.newArrayList();
}
