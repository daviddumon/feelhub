package com.steambeat.web;

import com.google.inject.Inject;
import com.steambeat.domain.subject.Subject;
import com.steambeat.domain.subject.steam.Steam;
import com.steambeat.repositories.*;

public class SteambeatBoot {

    @Inject
    public SteambeatBoot(final SessionProvider provider) {
        Repositories.initialize(new MongoRepositories(provider));
    }

    public void checkForSteam() {
        final Subject steam = Repositories.subjects().get("steam");
        if (steam == null) {
            Repositories.subjects().add(new Steam());
        }
    }
}
