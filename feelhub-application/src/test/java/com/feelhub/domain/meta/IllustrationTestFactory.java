package com.feelhub.domain.meta;

import com.feelhub.repositories.Repositories;

import java.util.UUID;

public class IllustrationTestFactory {

    public Illustration newIllustration(final UUID topicId) {
        return newIllustration(topicId, "http://www.fake.com/illustration.jpg");
    }

    public Illustration newIllustration(final UUID topicId, final String link) {
        final Illustration illustration = new Illustration(topicId, link);
        Repositories.illustrations().add(illustration);
        return illustration;
    }
}
