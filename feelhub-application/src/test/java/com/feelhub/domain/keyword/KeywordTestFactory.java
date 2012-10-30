package com.feelhub.domain.keyword;

import com.feelhub.domain.reference.Reference;
import com.feelhub.domain.thesaurus.FeelhubLanguage;
import com.feelhub.repositories.Repositories;

import java.util.UUID;

public class KeywordTestFactory {

    public Keyword newKeyword() {
        final String value = "Value";
        final FeelhubLanguage feelhubLanguage = FeelhubLanguage.forString("english");
        return newKeyword(value, feelhubLanguage);
    }

    public Keyword newKeyword(final String value) {
        return newKeyword(value, FeelhubLanguage.none());
    }

    public Keyword newKeyword(final String value, final FeelhubLanguage feelhubLanguage) {
        final Keyword keyword = new Keyword(value, feelhubLanguage, createAndPersistReference().getId());
        Repositories.keywords().add(keyword);
        return keyword;
    }

    public Keyword newKeyword(final String value, final FeelhubLanguage feelhubLanguage, final Reference reference) {
        final Keyword keyword = new Keyword(value, feelhubLanguage, reference.getId());
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
