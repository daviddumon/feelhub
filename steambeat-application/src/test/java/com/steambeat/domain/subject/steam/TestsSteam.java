package com.steambeat.domain.subject.steam;

import org.junit.Test;

import java.util.UUID;

import static org.hamcrest.MatcherAssert.*;
import static org.hamcrest.Matchers.*;

public class TestsSteam {

    @Test
    public void canCreateSteam() {
        final UUID uuid = UUID.randomUUID();
        final Steam steam = new Steam(uuid);

        assertThat(steam.getId(), notNullValue());
        assertThat(steam.getId(), is(uuid));
    }
}
