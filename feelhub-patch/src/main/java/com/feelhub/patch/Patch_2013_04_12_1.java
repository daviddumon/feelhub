package com.feelhub.patch;

import com.feelhub.domain.cloudinary.*;
import com.feelhub.domain.topic.Topic;
import com.feelhub.domain.topic.http.HttpTopic;
import com.feelhub.repositories.*;

import java.util.List;

public class Patch_2013_04_12_1 extends Patch {

    public Patch_2013_04_12_1(final SessionProvider sessionProvider) {
        super(sessionProvider);
    }

    @Override
    protected boolean withBusinessPatch() {
        return true;
    }

    @Override
    protected void doBusinessPatch() {
        System.out.println("Cloudinary patch begin " + this.getClass().getSimpleName());
        final List<Topic> topics = Repositories.topics().getAll();
        for (Topic topic : topics) {
            try {
                final HttpTopic httpTopic = (HttpTopic) topic;
                if (httpTopic.getThumbnail().isEmpty() && httpTopic.getIllustration().isEmpty() && !httpTopic.getUris().isEmpty()) {
                    createThumbnail(httpTopic);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        System.out.println("Cloudinary patch end");
    }

    private void createThumbnail(final HttpTopic httpTopic) {
        System.out.println("Thumbnail creation");
        final Cloudinary cloudinary = new Cloudinary(new CloudinaryLink());
        try {
            httpTopic.setIllustration("http://ec2-107-22-105-164.compute-1.amazonaws.com:3000/?url=" + httpTopic.getUris().get(0).toString() + "&clipRect={%22top%22:0,%22left%22:0,%22width%22:1024,%22height%22:600}");
            final String thumbnail = cloudinary.getThumbnail(httpTopic.getIllustration());
            httpTopic.setThumbnail(thumbnail);
            System.out.println(httpTopic.getCurrentId() + " : " + thumbnail);
        } catch (Exception e) {
            System.out.println("Cloudinary error for source " + httpTopic.getIllustration());
        }
    }

    @Override
    public Version version() {
        return new Version(1);
    }
}
