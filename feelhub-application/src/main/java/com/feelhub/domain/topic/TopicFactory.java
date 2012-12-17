package com.feelhub.domain.topic;

import com.feelhub.domain.thesaurus.FeelhubLanguage;
import com.feelhub.domain.topic.http.HttpTopic;
import com.feelhub.domain.topic.http.uri.*;
import com.feelhub.domain.topic.real.*;
import com.feelhub.domain.topic.world.WorldTopic;
import com.google.inject.Inject;
import org.restlet.data.MediaType;

import java.util.UUID;

public class TopicFactory {

    @Inject
    public TopicFactory(final UriResolver uriResolver) {
        this.uriResolver = uriResolver;
    }

    public RealTopic createRealTopic(final FeelhubLanguage feelhubLanguage, final String name, final RealTopicType type) {
        final UUID id = UUID.randomUUID();
        final RealTopic realTopic = new RealTopic(id, type);
        realTopic.addName(feelhubLanguage, name);
        return realTopic;
    }

    public WorldTopic createWorldTopic() {
        return new WorldTopic(UUID.randomUUID());
    }

    public HttpTopic createHttpTopic(final String name) {
        return createHttpTopic(name, null);
    }

    public HttpTopic createHttpTopic(final String name, final MediaType restrictedType) {
        final ResolverResult resolverResult = uriResolver.resolve(new Uri(name));
        if (restrictedType != null) {
            checkMediaType(resolverResult, restrictedType);
        }
        final HttpTopic httpTopic = new HttpTopic(UUID.randomUUID());
        httpTopic.setMediaType(resolverResult.getMediaType());
        httpTopic.addUri(getCanonical(resolverResult));
        createTagsForHttpTopic(resolverResult, httpTopic);
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
                httpTopic.createTags(variation);
            }
        }
    }

    private Uri getCanonical(final ResolverResult resolverResult) {
        return resolverResult.getPath().get(resolverResult.getPath().size() - 1);
    }

    private final UriResolver uriResolver;
}
