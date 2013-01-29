package com.feelhub.web.search.fake;

import com.feelhub.domain.media.Media;
import com.feelhub.repositories.*;
import com.feelhub.web.search.MediaSearch;
import com.google.common.base.Predicate;
import com.google.common.collect.*;
import com.google.inject.Inject;

import java.util.*;

public class FakeMediaSearch extends MediaSearch {

    @Inject
    public FakeMediaSearch(final SessionProvider provider) {
        super(provider);
    }

    @Override
    public List<Media> execute() {
        return mediaList;
    }

    @Override
    public MediaSearch withSkip(final int skip) {
        mediaList = Lists.newArrayList(Iterables.skip(mediaList, skip));
        return this;
    }

    @Override
    public MediaSearch withLimit(final int limit) {
        mediaList = Lists.newArrayList(Iterables.limit(mediaList, limit));
        return this;
    }

    @Override
    public MediaSearch withTopicId(final UUID topicId) {
        mediaList = Lists.newArrayList(Iterables.filter(mediaList, new Predicate<Media>() {

            @Override
            public boolean apply(final Media media) {
                if (media.getFromId().equals(topicId)) {
                    return true;
                }
                return false;
            }
        }));
        return this;
    }

    private List<Media> mediaList = Repositories.medias().getAll();
}
