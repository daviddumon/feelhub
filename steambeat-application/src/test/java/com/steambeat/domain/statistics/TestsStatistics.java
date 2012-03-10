package com.steambeat.domain.statistics;

import com.steambeat.domain.subject.Subject;
import com.steambeat.repositories.*;
import com.steambeat.test.WithDomainEvent;
import com.steambeat.test.testFactories.TestFactories;
import org.joda.time.DateTime;
import org.junit.*;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.*;

public class TestsStatistics extends TestWithMongoRepository {

    @Rule
    public WithDomainEvent withDomainEvent = new WithDomainEvent();

    @Test
    public void canCreateFromSubject() {
        final Subject subject = TestFactories.subjects().newWebPage();
        Repositories.subjects().add(subject);

        final Statistics statistics = new Statistics(subject, Granularity.hour, new DateTime());

        assertThat(statistics.getSubjectId(), is(subject.getId()));
    }
}
