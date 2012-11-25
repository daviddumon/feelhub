package com.feelhub.domain.meta;

import com.feelhub.domain.eventbus.DomainEventBus;
import com.feelhub.domain.scraper.*;
import com.feelhub.domain.thesaurus.FeelhubLanguage;
import com.feelhub.domain.topic.*;
import com.feelhub.repositories.*;
import com.google.common.eventbus.Subscribe;
import com.google.inject.Inject;
import org.apache.commons.lang.WordUtils;

public class UriMetaInformationFactory {

    @Inject
    public UriMetaInformationFactory(final Scraper scraper, final SessionProvider sessionProvider) {
        this.scraper = scraper;
        this.sessionProvider = sessionProvider;
        DomainEventBus.INSTANCE.register(this);
    }

    @Subscribe
    public void handle(final UriMetaInformationRequestEvent uriMetaInformationRequestEvent) {
        sessionProvider.start();
        final Topic topic = uriMetaInformationRequestEvent.getTopic();
        final ScrapedInformations scrapedInformations = scraper.scrap(uriMetaInformationRequestEvent.getValue());
        addIllustrations(topic, scrapedInformations.getIllustration());
        addType(topic, scrapedInformations.getType());
        addDescription(topic, scrapedInformations.getTitle());
        sessionProvider.stop();
    }

    private void addIllustrations(final Topic topic, final String illustrationValue) {
        final Illustration illustration = new Illustration(topic.getId(), illustrationValue);
        Repositories.illustrations().add(illustration);
    }

    private void addType(final Topic topic, final String type) {
        try {
             topic.setType(TopicType.valueOf(WordUtils.capitalizeFully(type)));
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        }
    }

    private void addDescription(final Topic topic, final String title) {
        topic.addDescription(FeelhubLanguage.none(), title);
    }

    private final Scraper scraper;
    private final SessionProvider sessionProvider;
}
