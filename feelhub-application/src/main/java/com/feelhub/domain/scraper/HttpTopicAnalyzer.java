package com.feelhub.domain.scraper;

import com.feelhub.domain.cloudinary.*;
import com.feelhub.domain.eventbus.*;
import com.feelhub.domain.topic.http.*;
import com.feelhub.repositories.Repositories;
import com.google.common.collect.Lists;
import com.google.common.eventbus.*;
import com.google.common.util.concurrent.RateLimiter;
import com.google.inject.Inject;
import org.slf4j.*;

import java.util.*;

public class HttpTopicAnalyzer {

    @Inject
    public HttpTopicAnalyzer(final Scraper scraper, final Cloudinary cloudinary) {
        this.scraper = scraper;
        this.cloudinary = cloudinary;
        DomainEventBus.INSTANCE.register(this);
        rateLimiter = RateLimiter.create(1.0);
    }

    @Subscribe
    @Synchronize
    @AllowConcurrentEvents
    public void onHttpTopicCreated(final HttpTopicCreatedEvent event) {
        rateLimiter.acquire();
        analyze(event.topicId);
    }

    public List<String> analyze(final UUID topicId) {
        final HttpTopic httpTopic = Repositories.topics().getHttpTopic(topicId);
        LOGGER.debug("Analysing topic : {}", httpTopic);
        if (HttpTopicType.Website.equals(httpTopic.getType())) {
            return scrap(httpTopic);
        }
        return Lists.newArrayList();
    }

    private List<String> scrap(final HttpTopic httpTopic) {
        final ScrapedInformation scrapedInformation = scraper.scrap(getCanonical(httpTopic));
        httpTopic.addDescription(scrapedInformation.getLanguage(), scrapedInformation.getDescription());
        setName(httpTopic, scrapedInformation);
        httpTopic.setType(scrapedInformation.getType());
        httpTopic.setOpenGraphType(scrapedInformation.getOpenGraphType());
        if (!scrapedInformation.getImages().isEmpty()) {
            httpTopic.setIllustration(scrapedInformation.getImages().get(0));
            getThumbnails(httpTopic);
        }
        return getMedias(scrapedInformation);
    }

    private void setName(final HttpTopic httpTopic, final ScrapedInformation scrapedInformation) {
        if (scrapedInformation.getName().isEmpty()) {
            httpTopic.addName(scrapedInformation.getLanguage(), getCanonical(httpTopic));
        } else {
            httpTopic.addName(scrapedInformation.getLanguage(), scrapedInformation.getName());
        }
    }

    private void getThumbnails(final HttpTopic httpTopic) {
        final CloudinaryThumbnails thumbnails = cloudinary.getThumbnails(httpTopic.getIllustration());
        httpTopic.setThumbnailLarge(thumbnails.getThumbnailLarge());
        httpTopic.setThumbnailMedium(thumbnails.getThumbnailMedium());
        httpTopic.setThumbnailSmall(thumbnails.getThumbnailSmall());
    }

    private List<String> getMedias(final ScrapedInformation scrapedInformation) {
        final List<String> medias = Lists.newArrayList();
        medias.addAll(scrapedInformation.getImages());
        medias.addAll(scrapedInformation.getAudios());
        medias.addAll(scrapedInformation.getImages());
        return medias;
    }

    private String getCanonical(final HttpTopic httpTopic) {
        if (!httpTopic.getUris().isEmpty()) {
            return httpTopic.getUris().get(0).toString();
        } else {
            return "";
        }
    }

    private final Scraper scraper;
    private final Cloudinary cloudinary;
    private final RateLimiter rateLimiter;
    private final Logger LOGGER = LoggerFactory.getLogger(HttpTopicAnalyzer.class);
}
