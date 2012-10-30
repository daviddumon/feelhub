package com.feelhub.domain.illustration;

import com.feelhub.domain.reference.ReferencePatch;
import com.feelhub.repositories.Repositories;
import com.google.common.collect.Lists;

import java.util.*;

public class IllustrationManager {

    public void merge(final ReferencePatch referencePatch) {
        final List<UUID> referenceIdList = getReferenceIdList(referencePatch);
        final List<Illustration> illustrations = getAllIllustrations(referenceIdList);
        if (!illustrations.isEmpty()) {
            migrateExistingIllustrations(illustrations, referencePatch.getNewReferenceId());
            removeDuplicate(illustrations);
        }
    }

    private List<UUID> getReferenceIdList(final ReferencePatch referencePatch) {
        final List<UUID> referenceIdList = Lists.newArrayList();
        referenceIdList.addAll(referencePatch.getOldReferenceIds());
        referenceIdList.add(referencePatch.getNewReferenceId());
        return referenceIdList;
    }

    private List<Illustration> getAllIllustrations(final List<UUID> referenceIds) {
        final List<Illustration> illustrations = Lists.newArrayList();
        for (final UUID referenceId : referenceIds) {
            final Illustration illustration = getIllustrationFor(referenceId);
            if (illustration != null) {
                illustrations.add(illustration);
            }
        }
        return illustrations;
    }

    private Illustration getIllustrationFor(final UUID referenceId) {
        final List<Illustration> illustrations = Repositories.illustrations().forReferenceId(referenceId);
        if (!illustrations.isEmpty()) {
            return illustrations.get(0);
        } else {
            return null;
        }
    }

    private void migrateExistingIllustrations(final List<Illustration> illustrations, final UUID newReference) {
        for (final Illustration illustration : illustrations) {
            if (illustration.getReferenceId() != newReference) {
                illustration.setReferenceId(newReference);
            }
        }
    }

    private void removeDuplicate(final List<Illustration> illustrations) {
        if (illustrations.size() > 1) {
            for (int i = 1; i < illustrations.size(); i++) {
                Repositories.illustrations().delete(illustrations.get(i));
            }
        }
    }
}
