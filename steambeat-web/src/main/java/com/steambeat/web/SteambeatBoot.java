package com.steambeat.web;

import com.google.inject.Inject;
import com.steambeat.domain.subject.steam.Steam;
import com.steambeat.repositories.*;

import java.util.UUID;

public class SteambeatBoot {

    @Inject
    public SteambeatBoot(final SessionProvider provider) {
        Repositories.initialize(new MongoRepositories(provider));
    }

    public void checkForSteam() {
        final Steam steam = Repositories.subjects().getSteam();
        if (steam == null) {
            Repositories.subjects().add(new Steam(UUID.randomUUID()));
        }
    }
}
