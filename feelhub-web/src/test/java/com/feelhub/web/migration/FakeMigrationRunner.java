package com.feelhub.web.migration;

import com.feelhub.repositories.SessionProvider;
import com.google.inject.Inject;

public class FakeMigrationRunner extends MigrationRunner {

    @Inject
    public FakeMigrationRunner(final SessionProvider sessionProvider) {
        super(sessionProvider);
    }

    @Override
    public void run() {
        context.getAttributes().replace("com.feelhub.ready", true);
        Thread.currentThread().interrupt();
        return;
    }
}
