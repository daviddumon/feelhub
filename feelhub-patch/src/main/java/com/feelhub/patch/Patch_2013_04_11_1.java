package com.feelhub.patch;

import com.feelhub.domain.cloudinary.*;
import com.feelhub.domain.media.Media;
import com.feelhub.domain.topic.Topic;
import com.feelhub.repositories.*;
import com.feelhub.tools.MongoLinkAwareExecutor;
import com.google.common.util.concurrent.RateLimiter;

import java.util.*;

public class Patch_2013_04_11_1 extends Patch {

    public Patch_2013_04_11_1(final SessionProvider sessionProvider) {
        super(sessionProvider);
    }

    @Override
    protected boolean withBusinessPatch() {
        return true;
    }

    @Override
    protected void doBusinessPatch() {
        System.out.println("Cloudinary patch begin");
        final RateLimiter rateLimiter = RateLimiter.create(2);
        final MongoLinkAwareExecutor mongoLinkAwareExecutor = new MongoLinkAwareExecutor(sessionProvider);
        final List<Topic> topicList = Repositories.topics().getAll();
        int counter = 0;
        int thumbs = 0;
        for (final Topic topic : topicList) {
            try {
                if (topic.getIllustration() != null && !topic.getIllustration().isEmpty()) {
                    if (topic.getThumbnail() == null || topic.getThumbnail().isEmpty()) {
                        rateLimiter.acquire();
                        System.out.println("Creating thumbnail for " + topic.getIllustration());
                        final Runnable runnable = new Runnable() {

                            @Override
                            public void run() {
                                createThumbnail(topic.getIllustration(), topic.getCurrentId());
                            }
                        };
                        mongoLinkAwareExecutor.execute(runnable);
                        thumbs++;
                    }
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
            thumbnail = cloudinary.getCloudinaryImage(illustration);
            final Topic currentTopic = Repositories.topics().getCurrentTopic(topicId);
            currentTopic.setThumbnail(thumbnail);
            System.out.println(currentTopic.getCurrentId() + " : " + thumbnail + " (topic)");
            setThumbnailForMediaLinked(currentTopic, thumbnail);
        } catch (Exception e) {
            System.out.println("Cloudinary error for source " + illustration);
        }
    }

    private void setThumbnailForMediaLinked(final Topic currentTopic, final String thumbnail) {
        final List<Media> medias = Repositories.medias().containingTopicId(currentTopic.getCurrentId());
        for (Media media : medias) {
            if (!media.getFromId().equals(currentTopic.getCurrentId())) {
                final Topic fromId = Repositories.topics().getCurrentTopic(media.getFromId());
                fromId.setThumbnail(thumbnail);
                System.out.println(fromId.getCurrentId() + " : " + thumbnail + " (media)");
            }
        }
    }

    @Override
    public Version version() {
        return new Version(1);
    }
}
