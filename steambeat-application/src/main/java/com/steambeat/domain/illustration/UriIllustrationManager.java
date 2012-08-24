package com.steambeat.domain.illustration;

import com.google.common.eventbus.Subscribe;
import com.google.inject.Inject;
import com.steambeat.domain.eventbus.DomainEventBus;
import com.steambeat.domain.keyword.Keyword;
import com.steambeat.domain.reference.UriReferencesChangedEvent;
import com.steambeat.domain.scraper.Scraper;
import com.steambeat.repositories.*;

import java.util.List;

public class UriIllustrationManager extends IllustrationManager {

    @Inject
    public UriIllustrationManager(final SessionProvider sessionProvider, final Scraper scraper) {
        this.sessionProvider = sessionProvider;
        this.scraper = scraper;
        DomainEventBus.INSTANCE.register(this);
    }

    @Subscribe
    public void handle(final UriReferencesChangedEvent event) {
        sessionProvider.start();
        final List<Illustration> illustrations = getAllIllustrations(event);
        if (illustrations.isEmpty()) {
            addAnIllustration(event);
        } else {
            migrateExistingIllustrations(illustrations, event.getNewReferenceId());
            removeDuplicate(illustrations);
        }
        sessionProvider.stop();
    }

    private void addAnIllustration(final UriReferencesChangedEvent event) {
        final Keyword keyword = getKeywordFor(event);
        scraper.scrap(keyword.getValue());
        final Illustration illustration = new Illustration(event.getNewReferenceId(), scraper.getIllustration());
        Repositories.illustrations().add(illustration);
    }

    private SessionProvider sessionProvider;
    private Scraper scraper;
}
