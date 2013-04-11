package com.feelhub.domain.topic;

import com.feelhub.domain.BaseEntity;
import com.feelhub.domain.feeling.*;
import com.feelhub.domain.thesaurus.FeelhubLanguage;
import com.feelhub.domain.topic.http.HttpTopic;
import com.feelhub.domain.topic.http.uri.Uri;
import com.feelhub.repositories.Repositories;
import com.google.common.base.Objects;
import com.google.common.collect.*;
import org.apache.commons.lang.WordUtils;

import java.util.*;


public abstract class Topic extends BaseEntity {

    //mongolink
    protected Topic() {
    }

    public Topic(final UUID id) {
        this.id = id;
        this.currentId = id;
    }

    public void changeCurrentId(final UUID currentId) {
        if (!currentId.equals(this.currentId)) {
            this.currentId = currentId;
            topicMerger.merge(this.getCurrentId(), this.getId());
        }
    }

    public UUID getCurrentId() {
        return currentId;
    }

    @Override
    public UUID getId() {
        return id;
    }

    public abstract TopicType getType();

    public abstract String getTypeValue();

    public UUID getUserId() {
        return userId;
    }

    public void setUserId(final UUID userId) {
        this.userId = userId;
    }

    public void addName(final FeelhubLanguage feelhubLanguage, final String name) {
        names.put(feelhubLanguage.getCode(), WordUtils.capitalizeFully(name));
    }

    public String getName(final FeelhubLanguage feelhubLanguage) {
        if (names.containsKey(feelhubLanguage.getCode())) {
            return names.get(feelhubLanguage.getCode());
        }
        if (names.containsKey(FeelhubLanguage.reference().getCode())) {
            return names.get(FeelhubLanguage.reference().getCode());
        }
        if (names.containsKey(FeelhubLanguage.none().getCode())) {
            return names.get(FeelhubLanguage.none().getCode());
        }
        if (names.size() > 0) {
            return Iterables.get(names.values(), 0);
        }
        return "";
    }

    public void addDescription(final FeelhubLanguage language, final String description) {
        descriptions.put(language.getCode(), description);
    }

    public String getDescription(final FeelhubLanguage language) {
        if (descriptions.containsKey(language.getCode())) {
            return descriptions.get(language.getCode());
        } else if (descriptions.containsKey(FeelhubLanguage.reference().getCode())) {
            return descriptions.get(FeelhubLanguage.reference().getCode());
        } else if (descriptions.containsKey(FeelhubLanguage.none().getCode())) {
            return descriptions.get(FeelhubLanguage.none().getCode());
        } else {
            return "";
        }
    }

    public Map<String, String> getNames() {
        return names;
    }

    public Map<String, String> getDescriptions() {
        return descriptions;
    }

    public List<String> getSubTypes() {
        return subTypes;
    }

    public void addSubType(final String subtype) {
        subTypes.add(subtype);
    }

    public List<Uri> getUris() {
        return uris;
    }

    public void addUri(final Uri uri) {
        uris.add(uri);
    }

    public void setTopicMerger(final TopicMerger topicMerger) {
        this.topicMerger = topicMerger;
    }

    public int getSentimentScore() {
        return new TopicSentimentScoreCalculator().sentimentScore(getSentiments());
    }

    private List<Sentiment> getSentiments() {
        final List<Sentiment> sentiments = Lists.newArrayList();
        final List<Feeling> feelings = Repositories.feelings().forTopicId(currentId);
        for (final Feeling feeling : feelings) {
            for (final Sentiment sentiment : feeling.getSentiments()) {
                if (sentiment.getTopicId() != null && sentiment.getTopicId().equals(currentId)) {
                    sentiments.add(sentiment);
                }
            }
        }
        return sentiments;
    }

    public void setIllustrationAndThumbnail(final HttpTopic image) {
        if (getIllustration().isEmpty()) {
            setIllustration(image.getIllustration());
            setThumbnail(image.getThumbnail());
        }
    }

    public String getIllustration() {
        return illustration;
    }

    public void setIllustration(final String illustration) {
        this.illustration = illustration;
    }

    @Override
    public String toString() {
        return Objects.toStringHelper(this).add("Id", getId()).add("Name", getName(FeelhubLanguage.REFERENCE))
                .add("Type", getType()).toString();
    }

    public boolean hasUser() {
        return userId != null;
    }

    public String getThumbnail() {
        return thumbnail;
    }

    public void setThumbnail(final String thumbnail) {
        this.thumbnail = thumbnail;
    }

    private final Map<String, String> descriptions = Maps.newHashMap();
    private final List<String> subTypes = Lists.newArrayList();
    private final List<Uri> uris = Lists.newArrayList();
    protected UUID id;
    protected UUID currentId;
    protected Map<String, String> names = Maps.newHashMap();
    private UUID userId;
    private String illustration = "";
    private TopicMerger topicMerger = new TopicMerger();
    private String thumbnail;
}
