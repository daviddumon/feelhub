package com.steambeat.application;

import com.google.common.eventbus.Subscribe;
import com.google.inject.Inject;
import com.steambeat.domain.eventbus.DomainEventBus;
import com.steambeat.domain.keyword.*;
import com.steambeat.domain.opinion.*;
import com.steambeat.domain.relation.OpinionRelationBinder;
import com.steambeat.domain.thesaurus.SteambeatLanguage;
import com.steambeat.repositories.*;

import java.util.List;

public class OpinionService {

    @Inject
    public OpinionService(final SessionProvider sessionProvider, final KeywordService keywordService, final OpinionRelationBinder opinionRelationBinder) {
        this.sessionProvider = sessionProvider;
        this.keywordService = keywordService;
        this.opinionRelationBinder = opinionRelationBinder;
        subjectExtractor = new SubjectExtractor();
        DomainEventBus.INSTANCE.register(this);
    }

    @Subscribe
    public void handle(final OpinionRequestEvent opinionRequestEvent) {
        sessionProvider.start();
        final Opinion opinion = new Opinion(opinionRequestEvent.getText(), opinionRequestEvent.getUserId(), opinionRequestEvent.getOpinionId());
        opinion.setLanguageCode(opinionRequestEvent.getUserLanguageCode());
        final List<Subject> subjects = subjectExtractor.extract(opinionRequestEvent.getText());
        for (Subject subject : subjects) {
            final Keyword keyword = getKeyword(subject.text, opinion.getLanguageCode());
            final Judgment judgment = new Judgment(keyword.getReferenceId(), subject.feeling);
            opinion.addJudgment(judgment);
        }
        final Keyword keyword = getKeyword(opinionRequestEvent.getKeywordValue(), opinionRequestEvent.getLanguageCode());
        final Judgment judgment = new Judgment(keyword.getReferenceId(), opinionRequestEvent.getFeeling());
        opinion.addJudgment(judgment);
        opinionRelationBinder.bind(opinion);
        Repositories.opinions().add(opinion);
        sessionProvider.stop();
    }

    private Keyword getKeyword(final String value, final String languageCode) {
        Keyword keyword;
        try {
            keyword = keywordService.lookUp(value, SteambeatLanguage.forString(languageCode));
        } catch (KeywordNotFound e) {
            keyword = keywordService.createKeyword(value, SteambeatLanguage.forString(languageCode));
        }
        return keyword;
    }

    private SessionProvider sessionProvider;
    private KeywordService keywordService;
    private OpinionRelationBinder opinionRelationBinder;
    private final SubjectExtractor subjectExtractor;
}
