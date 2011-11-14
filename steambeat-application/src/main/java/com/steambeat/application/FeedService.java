package com.steambeat.application;

import com.google.inject.Inject;
import com.steambeat.repositories.Repositories;
import com.steambeat.domain.subject.feed.*;

public class FeedService {

    @Inject
    public FeedService(final AssociationService associationService, final FeedFactory feedFactory) {
        this.associationService = associationService;
        this.feedFactory = feedFactory;
    }

    public Feed lookUpFeed(final Uri uri) {
        final Association association = associationService.lookUp(uri.toString());
        final Feed feed = Repositories.feeds().get(association.getCanonicalUri());
        if (feed == null) {
            throw new FeedNotYetCreatedException();
        }
        return feed;
    }

    public Feed addFeed(final Uri uri) {
        final Association association = associationService.lookUp(uri.toString());
        final Feed result = feedFactory.buildFeed(association);
        Repositories.feeds().add(result);
        return result;
    }

    private final AssociationService associationService;
    private final FeedFactory feedFactory;
}
