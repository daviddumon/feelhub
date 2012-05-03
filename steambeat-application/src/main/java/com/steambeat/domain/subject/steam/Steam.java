package com.steambeat.domain.subject.steam;

import com.steambeat.domain.scrapers.Scraper;
import com.steambeat.domain.subject.Subject;

import java.util.UUID;

public class Steam extends Subject {

    public Steam() {
    }

    public Steam(final UUID uuid) {
        super(uuid);
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

    @Override
    public String getUriToken() {
        return "";
    }
}
