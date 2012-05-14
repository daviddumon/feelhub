package com.steambeat.application;

import com.google.common.collect.Lists;
import com.google.inject.Inject;
import com.steambeat.domain.association.*;
import com.steambeat.domain.association.tag.Tag;
import com.steambeat.domain.association.uri.*;
import com.steambeat.domain.thesaurus.Language;
import com.steambeat.repositories.Repositories;

import java.util.*;

public class AssociationService {

    @Inject
    public AssociationService(final UriPathResolver pathResolver) {
        this.pathResolver = pathResolver;
    }

    public Association lookUp(final Identifier identifier) {
        final Association association = Repositories.associations().forIdentifier(identifier);
        if (association == null) {
            throw new AssociationNotFound();
        }
        return association;
    }

    //todo delete
    public Association createAssociationFor(final Tag tag, final UUID id) {
        final Association association = new Association(tag, id);
        Repositories.associations().add(association);
        return association;
    }

    public Association createAssociationFor(final Tag tag, final UUID id, Language language) {
        final Association association = new Association(tag, id, language);
        Repositories.associations().add(association);
        return association;
    }

    public Association createAssociationsFor(final Uri uri) {
        final List<Uri> path = pathResolver.resolve(uri);
        final UUID subjectId = generateSubjectId(lastFrom(path));
        final List<Association> associations = createAssociations(path, subjectId);
        return lastAssociation(associations);
    }

    private UUID generateSubjectId(final Uri canonicalAddress) {
        final Association foundAssociation = Repositories.associations().forIdentifier(canonicalAddress);
        if (foundAssociation != null) {
            return foundAssociation.getSubjectId();
        }
        return UUID.randomUUID();
    }

    private Uri lastFrom(final List<Uri> path) {
        return path.get(path.size() - 1);
    }

    private List<Association> createAssociations(final List<Uri> path, final UUID subjectId) {
        final ArrayList<Association> associations = Lists.newArrayList();
        for (final Uri uri : path) {
            Association association;
            try {
                association = lookUp(uri);
            } catch (AssociationNotFound e) {
                association = new Association(uri, subjectId);
                Repositories.associations().add(association);
            }
            associations.add(association);
        }
        return associations;
    }

    private Association lastAssociation(final List<Association> associations) {
        return associations.get(associations.size() - 1);
    }

    private final UriPathResolver pathResolver;
}
