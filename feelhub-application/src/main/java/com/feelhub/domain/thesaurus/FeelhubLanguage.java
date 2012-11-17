package com.feelhub.domain.thesaurus;

import com.google.common.base.Objects;
import com.google.common.collect.Maps;
import com.memetix.mst.language.Language;
import org.apache.commons.lang.LocaleUtils;

import java.util.Map;

public class FeelhubLanguage {

    public static FeelhubLanguage fromCode(String code) {
        try {
            return new FeelhubLanguage(LocaleUtils.toLocale(code).getLanguage());
        } catch (Exception e) {
            return FeelhubLanguage.none();
        }
    }

    public static FeelhubLanguage fromCountryName(final String language) {
        return fromCode(feelhubCodeFor(language.toLowerCase().trim()));
    }

    private static String feelhubCodeFor(final String value) {
        if(alchemyLanguagues.containsKey(value)) {
            return alchemyLanguagues.get(value);
        }
        return value;
    }

    public FeelhubLanguage(String code) {
        this.code = code;
    }

    public static FeelhubLanguage reference() {
        return new FeelhubLanguage("en");
    }

    public static FeelhubLanguage none() {
        return NULL_FEELHUB_LANGUAGE;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || !getClass().isAssignableFrom(o.getClass())) {
            return false;
        }
        final FeelhubLanguage feelhubLanguage = (FeelhubLanguage) o;
        return Objects.equal(feelhubLanguage.code, code);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(code);
    }

    @Override
    public String toString() {
        return code;
    }

    public String getCode() {
        return code;
    }

    public Language getMicrosoftLanguage() {
        return Language.fromString(code);
    }
    private String code;
    private static final NullFeelhubLanguage NULL_FEELHUB_LANGUAGE = new NullFeelhubLanguage();

    private static final Map<String, String> alchemyLanguagues = Maps.newConcurrentMap();

    static {
        alchemyLanguagues.put("french", "fr");
        alchemyLanguagues.put("german", "de");
        alchemyLanguagues.put("italian", "it");
        alchemyLanguagues.put("portuguese", "pt");
        alchemyLanguagues.put("russian", "ru");
        alchemyLanguagues.put("spanish", "es");
        alchemyLanguagues.put("swedish", "sv");
        alchemyLanguagues.put("english", "en");
    }
}
