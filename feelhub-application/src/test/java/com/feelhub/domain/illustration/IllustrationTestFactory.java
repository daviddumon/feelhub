package com.feelhub.domain.illustration;

import com.feelhub.domain.reference.Reference;
import com.feelhub.repositories.Repositories;

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
