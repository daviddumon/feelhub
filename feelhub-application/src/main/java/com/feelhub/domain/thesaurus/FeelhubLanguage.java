package com.feelhub.domain.thesaurus;

import com.google.common.base.Objects;
import com.google.common.base.Strings;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.memetix.mst.language.Language;
import org.apache.commons.lang.LocaleUtils;
import org.apache.commons.lang.StringUtils;

import java.util.*;


public class FeelhubLanguage {

    public static FeelhubLanguage fromCountryName(final String language) {
        return fromCode(feelhubCodeFor(language.toLowerCase().trim()));
    }

    public static FeelhubLanguage fromCode(final String code) {
        try {
            return new FeelhubLanguage(LocaleUtils.toLocale(code).getLanguage());
        } catch (Exception e) {
            return FeelhubLanguage.none();
        }
    }

    private static String feelhubCodeFor(final String value) {
        if (alchemyLanguagues.containsKey(value)) {
            return alchemyLanguagues.get(value);
        }
        return value;
    }

    public static List<FeelhubLanguage> availables() {
        return AVAILABLES;
    }

    public static FeelhubLanguage reference() {
        return REFERENCE;
    }

    public static FeelhubLanguage none() {
        return NULL_FEELHUB_LANGUAGE;
    }

    public FeelhubLanguage(final String code) {
        this.code = code;
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

    public boolean isReference() {
        return equals(reference());
    }

    public boolean isNone() {
        return equals(none());
    }

    public String getName() {
        return LocaleUtils.toLocale(code).getDisplayName(Locale.ENGLISH);
    }

    public String getLocalizedName() {
        Locale locale = LocaleUtils.toLocale(code);
        return StringUtils.capitalize(locale.getDisplayName(locale));
    }


    private String code;

    private static final NullFeelhubLanguage NULL_FEELHUB_LANGUAGE = new NullFeelhubLanguage();

    public static final FeelhubLanguage REFERENCE = new FeelhubLanguage("en");

    private static final Map<String, String> alchemyLanguagues = Maps.newConcurrentMap();

    private static final List<FeelhubLanguage> AVAILABLES = Lists.newArrayList();

    static {
        alchemyLanguagues.put("french", "fr");
        alchemyLanguagues.put("german", "de");
        alchemyLanguagues.put("italian", "it");
        alchemyLanguagues.put("portuguese", "pt");
        alchemyLanguagues.put("russian", "ru");
        alchemyLanguagues.put("spanish", "es");
        alchemyLanguagues.put("swedish", "sv");
        alchemyLanguagues.put("english", "en");

        for (final Locale locale : Locale.getAvailableLocales()) {
            if (!Strings.isNullOrEmpty(locale.getDisplayName(Locale.ENGLISH))) {
                final FeelhubLanguage language = new FeelhubLanguage(locale.getLanguage());
                if (!AVAILABLES.contains(language)) {
                    AVAILABLES.add(language);
                }
            }
        }
        Collections.sort(AVAILABLES, new Comparator<FeelhubLanguage>() {
            @Override
            public int compare(final FeelhubLanguage feelhubLanguage, final FeelhubLanguage feelhubLanguage2) {
                return feelhubLanguage.getName().compareTo(feelhubLanguage2.getName());
            }
        });
    }
}
