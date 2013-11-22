package com.feelhub.domain.topic;

import com.feelhub.domain.BaseEntity;
import com.feelhub.domain.feeling.Feeling;
import com.feelhub.domain.thesaurus.FeelhubLanguage;
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
        this.hasFeelings = false;
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

    private List<Feeling> getFeelings() {
        return Repositories.feelings().forTopicId(currentId);
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

    public List<Thumbnail> getThumbnails() {
        return thumbnails;
    }

    public void addThumbnail(final Thumbnail thumbnail) {
        thumbnails.add(thumbnail);
    }

    public int getHappyFeelingCount() {
        return happyFeelingCount;
    }

    public int getSadFeelingCount() {
        return sadFeelingCount;
    }

    public int getBoredFeelingCount() {
        return boredFeelingCount;
    }

    public void increasesFeelingCount(final Feeling feeling) {
        switch (feeling.getFeelingValue()) {
            case happy:
                happyFeelingCount++;
                break;
            case sad:
                sadFeelingCount++;
                break;
            case bored:
                boredFeelingCount++;
                break;
        }
        hasFeelings = true;
        popularityCount++;
    }

    public boolean getHasFeelings() {
        return hasFeelings;
    }

    public void incrementViewCount() {
        viewCount++;
        popularityCount++;
    }

    public int getViewCount() {
        return viewCount;
    }

    public int getPopularity() {
        final int popularity;
        if (popularityCount < 5) {
            popularity = 1;
        } else if (popularityCount < 10) {
            popularity = 2;
        } else if (popularityCount < 20) {
            popularity = 3;
        } else if (popularityCount < 50) {
            popularity = 4;
        } else {
            popularity = 5;
        }
        return popularity;
    }

    public String getLanguageCode() {
        return languageCode;
    }

    public void setLanguageCode(final String languageCode) {
        this.languageCode = languageCode;
    }

    public int getPopularityCount() {
        return popularityCount;
    }

    protected UUID id;
    protected UUID currentId;
    private final Map<String, String> descriptions = Maps.newHashMap();
    private final List<String> subTypes = Lists.newArrayList();
    private final List<Uri> uris = Lists.newArrayList();
    protected Map<String, String> names = Maps.newHashMap();
    private String thumbnail = "";
    private final List<Thumbnail> thumbnails = Lists.newArrayList();
    private UUID userId;
    private TopicMerger topicMerger = new TopicMerger();
    private int happyFeelingCount;
    private int sadFeelingCount;
    private int boredFeelingCount;
    private boolean hasFeelings;
    private int viewCount;
    private String languageCode = FeelhubLanguage.none().getCode();
    private int popularityCount;
}
