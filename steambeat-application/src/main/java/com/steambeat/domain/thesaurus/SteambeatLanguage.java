package com.steambeat.domain.thesaurus;

import com.google.common.base.Objects;
import com.memetix.mst.language.Language;

public class SteambeatLanguage {

    public static SteambeatLanguage forString(final String language) {
        final SteambeatLanguage result = new SteambeatLanguage();
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

    private static Language setMicrosoftCodeFor(final String language) {
        if (language.equalsIgnoreCase("French")) {
            return Language.FRENCH;
        } else if (language.equalsIgnoreCase("German")) {
            return Language.GERMAN;
        } else if (language.equalsIgnoreCase("Italian")) {
            return Language.ITALIAN;
        } else if (language.equalsIgnoreCase("Portuguese")) {
            return Language.PORTUGUESE;
        } else if (language.equalsIgnoreCase("Russian")) {
            return Language.RUSSIAN;
        } else if (language.equalsIgnoreCase("Spanish")) {
            return Language.SPANISH;
        } else if (language.equalsIgnoreCase("Swedish")) {
            return Language.SWEDISH;
        } else if (language.equalsIgnoreCase("English")) {
            return Language.ENGLISH;
        } else {
            return Language.AUTO_DETECT;
        }
    }

    public static SteambeatLanguage none() {
        final SteambeatLanguage steambeatLanguage = new SteambeatLanguage();
        steambeatLanguage.code = "none";
        return steambeatLanguage;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final SteambeatLanguage steambeatLanguage = (SteambeatLanguage) o;
        return Objects.equal(steambeatLanguage.code, code);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(code);
    }

    public String getCode() {
        return code;
    }

    public Language getMicrosoftLanguage() {
        return microsoftCode;
    }

    public static SteambeatLanguage reference() {
        return SteambeatLanguage.forString("English");
    }

    private String code;
    private Language microsoftCode;
}
