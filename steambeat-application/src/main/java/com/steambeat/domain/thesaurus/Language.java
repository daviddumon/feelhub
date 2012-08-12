package com.steambeat.domain.thesaurus;

import com.google.common.base.Objects;

public class Language {

    public static Language forString(final String language) {
        final Language result = new Language();
        result.code = setCodeFor(language).toLowerCase().trim();
        result.microsoftCode = setMicrosoftCodeFor(language);
        return result;
    }

    private static String setCodeFor(final String value) {
        if (value.equalsIgnoreCase("French")) {
            return "fr";
        } else if (value.equalsIgnoreCase("German")) {
            return "de";
        } else if (value.equalsIgnoreCase("Italian")) {
            return "it";
        } else if (value.equalsIgnoreCase("Portuguese")) {
            return "pt";
        } else if (value.equalsIgnoreCase("Russian")) {
            return "ru";
        } else if (value.equalsIgnoreCase("Spanish")) {
            return "es";
        } else if (value.equalsIgnoreCase("Swedish")) {
            return "sv";
        } else if (value.equalsIgnoreCase("English")) {
            return "en";
        } else {
            return value;
        }
    }

    private static com.memetix.mst.language.Language setMicrosoftCodeFor(final String language) {
        if (language.equalsIgnoreCase("French")) {
            return com.memetix.mst.language.Language.FRENCH;
        } else if (language.equalsIgnoreCase("German")) {
            return com.memetix.mst.language.Language.GERMAN;
        } else if (language.equalsIgnoreCase("Italian")) {
            return com.memetix.mst.language.Language.ITALIAN;
        } else if (language.equalsIgnoreCase("Portuguese")) {
            return com.memetix.mst.language.Language.PORTUGUESE;
        } else if (language.equalsIgnoreCase("Russian")) {
            return com.memetix.mst.language.Language.RUSSIAN;
        } else if (language.equalsIgnoreCase("Spanish")) {
            return com.memetix.mst.language.Language.SPANISH;
        } else if (language.equalsIgnoreCase("Swedish")) {
            return com.memetix.mst.language.Language.SWEDISH;
        } else if (language.equalsIgnoreCase("English")) {
            return com.memetix.mst.language.Language.ENGLISH;
        } else {
            return com.memetix.mst.language.Language.AUTO_DETECT;
        }
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

    public com.memetix.mst.language.Language getMicrosoftLanguage() {
        return microsoftCode;
    }

    public static Language reference() {
        return Language.forString("English");
    }

    private String code;
    private com.memetix.mst.language.Language microsoftCode;
}
