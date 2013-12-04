package com.feelhub.patch;

import com.feelhub.domain.cloudinary.*;
import com.feelhub.domain.topic.Thumbnail;
import com.feelhub.domain.topic.Topic;
import com.feelhub.repositories.*;
import com.feelhub.tools.MongoLinkAwareExecutor;
import com.google.common.util.concurrent.RateLimiter;

import java.util.*;

public class Patch_2013_04_13_1 extends Patch {

    public Patch_2013_04_13_1(final SessionProvider sessionProvider) {
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
                if ((topic.getThumbnail() == null || topic.getThumbnail().isEmpty()) && !topic.getThumbnails().isEmpty() && !topic.getThumbnails().get(0).getOrigin().isEmpty()) {
                    rateLimiter.acquire();
                    final Runnable runnable = new Runnable() {

                        @Override
                        public void run() {
                            createThumbnail(topic.getThumbnails().get(0).getOrigin(), topic.getCurrentId());
                        }
                    };
                    mongoLinkAwareExecutor.execute(runnable);
                    thumbs++;
                }
            } catch (Exception e) {
            }
            counter++;
        }
        System.out.println("end of patch, number of topics : " + counter + " number of thumbs :" + thumbs);
        System.out.println("Cloudinary patch end");
    }

    private void createThumbnail(final String illustration, final UUID topicId) {
        final Cloudinary cloudinary = new Cloudinary(new CloudinaryLink());
        final String thumbnail;
        try {
            thumbnail = cloudinary.getCloudinaryImage(new Thumbnail(illustration));
            final Topic currentTopic = Repositories.topics().getCurrentTopic(topicId);
            currentTopic.setThumbnail(thumbnail);
            currentTopic.getThumbnails().get(0).setCloudinary(thumbnail);
            System.out.println(currentTopic.getCurrentId() + " : " + thumbnail);
        } catch (Exception e) {
            System.out.println("Cloudinary error for source " + illustration);
        }
    }

    @Override
    public Version version() {
        return new Version(1);
    }
}
