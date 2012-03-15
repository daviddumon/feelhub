package com.steambeat.web;

import com.steambeat.domain.subject.Subject;
import com.steambeat.domain.subject.steam.Steam;
import com.steambeat.repositories.*;
import org.junit.Test;

import static org.hamcrest.MatcherAssert.*;
import static org.hamcrest.Matchers.*;

public class TestsSteambeatBoot extends TestWithMongoRepository {

    @Test
    public void canCreateSteamIfNotPresent() {
        final SteambeatBoot steambeatBoot = new SteambeatBoot(getProvider());

        steambeatBoot.boot();

        assertThat(Repositories.subjects().getSteam(), notNullValue());
    }

    @Test
    public void doNotCreateNewSteamIfPresent() {
        final Subject steam = new Steam();
        Repositories.subjects().add(steam);
        final SteambeatBoot steambeatBoot = new SteambeatBoot(getProvider());

        steambeatBoot.boot();

        assertThat(Repositories.subjects().getAll().size(), is(1));
        final Steam steamFound = Repositories.subjects().getSteam();
        assertThat(steamFound, notNullValue());
        assertThat(steamFound, is(steam));
    }
}
