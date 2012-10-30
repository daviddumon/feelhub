package com.feelhub.application;

import com.feelhub.domain.eventbus.DomainEventBus;
import com.feelhub.domain.keyword.Keyword;
import com.feelhub.domain.opinion.*;
import com.feelhub.domain.relation.OpinionRelationBinder;
import com.feelhub.repositories.*;
import com.google.common.collect.Lists;
import com.google.common.eventbus.Subscribe;
import com.google.inject.Inject;

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
        final List<Judgment> judgments = getJudgments(opinionRequestEvent);
        final Opinion opinion = buildOpinion(opinionRequestEvent, judgments);
        opinionRelationBinder.bind(opinion);
        Repositories.opinions().add(opinion);
        sessionProvider.stop();
    }

    private List<Judgment> getJudgments(final OpinionRequestEvent opinionRequestEvent) {
        final List<Judgment> judgments = Lists.newArrayList();
        judgments.addAll(fromText(opinionRequestEvent));
        judgments.addAll(fromOpinionFeeling(opinionRequestEvent));
        return judgments;
    }

    private List<Judgment> fromText(final OpinionRequestEvent opinionRequestEvent) {
        final List<Judgment> result = Lists.newArrayList();
        final List<Subject> subjects = subjectExtractor.extract(opinionRequestEvent.getText());
        for (final Subject subject : subjects) {
            final Keyword keyword = keywordService.lookUpOrCreate(subject.text, opinionRequestEvent.getUserLanguageCode());
            final Judgment judgment = new Judgment(keyword.getReferenceId(), subject.feeling);
            result.add(judgment);
        }
        return result;
    }

    private List<Judgment> fromOpinionFeeling(final OpinionRequestEvent opinionRequestEvent) {
        final List<Judgment> result = Lists.newArrayList();
        final Keyword keyword = keywordService.lookUpOrCreate(opinionRequestEvent.getKeywordValue(), opinionRequestEvent.getLanguageCode());
        final Judgment judgment = new Judgment(keyword.getReferenceId(), opinionRequestEvent.getFeeling());
        result.add(judgment);
        return result;
    }

    private Opinion buildOpinion(final OpinionRequestEvent opinionRequestEvent, final List<Judgment> judgments) {
        final Opinion.Builder builder = new Opinion.Builder();
        builder.id(opinionRequestEvent.getOpinionId());
        builder.text(opinionRequestEvent.getText());
        builder.user(opinionRequestEvent.getUserId());
        builder.language(opinionRequestEvent.getUserLanguageCode());
        builder.judgments(judgments);
        return builder.build();
    }

    private final SessionProvider sessionProvider;
    private final KeywordService keywordService;
    private final OpinionRelationBinder opinionRelationBinder;
    private final SubjectExtractor subjectExtractor;
}
