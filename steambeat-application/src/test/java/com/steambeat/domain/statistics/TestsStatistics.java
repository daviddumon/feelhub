package com.steambeat.domain.statistics;

import com.steambeat.domain.subject.webpage.WebPage;
import com.steambeat.repositories.Repositories;
import com.steambeat.test.WithDomainEvent;
import com.steambeat.test.fakeRepositories.WithFakeRepositories;
import com.steambeat.test.testFactories.TestFactories;
import org.joda.time.DateTime;
import org.junit.*;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.*;

public class TestsStatistics {

    @Rule
    public WithFakeRepositories repositories = new WithFakeRepositories();

    @Rule
    public WithDomainEvent withDomainEvent = new WithDomainEvent();

    @Test
    public void canCreateFromSubject() {
        final WebPage webPage = TestFactories.webPages().newWebPage();
        Repositories.webPages().add(webPage);

        final Statistics statistics = new Statistics(webPage, Granularity.hour, new DateTime());

        assertThat(statistics.getSubjectId(), is(webPage.getId()));
    }
}
