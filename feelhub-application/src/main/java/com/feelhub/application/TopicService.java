package com.feelhub.application;

import com.feelhub.domain.eventbus.DomainEventBus;
import com.feelhub.domain.scraper.HttpTopicAnalyzeRequest;
import com.feelhub.domain.tag.*;
import com.feelhub.domain.thesaurus.FeelhubLanguage;
import com.feelhub.domain.topic.*;
import com.feelhub.domain.topic.http.*;
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
    public TopicService(final TagService tagService, final UriResolver uriResolver) {
        this.topicFactory = new TopicFactory();
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

    public Topic lookUpRealTopic(final String value, final RealTopicType type, final FeelhubLanguage language) {
        try {
            final Tag tag = tagService.lookUp(value);
            for (final UUID id : tag.getTopicsIdFor(language)) {
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

    public List<Topic> getTopics(final String value, final FeelhubLanguage language) {
        final List<Topic> topics = Lists.newArrayList();
        try {
            final Tag tag = tagService.lookUp(value);
            addTopicsForLanguage(language, topics, tag);
            addTopicsForLanguage(FeelhubLanguage.none(), topics, tag);
        } catch (TagNotFoundException e) {
        }
        return topics;
    }

    private void addTopicsForLanguage(final FeelhubLanguage language, final List<Topic> topics, final Tag tag) {
        for (final UUID id : tag.getTopicsIdFor(language)) {
            try {
                topics.add(lookUpCurrent(id));
            } catch (TopicNotFound e) {
            }
        }
    }

    public Topic lookUpCurrent(final UUID id) {
        final Topic topic = Repositories.topics().getCurrentTopic(id);
        if (topic == null) {
            throw new TopicNotFound();
        }
        return topic;
    }

    public RealTopic createRealTopic(final FeelhubLanguage feelhubLanguage, final String name, final RealTopicType type, final User user) {
        final RealTopic realTopic = createRealTopic(feelhubLanguage, name, type);
        realTopic.setUserId(user.getId());
        return realTopic;
    }

    public RealTopic createRealTopic(final FeelhubLanguage feelhubLanguage, final String name, final RealTopicType type) {
        final RealTopic realTopic = topicFactory.createRealTopic(feelhubLanguage, name, type);
        index(realTopic, name, feelhubLanguage);
        Repositories.topics().add(realTopic);
        return realTopic;
    }

    public HttpTopic createHttpTopic(final String value, final UUID userId) {
        final ResolverResult resolverResult = uriResolver.resolve(new Uri(value));
        final HttpTopic httpTopic = topicFactory.createHttpTopic(resolverResult);
        httpTopic.setUserId(userId);
        scrapHttpTopic(httpTopic);
        Repositories.topics().add(httpTopic);
        createTagsForHttpTopic(resolverResult, httpTopic);
        return httpTopic;
    }

    public HttpTopic createHttpTopicWithRestrictedMediaType(final String value, final MediaType restrictedType) {
        final ResolverResult resolverResult = uriResolver.resolve(new Uri(value));
        if (restrictedType != null) {
            checkMediaType(resolverResult, restrictedType);
        }
        final HttpTopic httpTopic = topicFactory.createHttpTopic(resolverResult);
        scrapHttpTopic(httpTopic);
        Repositories.topics().add(httpTopic);
        createTagsForHttpTopic(resolverResult, httpTopic);
        return httpTopic;
    }

    private void scrapHttpTopic(final HttpTopic httpTopic) {
        if (httpTopic.getType().equals(HttpTopicType.Website)) {
            final HttpTopicAnalyzeRequest httpTopicAnalyzeRequest = new HttpTopicAnalyzeRequest();
            httpTopicAnalyzeRequest.setHttpTopic(httpTopic);
            DomainEventBus.INSTANCE.post(httpTopicAnalyzeRequest);
        }
    }

    private void checkMediaType(final ResolverResult resolverResult, final MediaType restrictedType) {
        if (!resolverResult.getMediaType().getMainType().equalsIgnoreCase(restrictedType.getMainType())) {
            throw new TopicException();
        }
    }

    private void createTagsForHttpTopic(final ResolverResult resolverResult, final HttpTopic httpTopic) {
        for (final Uri uri : resolverResult.getPath()) {
            for (final String variation : uri.getVariations()) {
                index(httpTopic, variation, FeelhubLanguage.none());
            }
        }
    }

    public void index(final Topic topic, final String value, final FeelhubLanguage language) {
        final Tag tag = lookUpOrCreateTag(value);
        if (topic.getType().isTranslatable()) {
            indexForLanguage(topic, tag, language);
        } else {
            indexForLanguage(topic, tag, FeelhubLanguage.none());
        }
    }

    private void indexForLanguage(final Topic topic, final Tag tag, final FeelhubLanguage language) {
        if (topic.getType().hasTagUniqueness()) {
            addTopicIfNotPresent(topic, tag, language);
        } else {
            addTopicToTag(topic, tag, language);
        }
    }

    private void addTopicIfNotPresent(final Topic topic, final Tag tag, final FeelhubLanguage language) {
        for (final UUID id : tag.getTopicsIdFor(language)) {
            final Topic existingTopic = lookUpCurrent(id);
            if (existingTopic.getType().equals(topic.getType())) {
                topic.changeCurrentId(existingTopic.getId());
                return;
            }
        }
        tag.addTopic(topic, language);
    }

    private void addTopicToTag(final Topic topic, final Tag tag, final FeelhubLanguage language) {
        if (!tag.getTopicsIdFor(language).contains(topic.getId())) {
            tag.addTopic(topic, language);
        }
    }

    private Tag lookUpOrCreateTag(final String name) {
        try {
            return tagService.lookUp(name);
        } catch (TagNotFoundException e) {
            return tagService.createTag(name);
        }
    }

    private final TopicFactory topicFactory;
    private final TagService tagService;
    private UriResolver uriResolver;
}
