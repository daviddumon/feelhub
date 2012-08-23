package com.steambeat.domain.concept;

import com.google.common.collect.Lists;
import com.steambeat.domain.eventbus.DomainEvent;
import com.steambeat.domain.keyword.Keyword;

import java.util.concurrent.CopyOnWriteArrayList;

public class ConceptEvent extends DomainEvent {

    @Override
    public String toString() {
        final StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(date.toString());
        stringBuilder.append(" - ");
        stringBuilder.append("ConceptEvent ");
        stringBuilder.append(keywords.size());
        return stringBuilder.toString();
    }

    public CopyOnWriteArrayList<Keyword> getKeywords() {
        return keywords;
    }

    public void addIfAbsent(final Keyword keyword) {
        this.keywords.addIfAbsent(keyword);
    }

    private CopyOnWriteArrayList<Keyword> keywords = Lists.newCopyOnWriteArrayList();
}
