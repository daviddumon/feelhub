package com.feelhub.domain.illustration;

import com.feelhub.domain.keyword.uri.Uri;
import com.feelhub.domain.topic.Topic;
import com.feelhub.repositories.Repositories;

import java.util.UUID;

public class IllustrationTestFactory {

    public Illustration newIllustration(final Topic topic) {
        return newIllustration(topic, "http://www.fake.com/illustration.jpg");
    }

    public Illustration newIllustration(final Topic topic, final String link) {
        final Illustration illustration = new Illustration(topic.getId(), link);
        Repositories.illustrations().add(illustration);
        return illustration;
    }

    public Illustration newIllustration(final UUID topicId) {
        return newIllustration(topicId, "http://www.fake.com/illustration.jpg");
    }

    public Illustration newIllustration(final UUID topicId, final String link) {
        final Illustration illustration = new Illustration(topicId, link);
        Repositories.illustrations().add(illustration);
        return illustration;
    }

    public Illustration newIllustrationFor(final Uri uri) {
        final Illustration illustration = new Illustration(uri.getTopicId(), "http://www.fake.com/illustration.jpg");
        Repositories.illustrations().add(illustration);
        return illustration;
    }
}
