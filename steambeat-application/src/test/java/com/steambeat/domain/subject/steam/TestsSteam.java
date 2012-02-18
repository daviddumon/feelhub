package com.steambeat.domain.subject.steam;

import org.junit.Test;

import static org.hamcrest.MatcherAssert.*;
import static org.hamcrest.Matchers.*;

public class TestsSteam {

    @Test
    public void canCreateSteam() {
        final Steam steam = new Steam();

        assertThat(steam.getId(), is("steam"));
    }
}
