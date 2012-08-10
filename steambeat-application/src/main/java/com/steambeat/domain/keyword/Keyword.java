package com.steambeat.domain.keyword;

import com.google.common.base.Objects;
import com.steambeat.domain.BaseEntity;
import com.steambeat.domain.thesaurus.Language;
import org.joda.time.DateTime;

import java.util.UUID;

public class Keyword extends BaseEntity {

    //mongolink constructor do not delete
    public Keyword() {
    }

    public Keyword(final String value, final Language language, final UUID topic) {
        this.topic = topic;
        this.id = UUID.randomUUID();
        this.value = value;
        this.language = language;
        this.creationDate = new DateTime();
        this.lastModificationDate = this.creationDate;
    }

    @Override
    public Object getId() {
        return id;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(value, language);
    }

    @Override
    public String toString() {
        return value;
    }

    public String getValue() {
        return value;
    }

    public Language getLanguage() {
        return language;
    }

    public UUID getTopic() {
        return topic;
    }

    public DateTime getCreationDate() {
        return creationDate;
    }

    public DateTime getLastModificationDate() {
        return lastModificationDate;
    }

    private UUID id;
    private String value;
    private Language language;
    private UUID topic;
    private DateTime creationDate;
    private DateTime lastModificationDate;
}
