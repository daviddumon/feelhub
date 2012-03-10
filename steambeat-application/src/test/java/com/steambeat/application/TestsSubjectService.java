package com.steambeat.application;

import com.steambeat.domain.subject.steam.Steam;
import com.steambeat.repositories.*;
import org.junit.Test;

import static org.hamcrest.MatcherAssert.*;
import static org.hamcrest.Matchers.*;

public class TestsSubjectService extends TestWithMongoRepository {

    @Test
    public void buildSteamIfNoSteam() {
        final SubjectService subjectService = new SubjectService();

        final Steam steam = subjectService.steam();

        assertThat(steam, notNullValue());
    }

    @Test
    public void getSteamIfSteamExists() {
        final Steam firstSteam = new Steam();
        Repositories.subjects().add(firstSteam);
        final SubjectService subjectService = new SubjectService();

        final Steam secondSteam = subjectService.steam();

        assertThat(secondSteam, is(firstSteam));
    }
}
