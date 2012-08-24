package com.steambeat.domain.reference;

import com.google.common.collect.Lists;
import com.steambeat.domain.eventbus.DomainEvent;
import com.steambeat.domain.keyword.Keyword;

import java.util.Collection;
import java.util.concurrent.CopyOnWriteArrayList;

public abstract class ReferencesToChangeEvent extends DomainEvent {

    public CopyOnWriteArrayList<Keyword> getKeywords() {
        return keywords;
    }

    public void addAllAbsent(Collection<? extends Keyword> newKeywords) {
        keywords.addAllAbsent(newKeywords);
    }

    public void addIfAbsent(final Keyword keyword) {
        this.keywords.addIfAbsent(keyword);
    }

    protected CopyOnWriteArrayList<Keyword> keywords = Lists.newCopyOnWriteArrayList();
}
