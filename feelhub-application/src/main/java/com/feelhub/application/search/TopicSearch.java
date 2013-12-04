package com.feelhub.application.search;

import com.feelhub.domain.tag.Tag;
import com.feelhub.domain.thesaurus.FeelhubLanguage;
import com.feelhub.domain.topic.Topic;
import com.feelhub.domain.topic.TopicNotFound;
import com.feelhub.domain.topic.real.RealTopicType;
import com.feelhub.repositories.Repositories;
import com.google.common.base.Optional;
import com.google.common.collect.Lists;

import java.util.List;
import java.util.UUID;

public class TopicSearch {

    public Topic get(final UUID id) {
        final Topic topic = Repositories.topics().get(id);
        if (topic == null) {
            throw new TopicNotFound();
        }
        return topic;
    }

    public Optional<Topic> lookUpTopic(final String value, final RealTopicType type, final FeelhubLanguage language) {
        final Optional<Tag> tag = lookUpTag(value);
        if (!tag.isPresent()) {
            return Optional.absent();
        }
        for (final UUID id : tag.get().getTopicsIdFor(language)) {
            final Optional<Topic> topic = resolveCurrent(id);
            if (!topic.isPresent()) {
                return topic;
            }
            if (topic.get().getType().equals(type)) {
                return topic;
            }

        }

        return Optional.absent();
    }

    private Optional<Tag> lookUpTag(String value) {
        return Optional.fromNullable(Repositories.tags().get(value.toLowerCase()));
    }

    private Optional<Topic> resolveCurrent(UUID id) {
        return Optional.fromNullable(Repositories.topics().getCurrentTopic(id));
    }

    public List<Topic> findTopics(final String value, final FeelhubLanguage language) {
        final List<Topic> topics = Lists.newArrayList();
        final Optional<Tag> tag = lookUpTag(value);
        if(tag.isPresent()) {
            topics.addAll(topicsForLanguage(language, tag.get()));
            topics.addAll(topicsForLanguage(FeelhubLanguage.none(), tag.get()));
        }

        return topics;
    }

    private List<Topic> topicsForLanguage(final FeelhubLanguage language, final Tag tag) {
        List<Topic> result = Lists.newArrayList();
        for (final UUID id : tag.getTopicsIdFor(language)) {
            try {
                result.add(lookUpCurrent(id));
            } catch (TopicNotFound e) {
            }
        }
        return result;
    }

    public Topic lookUpCurrent(final UUID id) {
        Optional<Topic> result = resolveCurrent(id);
        if (!result.isPresent()) {
            throw new TopicNotFound();
        }
        return result.get();
    }

}
