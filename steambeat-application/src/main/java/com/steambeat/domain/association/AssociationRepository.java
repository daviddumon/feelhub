package com.steambeat.domain.association;

import com.steambeat.domain.Repository;
import com.steambeat.domain.thesaurus.Language;

public interface AssociationRepository extends Repository<Association> {

    public Association forIdentifier(final Identifier identifier);

    public Association forIdentifierAndLanguage(final Identifier identifier, final Language language);
}
