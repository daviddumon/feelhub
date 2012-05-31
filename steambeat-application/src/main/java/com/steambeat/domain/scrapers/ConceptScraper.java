package com.steambeat.domain.scrapers;

import com.steambeat.domain.association.Identifier;
import com.steambeat.domain.association.tag.Tag;
import com.steambeat.domain.bingsearch.BingLink;

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
        this.illustration = bingLink.getIllustration((Tag) identifier);
    }

    public void setBingLink(final BingLink bingLink) {
        this.bingLink = bingLink;
    }

    private BingLink bingLink;
    private String illustration;
}
