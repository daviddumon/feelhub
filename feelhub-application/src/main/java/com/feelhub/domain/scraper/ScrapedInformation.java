package com.feelhub.domain.scraper;

import com.feelhub.domain.thesaurus.FeelhubLanguage;
import com.feelhub.domain.topic.http.HttpTopicType;
import com.google.common.collect.*;

import java.util.*;

public class ScrapedInformation {

    public void addDescription(final int score, final String description) {
        descriptions.put(score, description);
    }

    public String getDescription() {
        if (descriptions.isEmpty()) {
            return "";
        }
        return descriptions.lastEntry().getValue();
    }

    public void addName(final int score, final String name) {
        names.put(score, name);
    }

    public String getName() {
        if (names.isEmpty()) {
            return "";
        }
        return names.lastEntry().getValue();
    }

    public void addIllustration(final int score, final String illustration) {
        illustrations.put(score, illustration);
    }

    public String getIllustration() {
        if (illustrations.isEmpty()) {
            return "";
        }
        return illustrations.lastEntry().getValue();
    }

    public void addPerson(final String person) {
        persons.add(person);
    }

    public List<String> getPersons() {
        return persons;
    }

    public void addLanguage(final int score, final FeelhubLanguage language) {
        languages.put(score, language);
    }

    public FeelhubLanguage getLanguage() {
        if (languages.isEmpty()) {
            return FeelhubLanguage.none();
        }
        return languages.lastEntry().getValue();
    }

    public void addType(final String type) {
        if (type.equalsIgnoreCase("article")) {
            this.type = HttpTopicType.Article;
        }
        this.openGraphType = type;
    }

    public HttpTopicType getType() {
        return type;
    }

    public String getOpenGraphType() {
        return openGraphType;
    }

    protected TreeMap<Integer, String> descriptions = Maps.newTreeMap();
    protected TreeMap<Integer, String> names = Maps.newTreeMap();
    protected TreeMap<Integer, String> illustrations = Maps.newTreeMap();
    protected TreeMap<Integer, FeelhubLanguage> languages = Maps.newTreeMap();
    private List<String> persons = Lists.newArrayList();
    protected HttpTopicType type = HttpTopicType.Website;
    private String openGraphType;
}
