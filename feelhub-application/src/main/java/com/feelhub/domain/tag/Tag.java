package com.feelhub.domain.tag;

import com.feelhub.domain.BaseEntity;
import com.feelhub.domain.thesaurus.FeelhubLanguage;
import com.google.common.base.Objects;

import java.util.UUID;

public class Tag extends BaseEntity {

    //mongolink constructor do not delete
    public Tag() {
    }

    public Tag(final String value, final FeelhubLanguage feelhubLanguage, final UUID topicId) {
        this.id = UUID.randomUUID();
        this.value = value;
        this.languageCode = feelhubLanguage.getCode();
        this.topicId = topicId;
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
        final Tag tag = (Tag) o;
        return Objects.equal(tag.value, value)
                && Objects.equal(tag.languageCode, languageCode);
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

    public FeelhubLanguage getLanguage() {
        return FeelhubLanguage.fromCode(languageCode);
    }

    public String getLanguageCode() {
        return languageCode;
    }

    public UUID getTopicId() {
        return topicId;
    }

    public void setTopicId(final UUID topicId) {
        this.topicId = topicId;
    }

    private UUID id;
    protected String value;
    private String languageCode;
    private UUID topicId;
}