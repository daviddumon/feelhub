package com.steambeat.domain.association;

import com.steambeat.domain.association.tag.Tag;
import com.steambeat.domain.association.uri.Uri;
import com.steambeat.domain.thesaurus.Language;
import com.steambeat.repositories.Repositories;

import java.util.UUID;

public class AssociationTestFactory {

    public Association newAssociation(final Uri uri) {
        final Association association = new Association(uri, UUID.randomUUID());
        Repositories.associations().add(association);
        return association;
    }

    public Association newAssociation(final Identifier identifier, final UUID uuid, final Language language) {
        final Association association = new Association(identifier, uuid, language);
        Repositories.associations().add(association);
        return association;
    }

    public Association newAssociation() {
        return newAssociation(new Tag("tag"), UUID.randomUUID(), Language.forString("french"));
    }
}
