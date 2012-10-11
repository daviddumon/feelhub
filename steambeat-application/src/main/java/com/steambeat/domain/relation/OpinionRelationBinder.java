package com.steambeat.domain.relation;

import com.google.common.collect.Lists;
import com.google.common.eventbus.Subscribe;
import com.google.inject.Inject;
import com.steambeat.domain.eventbus.DomainEventBus;
import com.steambeat.domain.opinion.*;
import com.steambeat.domain.reference.Reference;
import com.steambeat.repositories.*;

import java.util.List;

public class OpinionRelationBinder {

    @Inject
    public OpinionRelationBinder(final SessionProvider sessionProvider, final RelationBuilder relationBuilder) {
        this.sessionProvider = sessionProvider;
        this.relationBuilder = relationBuilder;
        DomainEventBus.INSTANCE.register(this);
    }

    @Subscribe
    public void handle(final OpinionCreatedEvent opinionCreatedEvent) {
        sessionProvider.start();
        final List<Reference> references = loadAllReferences(opinionCreatedEvent.getOpinion().getJudgments());
        createRelations(references);
        sessionProvider.stop();
    }

    private List<Reference> loadAllReferences(final List<Judgment> judgments) {
        List<Reference> references = Lists.newArrayList();
        for (Judgment judgment : judgments) {
            final Reference reference = Repositories.references().get(judgment.getReferenceId());
            references.add(reference);
        }
        return references;
    }

    private void createRelations(final List<Reference> references) {
        for (int i = 0; i < references.size(); i++) {
            final Reference currentReference = references.get(i);
            connectReference(currentReference, references, i + 1);
        }
    }

    private void connectReference(final Reference currentReference, final List<Reference> references, final int beginningIndex) {
        for (int i = beginningIndex; i < references.size(); i++) {
            relationBuilder.connectTwoWays(currentReference, references.get(i));
        }
    }

    private SessionProvider sessionProvider;
    private RelationBuilder relationBuilder;
}
