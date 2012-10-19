package com.steambeat.domain.keyword;

import com.steambeat.domain.reference.Reference;
import com.steambeat.domain.thesaurus.SteambeatLanguage;
import com.steambeat.repositories.Repositories;

import java.util.UUID;

public class KeywordTestFactory {

    public Keyword newKeyword() {
        final String value = "Value";
        final SteambeatLanguage steambeatLanguage = SteambeatLanguage.forString("english");
        return newKeyword(value, steambeatLanguage);
    }

    public Keyword newKeyword(final String value) {
        return newKeyword(value, SteambeatLanguage.none());
    }

    public Keyword newKeyword(final String value, final SteambeatLanguage steambeatLanguage) {
        final Keyword keyword = new Keyword(value, steambeatLanguage, createAndPersistReference().getId());
        Repositories.keywords().add(keyword);
        return keyword;
    }

    public Keyword newKeyword(final String value, final SteambeatLanguage steambeatLanguage, final Reference reference) {
        final Keyword keyword = new Keyword(value, steambeatLanguage, reference.getId());
        Repositories.keywords().add(keyword);
        return keyword;
    }

    private Reference createAndPersistReference() {
        final UUID id = UUID.randomUUID();
        final Reference reference = new Reference(id);
        Repositories.references().add(reference);
        return reference;
    }
}
