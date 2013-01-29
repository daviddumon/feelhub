package com.feelhub.domain.media;

import com.feelhub.domain.related.*;
import com.feelhub.domain.topic.Topic;
import com.feelhub.repositories.Repositories;
import com.google.inject.Inject;

public class MediaBuilder {

    @Inject
    public MediaBuilder(final MediaFactory mediaFactory, final RelatedFactory relatedFactory) {
        this.mediaFactory = mediaFactory;
        this.relatedFactory = relatedFactory;
    }

    public void connectTwoWays(final Topic from, final Topic to) {
        if (!from.equals(to)) {
            connectOneWayWithMedia(from, to);
            connectOneWayWithRelated(to, from);
        }
    }

    private void connectOneWayWithMedia(final Topic from, final Topic to) {
        final Media media = Repositories.medias().lookUp(from.getId(), to.getId());
        if (media == null) {
            createNewMedia(from, to);
        }
    }

    private void createNewMedia(final Topic from, final Topic to) {
        final Media media = mediaFactory.newMedia(from, to);
        Repositories.medias().add(media);
    }

    private void connectOneWayWithRelated(final Topic from, final Topic to) {
        final Related related = Repositories.related().lookUp(from.getId(), to.getId());
        if (related == null) {
            createNewRelated(from, to);
        } else {
            related.addWeight(1.0);
        }
    }

    private void createNewRelated(final Topic from, final Topic to) {
        final Related related = relatedFactory.newRelated(from, to, 0.0);
        Repositories.related().add(related);
    }

    private final MediaFactory mediaFactory;
    private RelatedFactory relatedFactory;
}
