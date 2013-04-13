package com.feelhub.domain.topic;

import com.feelhub.domain.eventbus.DomainEventBus;
import com.feelhub.domain.thesaurus.FeelhubLanguage;
import com.feelhub.domain.topic.http.*;
import com.feelhub.domain.topic.http.uri.*;
import com.feelhub.domain.topic.real.*;
import com.feelhub.domain.topic.world.WorldTopic;
import org.restlet.data.MediaType;

import java.util.UUID;

public class TopicFactory {

    public RealTopic createRealTopic(final FeelhubLanguage feelhubLanguage, final String name, final RealTopicType type, final UUID userID) {
        final UUID id = UUID.randomUUID();
        final RealTopic realTopic = new RealTopic(id, type);
        realTopic.setUserId(userID);
        realTopic.addName(feelhubLanguage, name);
        DomainEventBus.INSTANCE.post(new RealTopicCreatedEvent(id, feelhubLanguage));
        return realTopic;
    }

    public WorldTopic createWorldTopic() {
        return new WorldTopic(UUID.randomUUID());
    }

    private void checkMediaType(final ResolverResult resolverResult, final MediaType restrictedType) {
        if (!resolverResult.getMediaType().getMainType().equalsIgnoreCase(restrictedType.getMainType())) {
            throw new TopicException();
        }
    }

    public HttpTopic createHttpTopic(final String value, final UUID userId, final UriResolver uriResolver) {
        final ResolverResult resolverResult = uriResolver.resolve(new Uri(value));
        final HttpTopic httpTopic = new HttpTopic(UUID.randomUUID());
        httpTopic.setUserId(userId);
        fillHttpTopicDatas(resolverResult, httpTopic);
        return httpTopic;
    }

    private void fillHttpTopicDatas(final ResolverResult resolverResult, final HttpTopic httpTopic) {
        httpTopic.setMediaType(resolverResult.getMediaType());
        httpTopic.addUri(getCanonical(resolverResult));
        DomainEventBus.INSTANCE.post(new HttpTopicCreatedEvent(httpTopic.getId(), resolverResult));
    }

    private Uri getCanonical(final ResolverResult resolverResult) {
        return resolverResult.getPath().get(resolverResult.getPath().size() - 1);
    }
}
