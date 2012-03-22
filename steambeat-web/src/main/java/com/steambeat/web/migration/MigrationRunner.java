package com.steambeat.web.migration;

import com.google.common.collect.Lists;
import com.google.inject.Inject;
import com.steambeat.repositories.SessionProvider;
import org.restlet.Context;

import java.util.List;

public class MigrationRunner implements Runnable {

    @Inject
    public MigrationRunner(final SessionProvider sessionProvider) {
        this.sessionProvider = sessionProvider;
    }

    public void setContext(final Context context) {
        this.context = context;
    }

    @Override
    public void run() {
        List<Migration> migrations = Lists.newArrayList();
        migrations.add(new Migration00001(sessionProvider));
        migrations.add(new Migration00002(sessionProvider));
        for (Migration migration : migrations) {
            sessionProvider.start();
            migration.run();
            sessionProvider.stop();
        }
        context.getAttributes().replace("com.steambeat.ready", true);
        Thread.currentThread().interrupt();
        return;
    }

    protected Context context;
    private SessionProvider sessionProvider;
}
