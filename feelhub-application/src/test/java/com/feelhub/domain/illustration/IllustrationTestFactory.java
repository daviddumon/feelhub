package com.feelhub.domain.illustration;

import com.feelhub.domain.topic.Topic;
import com.feelhub.repositories.Repositories;

public class IllustrationTestFactory {

    public Illustration newIllustration(final Topic topic) {
        return newIllustration(topic, "http://www.fake.com/illustration.jpg");
    }

    public Illustration newIllustration(final Topic topic, final String link) {
        final Illustration illustration = new Illustration(topic.getId(), link);
        Repositories.illustrations().add(illustration);
        return illustration;
    }
}
