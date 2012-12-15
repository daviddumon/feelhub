package com.feelhub.domain.topic;

import com.feelhub.domain.thesaurus.FeelhubLanguage;
import com.feelhub.domain.topic.http.HttpTopic;
import com.feelhub.domain.topic.http.uri.*;
import com.feelhub.domain.topic.real.*;
import com.feelhub.domain.topic.world.WorldTopic;
import com.google.inject.Inject;

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
        final ResolverResult resolverResult = uriResolver.resolve(new Uri(name));
        final HttpTopic httpTopic = new HttpTopic(UUID.randomUUID());
        httpTopic.setMediaType(resolverResult.getMediaType());
        httpTopic.addUri(getCanonical(resolverResult));
        createTagsForHttpTopic(resolverResult, httpTopic);
        return httpTopic;
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
