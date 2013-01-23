package com.feelhub.domain.scraper;

import com.feelhub.domain.thesaurus.FeelhubLanguage;
import com.feelhub.domain.topic.http.HttpTopic;
import com.google.inject.Inject;

public class HttpTopicAnalyzer {

    @Inject
    public HttpTopicAnalyzer(final Scraper scraper) {
        this.scraper = scraper;
    }

    public void analyze(final HttpTopic httpTopic) {
        //scraper.scrap(getCanonical(httpTopic));
        httpTopic.addDescription(FeelhubLanguage.reference(), "description");
    }

    private String getCanonical(final HttpTopic httpTopic) {
        if (!httpTopic.getUris().isEmpty()) {
            return httpTopic.getUris().get(0).toString();
        } else {
            return "";
        }
    }

    private Scraper scraper;
}
