package com.feelhub.domain.topic;

import com.feelhub.domain.thesaurus.FeelhubLanguage;
import com.feelhub.domain.topic.http.HttpTopic;
import com.feelhub.domain.topic.http.uri.*;
import com.feelhub.domain.topic.real.*;
import com.feelhub.domain.topic.world.WorldTopic;

import java.util.UUID;

public class TopicFactory {

    public RealTopic createRealTopic(final FeelhubLanguage feelhubLanguage, final String name, final RealTopicType type) {
        final UUID id = UUID.randomUUID();
        final RealTopic realTopic = new RealTopic(id, type);
        realTopic.addName(feelhubLanguage, name);
        return realTopic;
    }

    public WorldTopic createWorldTopic() {
        return new WorldTopic(UUID.randomUUID());
    }

    public HttpTopic createHttpTopic(final String name, final ResolverResult resolverResult) {
        final HttpTopic httpTopic = new HttpTopic(UUID.randomUUID());
        httpTopic.setMediaType(resolverResult.getMediaType());
        httpTopic.addUri(getCanonical(resolverResult));
        return httpTopic;
    }

    private Uri getCanonical(final ResolverResult resolverResult) {
        return resolverResult.getPath().get(resolverResult.getPath().size() - 1);
    }
}
