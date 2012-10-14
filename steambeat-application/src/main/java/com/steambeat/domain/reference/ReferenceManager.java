package com.steambeat.domain.reference;

import com.google.common.collect.Lists;
import com.steambeat.domain.keyword.Keyword;
import com.steambeat.repositories.Repositories;

import java.util.*;

public abstract class ReferenceManager {

    //protected List<Reference> getAllReferences(final ReferencesToChangeEvent event) {
    //    final List<Reference> references = Lists.newArrayList();
    //    for (final Keyword keyword : event.getKeywords()) {
    //        final UUID referenceId = keyword.getReferenceId();
    //        final Reference reference = Repositories.references().get(referenceId);
    //        references.add(reference);
    //    }
    //    return references;
    //}
    //
    //protected Reference getOldestReference(final List<Reference> references) {
    //    Reference result = references.get(0);
    //    for (int i = 1; i < references.size(); i++) {
    //        final Reference current = references.get(i);
    //        if (current.getCreationDate().isBefore(result.getCreationDate())) {
    //            result = current;
    //        }
    //    }
    //    return result;
    //}
    //
    //protected void setInactiveReferences(final Reference reference, final List<Reference> references) {
    //    for (final Reference current : references) {
    //        if (!current.equals(reference)) {
    //            current.setActive(false);
    //            current.setCurrentReferenceId(reference.getId());
    //        }
    //    }
    //}
    //
    //protected ConceptReferencesChangedEvent createConceptReferencesChangedEvent(final Reference newReference, final List<Reference> references) {
    //    final ConceptReferencesChangedEvent event = new ConceptReferencesChangedEvent(newReference.getId());
    //    for (final Reference reference : references) {
    //        if (!reference.isActive()) {
    //            event.addReferenceToChange(reference.getId());
    //        }
    //    }
    //    return event;
    //}
}
