package com.feelhub.domain.topic;

import com.feelhub.domain.BaseEntity;
import com.feelhub.domain.feeling.*;
import com.feelhub.domain.thesaurus.FeelhubLanguage;
import com.feelhub.domain.topic.http.uri.Uri;
import com.feelhub.repositories.Repositories;
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

    @Override
    public UUID getId() {
        return id;
    }

    public UUID getCurrentId() {
        return currentId;
    }

    public void changeCurrentId(final UUID currentId) {
        if (!currentId.equals(this.currentId)) {
            this.currentId = currentId;
            topicMerger.merge(this.getCurrentId(), this.getId());
        }
    }

    public abstract TopicType getType();

    public abstract String getTypeValue();

    public void setUserId(final UUID userId) {
        this.userId = userId;
    }

    public UUID getUserId() {
        return userId;
    }

    public void addName(final FeelhubLanguage feelhubLanguage, final String name) {
        names.put(feelhubLanguage.getCode(), WordUtils.capitalizeFully(name));
    }

    public String getName(final FeelhubLanguage feelhubLanguage) {
        if (names.containsKey(feelhubLanguage.getCode())) {
            return names.get(feelhubLanguage.getCode());
        } else if (names.containsKey(FeelhubLanguage.reference().getCode())) {
            return names.get(FeelhubLanguage.reference().getCode());
        } else if (names.containsKey(FeelhubLanguage.none().getCode())) {
            return names.get(FeelhubLanguage.none().getCode());
        } else if (names.size() > 0) {
            final Iterator<String> iterator = names.values().iterator();
            return iterator.next();
        } else {
            return "";
        }
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

    public String getIllustration() {
        return illustration;
    }

    public void setIllustration(final String illustration) {
        this.illustration = illustration;
    }

    public void setTopicMerger(final TopicMerger topicMerger) {
        this.topicMerger = topicMerger;
    }

    public int getSentimentScore() {
        return new TopicSentimentScoreCalculator().sentimentScore(getSentiments());
    }

    private List<Sentiment> getSentiments() {
        List<Sentiment> sentiments = Lists.newArrayList();
        List<Feeling> feelings = Repositories.feelings().forTopicId(currentId);
        for (Feeling feeling : feelings) {
            for (Sentiment sentiment : feeling.getSentiments()) {
                sentiments.add(sentiment);
            }
        }
        return sentiments;
    }

    protected UUID id;
    protected UUID currentId;
    private UUID userId;
    protected Map<String, String> names = Maps.newHashMap();
    private final Map<String, String> descriptions = Maps.newHashMap();
    private final List<String> subTypes = Lists.newArrayList();
    private final List<Uri> uris = Lists.newArrayList();
    private String illustration = "";
    private TopicMerger topicMerger = new TopicMerger();

}
