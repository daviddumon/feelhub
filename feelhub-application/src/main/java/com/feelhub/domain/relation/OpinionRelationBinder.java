package com.feelhub.domain.relation;

import com.feelhub.domain.opinion.*;
import com.feelhub.domain.reference.Reference;
import com.feelhub.repositories.Repositories;
import com.google.common.collect.Lists;
import com.google.inject.Inject;

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

    private void connectReference(final Reference from, final List<Reference> references, final int beginningIndex) {
        for (int i = beginningIndex; i < references.size(); i++) {
            final Reference to = references.get(i);
            relationBuilder.connectTwoWays(from, to);
        }
    }

    private final RelationBuilder relationBuilder;
}
