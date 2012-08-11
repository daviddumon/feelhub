package com.steambeat.domain.thesaurus;

import com.google.common.base.Objects;

public class Language {

    public static Language forString(final String language) {
        final Language result = new Language();
        result.code = language.toLowerCase().trim();
        return result;
    }

    public static Language none() {
        final Language language = new Language();
        language.code = "none";
        return language;
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

    public String getCode() {
        return code;
    }

    private String code;
}
