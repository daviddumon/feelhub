package com.feelhub.application;

import com.feelhub.domain.scraper.Scraper;
import com.feelhub.domain.tag.*;
import com.feelhub.domain.thesaurus.FeelhubLanguage;
import com.feelhub.domain.topic.*;
import com.feelhub.domain.topic.http.HttpTopic;
import com.feelhub.domain.topic.http.uri.*;
import com.feelhub.domain.topic.real.*;
import com.feelhub.domain.user.User;
import com.feelhub.repositories.Repositories;
import com.google.common.collect.Lists;
import com.google.inject.Inject;
import org.restlet.data.MediaType;

import java.util.*;

public class TopicService {

    @Inject
    public TopicService(final TopicFactory topicFactory, final Scraper scraper, final TagService tagService, final UriResolver uriResolver) {
        this.topicFactory = topicFactory;
        this.scraper = scraper;
        this.tagService = tagService;
        this.uriResolver = uriResolver;
    }

    public Topic lookUp(final UUID id) {
        final Topic topic = Repositories.topics().get(id);
        if (topic == null) {
            throw new TopicNotFound();
        }
        return topic;
    }

    public Topic lookUpCurrent(final UUID id) {
        final Topic topic = Repositories.topics().getCurrentTopic(id);
        if (topic == null) {
            throw new TopicNotFound();
        }
        return topic;
    }

    public Topic lookUp(final String value, final RealTopicType type) {
        try {
            final Tag tag = tagService.lookUp(value);
            for (final UUID id : tag.getTopicIds()) {
                try {
                    final Topic topic = lookUpCurrent(id);
                    if (topic.getType().equals(type)) {
                        return topic;
                    }
                } catch (TopicNotFound e) {
                }
            }
        } catch (TagNotFoundException e) {
        }
        return null;
    }

    public RealTopic createRealTopic(final FeelhubLanguage feelhubLanguage, final String name, final RealTopicType type, final User user) {
        final RealTopic realTopic = createRealTopic(feelhubLanguage, name, type);
        realTopic.setUserId(user.getId());
        return realTopic;
    }

    public RealTopic createRealTopic(final FeelhubLanguage feelhubLanguage, final String name, final RealTopicType type) {
        final RealTopic realTopic = topicFactory.createRealTopic(feelhubLanguage, name, type);
        index(realTopic, name);
        Repositories.topics().add(realTopic);
        return realTopic;
    }

    public HttpTopic createHttpTopic(final String value, final User user) {
        final ResolverResult resolverResult = uriResolver.resolve(new Uri(value));
        final HttpTopic httpTopic = createHttpTopic(resolverResult);
        httpTopic.setUserId(user.getId());
        return httpTopic;
    }

    public HttpTopic createHttpTopic(final String value, final MediaType restrictedType) {
        final ResolverResult resolverResult = uriResolver.resolve(new Uri(value));
        if (restrictedType != null) {
            checkMediaType(resolverResult, restrictedType);
        }
        return createHttpTopic(resolverResult);
    }

    private HttpTopic createHttpTopic(final ResolverResult resolverResult) {
        final HttpTopic httpTopic = topicFactory.createHttpTopic(resolverResult);
        createTagsForHttpTopic(resolverResult, httpTopic);
        Repositories.topics().add(httpTopic);
        return httpTopic;
    }

    private void checkMediaType(final ResolverResult resolverResult, final MediaType restrictedType) {
        if (!resolverResult.getMediaType().getMainType().equalsIgnoreCase(restrictedType.getMainType())) {
            throw new TopicException();
        }
    }

    private void createTagsForHttpTopic(final ResolverResult resolverResult, final HttpTopic httpTopic) {
        for (final Uri uri : resolverResult.getPath()) {
            for (final String variation : uri.getVariations()) {
                index(httpTopic, variation);
            }
        }
    }

    public List<Topic> getTopics(final String value) {
        final List<Topic> topics = Lists.newArrayList();
        try {
            final Tag tag = tagService.lookUp(value);
            for (final UUID id : tag.getTopicIds()) {
                try {
                    topics.add(lookUpCurrent(id));
                } catch (TopicNotFound e) {
                }
            }
        } catch (TagNotFoundException e) {
        }
        return topics;
    }

    public void index(final Topic topic, final String value) {
        final Tag tag = lookUpOrCreateTag(value);
        if (topic.getType().hasTagUniqueness()) {
            addTopicIfNotPresent(topic, tag);
        } else {
            addTopicToTag(topic, tag);
        }
    }

    private void addTopicIfNotPresent(final Topic topic, final Tag tag) {
        for (final UUID id : tag.getTopicIds()) {
            final Topic existingTopic = lookUpCurrent(id);
            if (existingTopic.getType().equals(topic.getType())) {
                topic.changeCurrentId(existingTopic.getId());
                return;
            }
        }
        tag.addTopic(topic);
    }

    private void addTopicToTag(final Topic topic, final Tag tag) {
        if (!tag.getTopicIds().contains(topic.getId())) {
            tag.addTopic(topic);
        }
    }

    private Tag lookUpOrCreateTag(final String description) {
        try {
            return tagService.lookUp(description);
        } catch (TagNotFoundException e) {
            return tagService.createTag(description);
        }
    }

    private final Scraper scraper;
    private final TopicFactory topicFactory;
    private final TagService tagService;
    private UriResolver uriResolver;
}
