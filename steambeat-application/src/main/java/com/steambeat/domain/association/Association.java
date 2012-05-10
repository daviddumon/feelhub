package com.steambeat.domain.association;

import com.steambeat.domain.BaseEntity;
import com.steambeat.domain.thesaurus.Language;

import java.util.UUID;

public class Association extends BaseEntity {

    // Mongolink constructor
    protected Association() {
    }

    public Association(final Identifier identifier, final UUID uuid) {
        this.id = identifier.toString();
        this.subjectId = uuid;
        this.language = Language.forString("none");
    }

    public Association(final Identifier identifier, final UUID uuid, final Language language) {
        this.id = identifier.toString();
        this.subjectId = uuid;
        this.language = language;
    }

    @Override
    public String getId() {
        return id;
    }

    public UUID getSubjectId() {
        return subjectId;
    }

    public Language getLanguage() {
        return language;
    }

    private String id;
    private UUID subjectId;
    private Language language;
}
