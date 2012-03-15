package com.steambeat.web;

import com.google.inject.Inject;
import com.steambeat.domain.subject.steam.Steam;
import com.steambeat.repositories.*;

public class SteambeatBoot {

    @Inject
    public SteambeatBoot(final SessionProvider provider) {
        Repositories.initialize(new MongoRepositories(provider));
    }

    public void boot() {
        checkForSteam();
    }

    private void checkForSteam() {
        final Steam steam = Repositories.subjects().getSteam();
        if (steam == null) {
            Repositories.subjects().add(new Steam());
        }
    }
}
