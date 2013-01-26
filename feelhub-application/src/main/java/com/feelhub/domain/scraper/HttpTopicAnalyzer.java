package com.feelhub.domain.scraper;

import com.feelhub.application.TopicService;
import com.feelhub.domain.topic.http.HttpTopic;
import com.google.common.collect.Lists;
import com.google.inject.Inject;

import java.util.List;

public class HttpTopicAnalyzer {

    @Inject
    public HttpTopicAnalyzer(final Scraper scraper) {
        this.scraper = scraper;
    }

    public List<String> analyze(final HttpTopic httpTopic) {
        final ScrapedInformation scrapedInformation = scraper.scrap(getCanonical(httpTopic));
        httpTopic.addDescription(scrapedInformation.getLanguage(), scrapedInformation.getDescription());
        httpTopic.addName(scrapedInformation.getLanguage(), scrapedInformation.getName());
        httpTopic.setType(scrapedInformation.getType());
        httpTopic.setOpenGraphType(scrapedInformation.getOpenGraphType());
        if (!scrapedInformation.getImages().isEmpty()) {
            httpTopic.setIllustration(scrapedInformation.getImages().get(0));
        }
        return getMedias(scrapedInformation);
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
    private TopicService topicService;
}
