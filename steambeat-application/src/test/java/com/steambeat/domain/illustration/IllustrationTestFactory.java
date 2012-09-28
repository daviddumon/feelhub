package com.steambeat.domain.illustration;

import com.steambeat.domain.reference.Reference;
import com.steambeat.repositories.Repositories;

public class IllustrationTestFactory {

    public Illustration newIllustration(final Reference reference) {
        return newIllustration(reference, "http://www.fake.com/illustration.jpg");
    }

    public Illustration newIllustration(final Reference reference, final String link) {
        final Illustration illustration = new Illustration(reference.getId(), link);
        Repositories.illustrations().add(illustration);
        return illustration;
    }
}
