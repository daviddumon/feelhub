package com.steambeat.domain.subject.steam;

import com.steambeat.domain.scrapers.Scraper;
import com.steambeat.domain.subject.Subject;

import java.util.UUID;

public class Steam extends Subject {

    public Steam() {
        super(UUID.randomUUID());
    }

    @Override
    public void setScraper(final Scraper scraper) {
        update(scraper);
    }

    @Override
    protected void update(final Scraper scraper) {
        this.description = "steam";
        this.shortDescription = "steam";
        this.illustration = "";
    }
}
