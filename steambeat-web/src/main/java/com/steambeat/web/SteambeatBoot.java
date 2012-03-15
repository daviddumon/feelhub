package com.steambeat.web;

import com.google.common.collect.Lists;
import com.google.inject.Inject;
import com.steambeat.domain.subject.steam.Steam;
import com.steambeat.repositories.*;
import com.steambeat.web.migration.*;

import java.util.List;

public class SteambeatBoot {

    @Inject
    public SteambeatBoot(final SessionProvider provider) {
        this.provider = provider;
        Repositories.initialize(new MongoRepositories(provider));
    }

    public void boot() {
        checkForSteam();
        doMigrations();
    }

    private void doMigrations() {
        migrations.add(new Migration00001(provider));
        for (Migration migration : migrations) {
            migration.run();
        }
    }

    private void checkForSteam() {
        final Steam steam = Repositories.subjects().getSteam();
        if (steam == null) {
            Repositories.subjects().add(new Steam());
        }
    }

    List<Migration> migrations = Lists.newArrayList();
    private SessionProvider provider;
}
