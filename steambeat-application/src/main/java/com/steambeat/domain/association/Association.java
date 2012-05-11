package com.steambeat.domain.association;

import com.steambeat.domain.BaseEntity;
import com.steambeat.domain.thesaurus.Language;

import java.util.UUID;

public class Association extends BaseEntity {

    // Mongolink constructor
    protected Association() {
    }

    public Association(final Identifier identifier, final UUID uuid) {
        this.id = UUID.randomUUID();
        this.identifier = identifier.toString();
        this.subjectId = uuid;
        this.language = Language.forString("all");
    }

    public Association(final Identifier identifier, final UUID uuid, final Language language) {
        this.id = UUID.randomUUID();
        this.identifier = identifier.toString();
        this.subjectId = uuid;
        this.language = language;
    }

    @Override
    public UUID getId() {
        return id;
    }

    public UUID getSubjectId() {
        return subjectId;
    }

    public Language getLanguage() {
        return language;
    }

    public String getIdentifier() {
        return identifier;
    }

    private UUID id;
    private String identifier;
    private UUID subjectId;
    private Language language;
}
