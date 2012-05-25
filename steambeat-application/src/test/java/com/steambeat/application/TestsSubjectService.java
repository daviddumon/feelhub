package com.steambeat.application;

import com.steambeat.domain.association.Association;
import com.steambeat.domain.association.uri.Uri;
import com.steambeat.domain.subject.*;
import com.steambeat.domain.subject.webpage.WebPage;
import com.steambeat.repositories.Repositories;
import com.steambeat.test.SystemTime;
import com.steambeat.test.fakeFactories.*;
import com.steambeat.test.fakeRepositories.WithFakeRepositories;
import com.steambeat.test.testFactories.TestFactories;
import org.joda.time.DateTime;
import org.junit.*;
import org.junit.rules.ExpectedException;

import java.util.UUID;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.*;
import static org.junit.matchers.JUnitMatchers.*;

public class TestsSubjectService {

    @Rule
    public WithFakeRepositories repositories = new WithFakeRepositories();

    @Rule
    public ExpectedException exception = ExpectedException.none();

    @Rule
    public SystemTime time = SystemTime.fixed();

    @Before
    public void before() {
        subjectService = new SubjectService(new SubjectFactory(new FakeWebPageFactory(), new FakeConceptFactory()));
    }

    @Test
    public void canGetWebPageFromRepository() {
        final Subject subject = TestFactories.subjects().newWebPage();

        final WebPage webPageFound = subjectService.lookUpWebPage(subject.getId());

        assertThat(webPageFound, is(subject));
    }

    @Test
    public void canAddWebPageToRepository() {
        final Association association = TestFactories.associations().newAssociation(new Uri("uri"));
        final WebPage webPageFound = subjectService.addWebPage(association);

        assertThat(Repositories.subjects().getAll().size(), is(1));
        assertThat(Repositories.subjects().getAll(), hasItem((Subject) webPageFound));
    }

    @Test
    public void returnExistingWebPageOnNewAdd() {
        final Association association = TestFactories.associations().newAssociation(new Uri("uri"));
        final WebPage webPage1 = subjectService.addWebPage(association);
        final WebPage webPage2 = subjectService.addWebPage(association);

        assertThat(Repositories.subjects().getAll().size(), is(1));
        assertThat(webPage1, is(webPage2));
    }

    @Test
    public void throwsExceptionOnFailLookup() {
        exception.expect(WebPageNotYetCreatedException.class);
        subjectService.lookUpWebPage(UUID.randomUUID());
    }

    @Test
    public void updateWebPageIfExpired() {
        final WebPage webPage = TestFactories.subjects().newWebPage();
        final DateTime firstDate = webPage.getScrapedDataExpirationDate();
        time.waitDays(2);

        final WebPage webPageFound = subjectService.lookUpWebPage(webPage.getId());

        assertThat(firstDate, not(webPageFound.getScrapedDataExpirationDate()));
    }

    private SubjectService subjectService;
}
