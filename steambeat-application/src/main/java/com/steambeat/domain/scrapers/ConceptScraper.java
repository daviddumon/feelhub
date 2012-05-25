package com.steambeat.domain.scrapers;

import com.steambeat.domain.association.Identifier;
import com.steambeat.domain.bingsearch.BingLink;
import com.steambeat.domain.subject.concept.Concept;

public class ConceptScraper implements Scraper {

    @Override
    public String getShortDescription() {
        return "";
    }

    @Override
    public String getDescription() {
        return "";
    }

    @Override
    public String getIllustration() {
        return illustration;
    }

    @Override
    public void scrap(final Identifier identifier) {
        this.illustration = bingLink.getIllustration(concept);
    }

    public void setBingLink(final BingLink bingLink) {
        this.bingLink = bingLink;
    }

    public void setConcept(final Concept concept) {
        this.concept = concept;
    }

    private BingLink bingLink;
    private Concept concept;
    private String illustration;
}
