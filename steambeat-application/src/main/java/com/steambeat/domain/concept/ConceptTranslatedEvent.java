package com.steambeat.domain.concept;

import com.google.common.collect.Lists;
import com.steambeat.domain.eventbus.DomainEvent;
import com.steambeat.domain.keyword.Keyword;

import java.util.Collection;
import java.util.concurrent.CopyOnWriteArrayList;

public class ConceptTranslatedEvent extends DomainEvent {

    @Override
    public String toString() {
        final StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(date.toString());
        stringBuilder.append(" - ");
        stringBuilder.append("ConceptTranslatedEvent ");
        stringBuilder.append(keywords.size());
        return stringBuilder.toString();
    }

    public CopyOnWriteArrayList<Keyword> getKeywords() {
        return keywords;
    }

    public void addAllAbsent(Collection<? extends Keyword> newKeywords) {
        keywords.addAllAbsent(newKeywords);
    }

    public void addIfAbsent(final Keyword keyword) {
        this.keywords.addIfAbsent(keyword);
    }

    private CopyOnWriteArrayList<Keyword> keywords = Lists.newCopyOnWriteArrayList();
}
