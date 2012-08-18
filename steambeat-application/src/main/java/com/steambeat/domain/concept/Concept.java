package com.steambeat.domain.concept;

import com.google.common.collect.Lists;
import com.steambeat.domain.keyword.Keyword;

import java.util.concurrent.CopyOnWriteArrayList;

public class Concept {

    public Concept() {
    }

    public CopyOnWriteArrayList<Keyword> getKeywords() {
        return keywords;
    }

    public void addIfAbsent(final Keyword keyword) {
        this.keywords.addIfAbsent(keyword);
    }

    private CopyOnWriteArrayList<Keyword> keywords = Lists.newCopyOnWriteArrayList();
}
