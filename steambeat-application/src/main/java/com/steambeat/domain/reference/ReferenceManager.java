package com.steambeat.domain.reference;

import com.google.common.collect.Lists;
import com.steambeat.domain.keyword.Keyword;
import com.steambeat.repositories.Repositories;

import java.util.*;

public abstract class ReferenceManager {

    protected void getAllReferences(final ReferencesToChangeEvent event) {
        references = Lists.newArrayList();
        for (Keyword keyword : event.getKeywords()) {
            final UUID referenceId = keyword.getReferenceId();
            final Reference reference = Repositories.references().get(referenceId);
            references.add(reference);
        }
    }

    protected Reference getOldestReference(final ReferencesToChangeEvent event) {
        Reference result = references.get(0);
        for (int i = 1; i < references.size(); i++) {
            final Reference current = references.get(i);
            if (current.getCreationDate().isBefore(result.getCreationDate())) {
                result = current;
            }
        }
        return result;
    }

    protected void setInactiveReferences(final Reference reference) {
        for (Reference current : references) {
            if (!current.equals(reference)) {
                current.setActive(false);
            }
        }
    }

    protected List<Reference> references;
}
