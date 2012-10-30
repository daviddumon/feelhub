package com.feelhub.domain.keyword;

import com.feelhub.domain.illustration.IllustrationManager;
import com.feelhub.domain.opinion.OpinionManager;
import com.feelhub.domain.reference.*;
import com.feelhub.domain.relation.RelationManager;
import com.feelhub.domain.statistics.StatisticsManager;
import com.feelhub.repositories.Repositories;
import com.google.common.collect.Lists;

import java.util.*;

public class KeywordMerger {

    public void merge(final List<Keyword> keywords) {
        final ReferencePatch referencePatch = createReferencePatch(keywords);
        keywordManager.merge(referencePatch);
        referenceManager.merge(referencePatch);
        illustrationManager.merge(referencePatch);
        opinionManager.merge(referencePatch);
        relationManager.merge(referencePatch);
        statisticsManager.merge(referencePatch);
    }

    private ReferencePatch createReferencePatch(final List<Keyword> keywords) {
        final List<Reference> allReferences = getAllReferences(keywords);
        final Reference oldestReference = getOldestReference(allReferences);
        final ReferencePatch referencePatch = new ReferencePatch(oldestReference.getId());
        appendOldReferences(referencePatch, allReferences);
        return referencePatch;
    }

    protected List<Reference> getAllReferences(final List<Keyword> keywords) {
        final List<Reference> references = Lists.newArrayList();
        for (final Keyword keyword : keywords) {
            final UUID referenceId = keyword.getReferenceId();
            final Reference reference = Repositories.references().get(referenceId);
            references.add(reference);
        }
        return references;
    }

    private Reference getOldestReference(final List<Reference> references) {
        Reference result = references.get(0);
        for (int i = 1; i < references.size(); i++) {
            final Reference current = references.get(i);
            if (current.getCreationDate().isBefore(result.getCreationDate())) {
                result = current;
            }
        }
        return result;
    }

    private void appendOldReferences(final ReferencePatch referencePatch, final List<Reference> allReferences) {
        for (final Reference reference : allReferences) {
            final UUID currentId = reference.getId();
            if (!currentId.equals(referencePatch.getNewReferenceId())) {
                referencePatch.addOldReferenceId(currentId);
            }
        }
    }

    private final KeywordManager keywordManager = new KeywordManager();
    private final ReferenceManager referenceManager = new ReferenceManager();
    private final IllustrationManager illustrationManager = new IllustrationManager();
    private final OpinionManager opinionManager = new OpinionManager();
    private final RelationManager relationManager = new RelationManager();
    private final StatisticsManager statisticsManager = new StatisticsManager();
}
