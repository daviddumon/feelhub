package com.feelhub.domain.scraper;

public class FakeScrapedInformations extends ScrapedInformations {

    @Override
    public String getIllustration() {
        return "fakeillustration";
    }

    @Override
    public String getType() {
        return "video";
    }

    @Override
    public String getTitle() {
        return "faketitle";
    }
}
