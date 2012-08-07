package com.steambeat.domain.keyword;

import com.google.common.base.Objects;
import com.steambeat.domain.thesaurus.Language;

public class Keyword {

    public Keyword(final String value, final Language language) {
        this.value = value;
        this.language = language;
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
                && Objects.equal(keyword.language, language);
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

    private String value;
    private Language language;
}
