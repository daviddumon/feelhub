package com.steambeat.application;

import com.steambeat.domain.subject.steam.Steam;
import com.steambeat.repositories.Repositories;
import com.steambeat.test.fakeRepositories.WithFakeRepositories;
import org.junit.*;

import static org.hamcrest.MatcherAssert.*;
import static org.hamcrest.Matchers.*;

public class TestsSubjectService {

    @Rule
    public WithFakeRepositories repositories = new WithFakeRepositories();

    @Test
    public void buildSteamIfNoSteam() {
        final SubjectService subjectService = new SubjectService();

        final Steam steam = subjectService.steam();

        assertThat(steam, notNullValue());
    }

    @Test
    public void getSteamIfSteamExists() {
        final Steam firstSteam = new Steam();
        Repositories.steam().add(firstSteam);
        final SubjectService subjectService = new SubjectService();

        final Steam secondSteam = subjectService.steam();

        assertThat(secondSteam, is(firstSteam));
    }
}
