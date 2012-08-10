package com.steambeat.web.migration;

import com.google.inject.Inject;
import com.steambeat.repositories.SessionProvider;

public class FakeMigrationRunner extends MigrationRunner {

    @Inject
    public FakeMigrationRunner(final SessionProvider sessionProvider) {
        super(sessionProvider);
    }

    @Override
    public void run() {
        context.getAttributes().replace("com.steambeat.ready", true);
        Thread.currentThread().interrupt();
        return;
    }
}
