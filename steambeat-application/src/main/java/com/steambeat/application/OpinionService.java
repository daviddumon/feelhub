package com.steambeat.application;

import com.steambeat.domain.opinion.Feeling;
import com.steambeat.domain.subject.feed.Feed;
import com.steambeat.repositories.Repositories;

public class OpinionService {

    public void addOpinion(Feed feed, Feeling feeling, String text) {
        Repositories.opinions().add(feed.createOpinion(text, feeling));
    }
}
