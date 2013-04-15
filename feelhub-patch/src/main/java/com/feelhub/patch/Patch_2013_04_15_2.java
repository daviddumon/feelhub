package com.feelhub.patch;

import com.feelhub.domain.bing.BingLink;
import com.feelhub.domain.cloudinary.*;
import com.feelhub.domain.topic.*;
import com.feelhub.domain.topic.http.uri.*;
import com.feelhub.repositories.*;
import com.feelhub.tools.MongoLinkAwareExecutor;
import com.google.common.collect.*;
import com.google.common.util.concurrent.RateLimiter;

import java.io.IOException;
import java.util.*;

public class Patch_2013_04_15_2 extends Patch {

    public Patch_2013_04_15_2(final SessionProvider sessionProvider) {
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
        while (counter < topicList.size() && thumbs < 50) {
            try {
                final Topic topic = topicList.get(counter);
                if (topic.getThumbnail() == null || topic.getThumbnail().isEmpty()) {
                    rateLimiter.acquire();
                    bing(topic.getCurrentId());
                    thumbs++;
                }
            } catch (Exception e) {
            }
            counter++;
        }
        System.out.println("end of patch, number of topics : " + counter + " number of thumbs :" + thumbs);
        System.out.println("Cloudinary patch end");
    }

    private void bing(final UUID topicId) {
        final Topic topic = Repositories.topics().getCurrentTopic(topicId);
        final BingLink bingLink = new BingLink();
        System.out.println("BING: " + Iterables.get(topic.getNames().values(), 0));
        final List<String> illustrations = bingLink.getIllustrations(Iterables.get(topic.getNames().values(), 0));
        final Thumbnail thumbnail = findThumbnail(illustrations);
        final Map<String, String> params = Maps.newHashMap();
        params.put("format", "jpg");
        params.put("transformation", "w_564,h_348,c_fill,g_face,q_75");
        params.put("file", thumbnail.getOrigin());
        try {
            final CloudinaryLink cloudinaryLink = new CloudinaryLink();
            final String cloudinaryImage = cloudinaryLink.getIllustration(params);
            thumbnail.setCloudinary(cloudinaryImage);
            topic.addThumbnail(thumbnail);
            topic.setThumbnail(cloudinaryImage);
        } catch (IOException e) {
            throw new CloudinaryException();
        }
    }

    private Thumbnail findThumbnail(final List<String> illustrations) {
        int i = 0;
        while (i < illustrations.size()) {
            final String illustration = illustrations.get(i++);
            try {
                final UriResolver uriResolver = new UriResolver();
                final ResolverResult resolverResult = uriResolver.resolve(new Uri(illustration));
                final Thumbnail thumbnail = new Thumbnail();
                thumbnail.setOrigin(getCanonical(resolverResult).toString());
                return thumbnail;
            } catch (UriException e) {
            } catch (TopicException e) {
            } catch (CloudinaryException e) {
            }
        }
        return null;
    }

    private Uri getCanonical(final ResolverResult resolverResult) {
        return resolverResult.getPath().get(resolverResult.getPath().size() - 1);
    }

    @Override
    public Version version() {
        return new Version(1);
    }
}
