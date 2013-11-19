package com.feelhub.domain.scraper;

import com.feelhub.domain.eventbus.*;
import com.feelhub.domain.topic.*;
import com.feelhub.domain.topic.http.*;
import com.feelhub.repositories.Repositories;
import com.google.common.eventbus.*;
import com.google.common.util.concurrent.RateLimiter;
import com.google.inject.Inject;
import org.slf4j.*;

import java.util.UUID;

public class HttpTopicAnalyzer {

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
        if (!scrapedInformation.getImages().isEmpty()) {
            createThumbnail(httpTopic, scrapedInformation.getImages().get(0));
        } else {
            createThumbnail(httpTopic, "http://ec2-107-22-105-164.compute-1.amazonaws.com:3000/?url=" + getCanonical(httpTopic) + "&clipRect={%22top%22:0,%22left%22:0,%22width%22:1692,%22height%22:1044}");
        }
    }

    private void setName(final HttpTopic httpTopic, final ScrapedInformation scrapedInformation) {
        if (scrapedInformation.getName().isEmpty()) {
            httpTopic.addName(scrapedInformation.getLanguage(), getCanonical(httpTopic));
        } else {
            httpTopic.addName(scrapedInformation.getLanguage(), scrapedInformation.getName());
        }
    }

    private void createThumbnail(final Topic topic, final String origin) {
        final Thumbnail thumbnail = new Thumbnail();
        thumbnail.setOrigin(origin);
        postThumbnailCreatedEvent(topic, thumbnail);
    }

    private void postThumbnailCreatedEvent(final Topic topic, final Thumbnail thumbnail) {
        final ThumbnailCreatedEvent event = new ThumbnailCreatedEvent();
        event.setTopicId(topic.getCurrentId());
        event.setThumbnail(thumbnail);
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
