package com.steambeat.application;

import com.steambeat.domain.analytics.identifiers.uri.Uri;
import com.steambeat.domain.subject.webpage.WebPage;
import com.steambeat.repositories.Repositories;
import com.steambeat.test.*;
import com.steambeat.test.fakeFactories.FakeWebPageFactory;
import com.steambeat.test.fakeRepositories.WithFakeRepositories;
import com.steambeat.test.testFactories.TestFactories;
import org.joda.time.DateTime;
import org.junit.*;
import org.junit.rules.ExpectedException;

import java.util.UUID;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.*;
import static org.junit.matchers.JUnitMatchers.*;

public class TestsWebPageService {

    @Rule
    public WithFakeRepositories repositories = new WithFakeRepositories();

    @Rule
    public ExpectedException exception = ExpectedException.none();

    @Rule
    public SystemTime time = SystemTime.fixed();

    @Before
    public void before() {
        webPageService = new WebPageService(new AssociationService(new FakeUriPathResolver()), new FakeWebPageFactory());
    }

    @Test
    public void canGetWebPageFromRepository() {
        final WebPage webPage = TestFactories.webPages().newWebPage();
        Repositories.webPages().add(webPage);

        final WebPage webPageFound = webPageService.lookUpWebPage(UUID.fromString(webPage.getId()));

        assertThat(webPageFound, is(webPage));
    }

    @Test
    public void canAddWebPageToRepository() {
        final WebPage webPageFound = webPageService.addWebPage(new Uri("uri"));

        assertThat(Repositories.webPages().getAll().size(), is(1));
        assertThat(Repositories.webPages().getAll(), hasItem(webPageFound));
    }

    @Test
    public void throwsExceptionOnFailLookup() {
        exception.expect(WebPageNotYetCreatedException.class);
        webPageService.lookUpWebPage(UUID.randomUUID());
    }

    @Test
    public void updateWebPageIfExpired() {
        final WebPage webPage = TestFactories.webPages().newWebPage();
        Repositories.webPages().add(webPage);
        final DateTime firstDate = webPage.getScrapedDataExpirationDate();
        time.waitDays(2);

        final WebPage webPageFound = webPageService.lookUpWebPage(UUID.fromString(webPage.getId()));

        assertThat(firstDate, not(webPageFound.getScrapedDataExpirationDate()));
    }

    private WebPageService webPageService;
}
