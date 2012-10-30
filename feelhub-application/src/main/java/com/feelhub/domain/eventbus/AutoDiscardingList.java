package com.feelhub.domain.eventbus;

import java.util.LinkedList;

public class AutoDiscardingList<E> extends LinkedList<E> {

    public AutoDiscardingList(final int maximumSize) {
        this.maximumSize = maximumSize;
    }

    @Override
    public synchronized boolean add(final E e) {
        if (size() >= maximumSize) {
            pollLast();
        }
        return super.add(e);
    }

    private final int maximumSize;
}
