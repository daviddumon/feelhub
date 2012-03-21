package com.steambeat.web.migration;

import com.google.common.collect.Lists;
import com.google.inject.Inject;
import com.steambeat.repositories.SessionProvider;
import org.restlet.Context;

import java.util.List;

public class MigrationRunner {

    @Inject
    public MigrationRunner(final SessionProvider provider) {
        this.provider = provider;
    }

    public void setContext(final Context context) {
        this.context = context;
    }

    public void run() {
        List<Migration> migrations = Lists.newArrayList();
        //migrations.add(new Migration00001(provider));
        context.getAttributes().replace("com.steambeat.ready", true);
    }

    private Context context;
    private SessionProvider provider;
}
