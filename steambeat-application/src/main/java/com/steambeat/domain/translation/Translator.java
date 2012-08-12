package com.steambeat.domain.translation;

import com.google.common.eventbus.*;
import com.google.inject.Inject;
import com.steambeat.domain.eventbus.DomainEventBus;
import com.steambeat.domain.keyword.*;
import com.steambeat.domain.thesaurus.Language;

public class Translator {

    @Inject
    public Translator(final MicrosoftTranslator microsoftTranslator) {
        this.microsoftTranslator = microsoftTranslator;
        DomainEventBus.INSTANCE.register(this);
    }

    @Subscribe
    @AllowConcurrentEvents
    public void translate(final KeywordCreatedEvent event) {
        final Keyword keyword = event.getKeyword();
        final Language language = keyword.getLanguage();
        if (!language.equals(Language.reference()) && !language.equals(Language.none())) {
            try {
                final String result = microsoftTranslator.translate(keyword.getValue(), language.getMicrosoftLanguage());
                final TranslationDoneEvent translationDoneEvent = new TranslationDoneEvent(keyword, result);
                DomainEventBus.INSTANCE.post(translationDoneEvent);
            } catch (TranslateException e) {
            }
        }
    }

    private MicrosoftTranslator microsoftTranslator;
}
