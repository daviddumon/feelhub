package com.steambeat.domain.analytics.alchemy.thesaurus;

import com.google.common.base.Objects;

public class Language {

    public static Language forString(final String language) {
        final Language result = new Language();
        result.code = language;
        return result;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final Language language = (Language) o;
        return Objects.equal(language.code, code);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(code);
    }

    private String code;
}
