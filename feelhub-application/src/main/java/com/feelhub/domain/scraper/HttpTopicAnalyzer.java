package com.feelhub.domain.scraper;

import com.feelhub.application.TopicService;
import com.feelhub.domain.cloudinary.*;
import com.feelhub.domain.eventbus.DomainEventBus;
import com.feelhub.domain.topic.http.HttpTopic;
import com.feelhub.repositories.Repositories;
import com.feelhub.tools.MongoLinkAwareExecutor;
import com.google.common.collect.Lists;
import com.google.common.eventbus.Subscribe;
import com.google.common.util.concurrent.RateLimiter;
import com.google.inject.Inject;

import java.util.List;

public class HttpTopicAnalyzer {

    @Inject
    public HttpTopicAnalyzer(final Scraper scraper, final Cloudinary cloudinary, final MongoLinkAwareExecutor mongoLinkAwareExecutor) {
        this.scraper = scraper;
        this.cloudinary = cloudinary;
        this.mongoLinkAwareExecutor = mongoLinkAwareExecutor;
        DomainEventBus.INSTANCE.register(this);
        rateLimiter = RateLimiter.create(1.0);
    }

    @Subscribe
    public void onHttpTopicAnalyzeRequest(final HttpTopicAnalyzeRequest httpTopicAnalyzeRequest) {
        final Runnable runnable = new Runnable() {

            @Override
            public void run() {
                analyze(httpTopicAnalyzeRequest.getHttpTopic());
            }
        };
        rateLimiter.acquire();
        mongoLinkAwareExecutor.execute(runnable);
    }

    public List<String> analyze(final HttpTopic httpTopic) {
        final ScrapedInformation scrapedInformation = scraper.scrap(getCanonical(httpTopic));
        //todo : delete this horror once we figure out the persistence async problem
        final HttpTopic currentTopic = Repositories.topics().getHttpTopic(httpTopic.getCurrentId());
        //
        currentTopic.addDescription(scrapedInformation.getLanguage(), scrapedInformation.getDescription());
        currentTopic.addName(scrapedInformation.getLanguage(), scrapedInformation.getName());
        currentTopic.setType(scrapedInformation.getType());
        currentTopic.setOpenGraphType(scrapedInformation.getOpenGraphType());
        if (!scrapedInformation.getImages().isEmpty()) {
            currentTopic.setIllustration(scrapedInformation.getImages().get(0));
            getThumbnails(currentTopic);
        }
        return getMedias(scrapedInformation);
    }

    private void getThumbnails(final HttpTopic httpTopic) {
        final CloudinaryThumbnails thumbnails = cloudinary.getThumbnails(httpTopic.getIllustration());
        httpTopic.setThumbnailLarge(thumbnails.getThumbnailLarge());
        httpTopic.setThumbnailMedium(thumbnails.getThumbnailMedium());
        httpTopic.setThumbnailSmall(thumbnails.getThumbnailSmall());
    }

    private List<String> getMedias(final ScrapedInformation scrapedInformation) {
        List<String> medias = Lists.newArrayList();
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

    private Scraper scraper;
    private Cloudinary cloudinary;
    private MongoLinkAwareExecutor mongoLinkAwareExecutor;
    private TopicService topicService;
    private final RateLimiter rateLimiter;
}
