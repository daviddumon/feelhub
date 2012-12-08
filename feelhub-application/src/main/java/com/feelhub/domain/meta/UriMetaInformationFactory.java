package com.feelhub.domain.meta;

import com.feelhub.domain.eventbus.DomainEventBus;
import com.feelhub.domain.scraper.*;
import com.feelhub.domain.thesaurus.FeelhubLanguage;
import com.feelhub.domain.topic.usable.real.*;
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
        final RealTopic realTopic = uriMetaInformationRequestEvent.getRealTopic();
        final ScrapedInformations scrapedInformations = scraper.scrap(uriMetaInformationRequestEvent.getValue());
        addIllustrations(realTopic, scrapedInformations.getIllustration());
        addType(realTopic, scrapedInformations.getType());
        addName(realTopic, scrapedInformations.getTitle());
        sessionProvider.stop();
    }

    private void addIllustrations(final RealTopic realTopic, final String illustrationValue) {
        final Illustration illustration = new Illustration(realTopic.getId(), illustrationValue);
        Repositories.illustrations().add(illustration);
    }

    private void addType(final RealTopic realTopic, final String type) {
        try {
            realTopic.setType(RealTopicType.valueOf(WordUtils.capitalizeFully(type)));
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        }
    }

    private void addName(final RealTopic realTopic, final String title) {
        realTopic.addName(FeelhubLanguage.none(), title);
    }

    private final Scraper scraper;
    private final SessionProvider sessionProvider;
}
