package com.steambeat.domain.thesaurus;

import com.google.common.base.Objects;

public class Language {

    public static Language forString(String language) {
        final Language result = new Language();
        result.code = language;
        return result;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Language language = (Language) o;
        return Objects.equal(language.code, code);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(code);
    }

    private String code;
}
