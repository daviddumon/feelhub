package com.steambeat.domain.keyword;

import com.google.common.collect.Lists;
import com.steambeat.domain.illustration.IllustrationManager;
import com.steambeat.domain.opinion.OpinionManager;
import com.steambeat.domain.reference.*;
import com.steambeat.domain.relation.RelationManager;
import com.steambeat.domain.statistics.StatisticsManager;
import com.steambeat.repositories.Repositories;

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
        for (Reference reference : allReferences) {
            final UUID currentId = reference.getId();
            if (!currentId.equals(referencePatch.getNewReferenceId())) {
                referencePatch.addOldReferenceId(currentId);
            }
        }
    }

    private KeywordManager keywordManager = new KeywordManager();
    private ReferenceManager referenceManager = new ReferenceManager();
    private IllustrationManager illustrationManager = new IllustrationManager();
    private OpinionManager opinionManager = new OpinionManager();
    private RelationManager relationManager = new RelationManager();
    private StatisticsManager statisticsManager = new StatisticsManager();
}
