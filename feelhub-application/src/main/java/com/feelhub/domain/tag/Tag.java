package com.feelhub.domain.tag;

import com.feelhub.domain.BaseEntity;
import com.feelhub.domain.thesaurus.FeelhubLanguage;
import com.feelhub.domain.topic.Topic;
import com.google.common.collect.Lists;

import java.util.*;

public class Tag extends BaseEntity {

    //mongolink constructor do not delete
    public Tag() {
    }

    public Tag(final String tagValue) {
        this.id = tagValue;
    }

    @Override
    public String getId() {
        return id;
    }

    @Override
    public String toString() {
        return id;
    }

    public void addTopic(final Topic topic, final FeelhubLanguage language) {
        final TagItem tagItem = new TagItem();
        tagItem.setId(topic.getId());
        tagItem.setLanguageCode(language.getCode());
        topicIds.add(tagItem);
    }

    public List<TagItem> getTopicIds() {
        return topicIds;
    }

    public List<UUID> getTopicsIdFor(final FeelhubLanguage language) {
        List<UUID> results = Lists.newArrayList();
        for (TagItem item : topicIds) {
            if (item.getLanguageCode().equals(language.getCode())) {
                results.add(item.getId());
            }
        }
        return results;
    }

    private String id;
    private List<TagItem> topicIds = Lists.newArrayList();
}
