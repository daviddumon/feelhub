package com.feelhub.patch;

import com.feelhub.domain.cloudinary.*;
import com.feelhub.domain.topic.Topic;
import com.feelhub.repositories.*;
import com.feelhub.tools.MongoLinkAwareExecutor;
import com.google.common.util.concurrent.RateLimiter;

import java.util.*;

public class PatchCloudinary extends Patch {

    public PatchCloudinary(final SessionProvider sessionProvider) {
        super(sessionProvider);
    }

    @Override
    protected boolean withBusinessPatch() {
        return true;
    }

    @Override
    protected void doBusinessPatch() {
        System.out.println("Cloudinary patch begin");
        final RateLimiter rateLimiter = RateLimiter.create(1);
        final MongoLinkAwareExecutor mongoLinkAwareExecutor = new MongoLinkAwareExecutor(sessionProvider);
        final List<Topic> topicList = Repositories.topics().getAll();
        int counter = 0;
        int thumbs = 0;
        for (final Topic topic : topicList) {
            try {
                //if (topic.getIllustration() != null && !topic.getIllustration().isEmpty()) {
                //if (topic.getThumbnailLarge() == null || topic.getThumbnailLarge().isEmpty()) {
                //    rateLimiter.acquire();
                //    System.out.println("Creating thumbnails for " + topic.getIllustration());
                //    final Runnable runnable = new Runnable() {
                //
                //        @Override
                //        public void run() {
                //            createThumbnails(topic.getIllustration(), topic.getCurrentId());
                //        }
                //    };
                //    mongoLinkAwareExecutor.execute(runnable);
                //    thumbs++;
                //}
                //}
            } catch (Exception e) {
            }
            counter++;
        }

        System.out.println("end of patch, number of topics : " + counter + " number of thumbs :" + thumbs);
        System.out.println("Cloudinary patch end");
    }

    private void createThumbnails(final String illustration, final UUID topicId) {
        final Cloudinary cloudinary = new Cloudinary(new CloudinaryLink());
        //final CloudinaryThumbnails thumbnails = cloudinary.getThumbnails(illustration);
        final Topic currentTopic = Repositories.topics().getCurrentTopic(topicId);
        //currentTopic.setThumbnailLarge(thumbnails.getThumbnailLarge());
        //currentTopic.setThumbnailMedium(thumbnails.getThumbnailMedium());
        //currentTopic.setThumbnailSmall(thumbnails.getThumbnailSmall());
    }

    @Override
    public Version version() {
        return new Version(1);
    }
}
