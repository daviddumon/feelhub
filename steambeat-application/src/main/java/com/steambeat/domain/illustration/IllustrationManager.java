package com.steambeat.domain.illustration;

import com.google.common.collect.Lists;
import com.steambeat.repositories.Repositories;

import java.util.*;

public class IllustrationManager {

    public void migrate(final UUID referenceId, final List<UUID> oldReferenceIds) {
        final List<UUID> referenceIdList = getReferenceIdList(referenceId, oldReferenceIds);
        final List<Illustration> illustrations = getAllIllustrations(referenceIdList);
        if (!illustrations.isEmpty()) {
            migrateExistingIllustrations(illustrations, referenceId);
            removeDuplicate(illustrations);
        }
    }

    private List<UUID> getReferenceIdList(final UUID referenceId, final List<UUID> oldReferenceIds) {
        final List<UUID> referenceIdList = oldReferenceIds;
        referenceIdList.add(referenceId);
        return referenceIdList;
    }

    private List<Illustration> getAllIllustrations(final List<UUID> oldReferenceIds) {
        final List<Illustration> illustrations = Lists.newArrayList();
        for (final UUID referenceId : oldReferenceIds) {
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
