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

        steambeatBoot.checkForSteam();

        assertThat(Repositories.subjects().get("steam"), notNullValue());
    }

    @Test
    public void doNotCreateNewSteamIfPresent() {
        final Subject steam = new Steam();
        Repositories.subjects().add(steam);
        final SteambeatBoot steambeatBoot = new SteambeatBoot(getProvider());

        steambeatBoot.checkForSteam();

        assertThat(Repositories.subjects().getAll().size(), is(1));
        final Subject steamFound = Repositories.subjects().get("steam");
        assertThat(steamFound, notNullValue());
        assertThat(steamFound, is(steam));
    }
}
