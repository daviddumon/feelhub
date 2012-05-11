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
        this.weight = 1;
    }

    public Association(final Identifier identifier, final UUID uuid, final Language language) {
        this.id = UUID.randomUUID();
        this.identifier = identifier.toString();
        this.subjectId = uuid;
        this.language = language;
        this.weight = 1;
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

    public int getWeight() {
        return weight;
    }

    public void use() {
        this.weight++;
    }

    private UUID id;
    private String identifier;
    private UUID subjectId;
    private Language language;
    private int weight;
}
