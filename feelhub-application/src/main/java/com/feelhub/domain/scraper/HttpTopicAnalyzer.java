package com.feelhub.domain.scraper;

import com.feelhub.domain.eventbus.*;
import com.feelhub.domain.topic.*;
import com.feelhub.domain.topic.http.*;
import com.feelhub.repositories.Repositories;
import com.google.common.collect.Lists;
import com.google.common.eventbus.*;
import com.google.common.util.concurrent.RateLimiter;
import com.google.inject.Inject;
import org.slf4j.*;

import java.util.List;
import java.util.UUID;

public class HttpTopicAnalyzer {

    public static final String SCREENSHOT_SERVICE_URL = "http://ec2-107-22-105-164.compute-1.amazonaws.com:3000";

    @Inject
    public HttpTopicAnalyzer(final Scraper scraper) {
        this.scraper = scraper;
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

    @Subscribe
    @Synchronize
    @AllowConcurrentEvents
    public void onHttpTopicThumbnailUpdateRequested(final HttpTopicThumbnailUpdateRequestedEvent event) {
        rateLimiter.acquire();
        updateThumbnail(Repositories.topics().getHttpTopic(event.topicId));
    }

    public void analyze(final UUID topicId) {
        final HttpTopic httpTopic = Repositories.topics().getHttpTopic(topicId);
        LOGGER.debug("Analysing topic : {}", httpTopic);
        if (HttpTopicType.Website.equals(httpTopic.getType())) {
            scrap(httpTopic);
        } else if (HttpTopicType.Data.equals(httpTopic.getType()) || HttpTopicType.Image.equals(httpTopic.getType())) {
            createThumbnail(httpTopic, getCanonical(httpTopic));
        }
    }

    private void scrap(final HttpTopic httpTopic) {
        final ScrapedInformation scrapedInformation = scraper.scrap(getCanonical(httpTopic));
        httpTopic.addDescription(scrapedInformation.getLanguage(), scrapedInformation.getDescription());
        httpTopic.setLanguageCode(scrapedInformation.getLanguage().getCode());
        setName(httpTopic, scrapedInformation);
        httpTopic.setType(scrapedInformation.getType());
        httpTopic.setOpenGraphType(scrapedInformation.getOpenGraphType());
        createThumbnailFromScrapedInformations(httpTopic, scrapedInformation.getImages());
    }

    private void setName(final HttpTopic httpTopic, final ScrapedInformation scrapedInformation) {
        if (scrapedInformation.getName().isEmpty()) {
            httpTopic.addName(scrapedInformation.getLanguage(), getCanonical(httpTopic));
        } else {
            httpTopic.addName(scrapedInformation.getLanguage(), scrapedInformation.getName());
        }
    }

    private void updateThumbnail(HttpTopic topic) {
        if (HttpTopicType.Website.equals(topic.getType())) {
            createThumbnailFromScrapedInformations(topic, scraper.scrap(getCanonical(topic)).getImages());
        } else if (HttpTopicType.Data.equals(topic.getType()) || HttpTopicType.Image.equals(topic.getType())) {
            createThumbnail(topic, getCanonical(topic));
        }
    }

    private void createThumbnailFromScrapedInformations(HttpTopic httpTopic, List<String> images) {
        List<String> origins = Lists.newArrayList(images);
        origins.add(getScreenshotUrl(httpTopic));
        createThumbnail(httpTopic, origins.toArray(new String[origins.size()]));
    }

    private String getScreenshotUrl(HttpTopic httpTopic) {
        return SCREENSHOT_SERVICE_URL + "/?url=" + getCanonical(httpTopic) + "&clipRect={%22top%22:0,%22left%22:0,%22width%22:1692,%22height%22:1044}";
    }

    private void createThumbnail(final Topic topic, final String... origins) {
        List<Thumbnail> thumbnails = Lists.newArrayList();
        for (String origin : origins) {
            Thumbnail thumbnail = new Thumbnail(origin);
            thumbnails.add(thumbnail);
        }
        postThumbnailCreatedEvent(topic, thumbnails.toArray(new Thumbnail[thumbnails.size()]));
    }

    private void postThumbnailCreatedEvent(final Topic topic, final Thumbnail... thumbnails) {
        final ThumbnailCreatedEvent event = new ThumbnailCreatedEvent();
        event.setTopicId(topic.getCurrentId());
        event.addThumbnails(thumbnails);
        DomainEventBus.INSTANCE.post(event);
    }

    private String getCanonical(final HttpTopic httpTopic) {
        if (!httpTopic.getUris().isEmpty()) {
            return httpTopic.getUris().get(0).toString();
        } else {
            return "";
        }
    }

    private final Scraper scraper;
    private final RateLimiter rateLimiter;
    private final Logger LOGGER = LoggerFactory.getLogger(HttpTopicAnalyzer.class);
}
