package com.steambeat.domain.keyword;

import com.steambeat.domain.thesaurus.Language;
import com.steambeat.domain.reference.Reference;
import com.steambeat.repositories.Repositories;

import java.util.UUID;

public class KeywordTestFactory {

    public Keyword newKeyword() {
        final String value = "value";
        final Language language = Language.forString("english");
        return newKeyword(value, language);
    }

    public Keyword newKeyword(final String value, final Language language) {
        final Keyword keyword = new Keyword(value, language, createAndPersistReference().getId());
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
