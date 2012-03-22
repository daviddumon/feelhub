package com.steambeat.web;

import com.google.inject.Inject;
import com.steambeat.repositories.SessionProvider;

public class FakeSteambeatBoot extends SteambeatBoot {

    @Inject
    public FakeSteambeatBoot(final SessionProvider provider) {
        super(provider);
    }
}
