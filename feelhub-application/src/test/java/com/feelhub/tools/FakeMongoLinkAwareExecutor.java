package com.feelhub.tools;

import com.feelhub.repositories.SessionProvider;
import com.google.inject.Inject;

public class FakeMongoLinkAwareExecutor extends MongoLinkAwareExecutor {

    @Inject
    public FakeMongoLinkAwareExecutor(final SessionProvider sessionProvider) {
        super(sessionProvider);
    }

    @Override
    public void execute(final Runnable runnable) {
        runnable.run();
    }
}
