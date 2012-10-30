package com.feelhub.domain.alchemy;

import com.feelhub.domain.BaseEntity;
import com.feelhub.domain.keyword.Keyword;
import com.feelhub.domain.thesaurus.FeelhubLanguage;

import java.util.UUID;

public class AlchemyAnalysis extends BaseEntity {

    //mongolink constructor
    protected AlchemyAnalysis() {
    }

    public AlchemyAnalysis(final Keyword keyword) {
        this.id = UUID.randomUUID();
        this.referenceId = keyword.getReferenceId();
        this.value = keyword.getValue();
    }

    @Override
    public UUID getId() {
        return id;
    }

    public UUID getReferenceId() {
        return referenceId;
    }

    public String getValue() {
        return value;
    }

    public void setNewReferenceId(final UUID newReferenceId) {
        this.referenceId = newReferenceId;
    }

    public void setLanguageCode(final FeelhubLanguage language) {
        this.languageCode = language.getCode();
    }

    public String getLanguageCode() {
        return languageCode;
    }

    private UUID referenceId;
    private UUID id;
    private String value;
    private String languageCode;
}
