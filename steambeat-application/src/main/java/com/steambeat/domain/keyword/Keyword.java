package com.steambeat.domain.keyword;

import com.google.common.base.Objects;
import com.steambeat.domain.BaseEntity;
import com.steambeat.domain.thesaurus.SteambeatLanguage;

import java.util.UUID;

public class Keyword extends BaseEntity {

    //mongolink constructor do not delete
    public Keyword() {
    }

    public Keyword(final String value, final SteambeatLanguage steambeatLanguage, final UUID referenceId) {
        this.id = UUID.randomUUID();
        this.value = value;
        this.languageCode = steambeatLanguage.getCode();
        this.referenceId = referenceId;
    }

    @Override
    public Object getId() {
        return id;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final Keyword keyword = (Keyword) o;
        return Objects.equal(keyword.value, value)
                && Objects.equal(keyword.languageCode, languageCode);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(value, languageCode);
    }

    @Override
    public String toString() {
        return value;
    }

    public String getValue() {
        return value;
    }

    public SteambeatLanguage getLanguage() {
        return SteambeatLanguage.forString(languageCode);
    }

    public String getLanguageCode() {
        return languageCode;
    }

    public UUID getReferenceId() {
        return referenceId;
    }

    public void setReferenceId(final UUID referenceId) {
        this.referenceId = referenceId;
    }

    private UUID id;
    private String value;
    private String languageCode;
    private UUID referenceId;
}
