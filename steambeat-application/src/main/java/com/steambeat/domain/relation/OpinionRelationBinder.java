package com.steambeat.domain.relation;

import com.google.common.collect.Lists;
import com.google.inject.Inject;
import com.steambeat.domain.opinion.*;
import com.steambeat.domain.reference.Reference;
import com.steambeat.repositories.Repositories;

import java.util.List;

public class OpinionRelationBinder {

    @Inject
    public OpinionRelationBinder(final RelationBuilder relationBuilder) {
        this.relationBuilder = relationBuilder;
    }

    public void bind(final Opinion opinion) {
        final List<Reference> references = loadAllReferences(opinion.getJudgments());
        createRelations(references);
    }

    private List<Reference> loadAllReferences(final List<Judgment> judgments) {
        final List<Reference> references = Lists.newArrayList();
        for (final Judgment judgment : judgments) {
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

    private final RelationBuilder relationBuilder;
}
