package com.feelhub.domain.scraper;

import com.feelhub.domain.thesaurus.FeelhubLanguage;
import com.feelhub.domain.topic.http.HttpTopicType;
import com.google.common.collect.*;

import java.util.*;

class ScrapedInformation {

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

    public void addImage(final String illustration) {
        images.add(illustration);
    }

    public List<String> getImages() {
        return images;
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

    public void addVideo(final String video) {
        videos.add(video);
    }

    public List<String> getVideos() {
        return videos;
    }

    public void addAudio(final String audio) {
        audios.add(audio);
    }

    public List<String> getAudios() {
        return audios;
    }

    protected TreeMap<Integer, String> descriptions = Maps.newTreeMap();
    protected TreeMap<Integer, String> names = Maps.newTreeMap();
    protected TreeMap<Integer, FeelhubLanguage> languages = Maps.newTreeMap();
    private List<String> persons = Lists.newArrayList();
    protected HttpTopicType type = HttpTopicType.Website;
    private String openGraphType;
    private List<String> images = Lists.newArrayList();
    private List<String> videos = Lists.newArrayList();
    private List<String> audios = Lists.newArrayList();
}
