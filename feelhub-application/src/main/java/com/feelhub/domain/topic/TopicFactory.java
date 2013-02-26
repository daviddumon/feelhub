package com.feelhub.domain.topic;

import com.feelhub.domain.eventbus.DomainEventBus;
import com.feelhub.domain.thesaurus.FeelhubLanguage;
import com.feelhub.domain.topic.http.HttpTopic;
import com.feelhub.domain.topic.http.HttpTopicCreatedEvent;
import com.feelhub.domain.topic.http.uri.ResolverResult;
import com.feelhub.domain.topic.http.uri.Uri;
import com.feelhub.domain.topic.http.uri.UriResolver;
import com.feelhub.domain.topic.real.RealTopic;
import com.feelhub.domain.topic.real.RealTopicCreatedEvent;
import com.feelhub.domain.topic.real.RealTopicType;
import com.feelhub.domain.topic.world.WorldTopic;
import org.restlet.data.MediaType;

import java.util.UUID;

public class TopicFactory {

    public RealTopic createRealTopic(final FeelhubLanguage feelhubLanguage, final String name, final RealTopicType type, UUID userID) {
        final UUID id = UUID.randomUUID();
        final RealTopic realTopic = new RealTopic(id, type);
        realTopic.setUserId(userID);
        realTopic.addName(feelhubLanguage, name);
        DomainEventBus.INSTANCE.post(new RealTopicCreatedEvent(id));
        return realTopic;
    }

    public WorldTopic createWorldTopic() {
        return new WorldTopic(UUID.randomUUID());
    }

    public HttpTopic createHttpTopicWithMediaType(final String value, final MediaType restrictedType, UriResolver uriResolver) {
        final ResolverResult resolverResult = uriResolver.resolve(new Uri(value));
        if (restrictedType != null) {
            checkMediaType(resolverResult, restrictedType);
        }
        final HttpTopic httpTopic = new HttpTopic(UUID.randomUUID());
        httpTopic.setMediaType(restrictedType);
        fillHttpTopicDatas(uriResolver, httpTopic, new Uri(value));
        return httpTopic;
    }

    private void checkMediaType(final ResolverResult resolverResult, final MediaType restrictedType) {
        if (!resolverResult.getMediaType().getMainType().equalsIgnoreCase(restrictedType.getMainType())) {
            throw new TopicException();
        }
    }

    public HttpTopic createHttpTopic(String value, UUID userId, UriResolver resolver) {
        final HttpTopic httpTopic = new HttpTopic(UUID.randomUUID());
        httpTopic.setUserId(userId);
        Uri uri = new Uri(value);
        fillHttpTopicDatas(resolver, httpTopic, uri);
        return httpTopic;
    }

    private void fillHttpTopicDatas(UriResolver resolver, HttpTopic httpTopic, Uri uri) {
        ResolverResult resolverResult = resolver.resolve(uri);
        httpTopic.addUri(getCanonical(resolverResult));
        DomainEventBus.INSTANCE.post(new HttpTopicCreatedEvent(httpTopic.getId(), resolverResult));
    }

    private Uri getCanonical(final ResolverResult resolverResult) {
        return resolverResult.getPath().get(resolverResult.getPath().size() - 1);
    }
}
