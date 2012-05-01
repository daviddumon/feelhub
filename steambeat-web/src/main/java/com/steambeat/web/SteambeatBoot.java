package com.steambeat.web;

import com.google.inject.Inject;
import com.steambeat.domain.subject.steam.Steam;
import com.steambeat.repositories.*;

import java.util.UUID;

public class SteambeatBoot {

    @Inject
    public SteambeatBoot(final SessionProvider provider) {
        this.provider = provider;
    }

    public void checkForSteam() {
        Repositories.initialize(new MongoRepositories(provider));
        final Steam steam = Repositories.subjects().getSteam();
        if (steam == null) {
            Repositories.subjects().add(new Steam(UUID.randomUUID()));
        }
    }

    private SessionProvider provider;
}
