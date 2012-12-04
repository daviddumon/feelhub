package com.feelhub.domain.topic;

import com.feelhub.domain.BaseEntity;
import com.feelhub.domain.eventbus.DomainEventBus;
import com.feelhub.domain.tag.TagRequestEvent;
import com.feelhub.domain.thesaurus.FeelhubLanguage;
import com.google.common.collect.*;

import java.util.*;

public class Topic extends BaseEntity {

    //mongolink constructor do not delete!
    protected Topic() {
    }

    public Topic(final UUID id) {
        this.id = id;
        this.currentTopicId = id;
    }

    @Override
    public UUID getId() {
        return id;
    }

    public void setType(final TopicType type) {
        this.type = type;
    }

    public TopicType getType() {
        return type;
    }

    public List<String> getSubTypes() {
        return subTypes;
    }

    public void addSubType(final String subtype) {
        subTypes.add(subtype);
    }

    public List<String> getUrls() {
        return urls;
    }

    public void addUrl(final String url) {
        urls.add(url);
    }

    public void addDescription(final FeelhubLanguage language, final String description) {
        descriptions.put(language.getCode(), description);
        final TagRequestEvent tagRequestEvent = new TagRequestEvent(this);
        DomainEventBus.INSTANCE.post(tagRequestEvent);
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

    public Map<String, String> getDescriptions() {
        return descriptions;
    }

    public void setUserId(final UUID userId) {
        this.userId = userId;
    }

    public UUID getUserId() {
        return userId;
    }

    public UUID getCurrentTopicId() {
        return currentTopicId;
    }

    public void changeCurrentTopicId(final UUID currentTopicId) {
        this.currentTopicId = currentTopicId;
    }

    private UUID id;
    private TopicType type;
    private List<String> subTypes = Lists.newArrayList();
    private List<String> urls = Lists.newArrayList();
    private Map<String, String> descriptions = Maps.newHashMap();
    private UUID userId;
    private UUID currentTopicId;
}
