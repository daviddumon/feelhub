package com.steambeat.application;

import com.steambeat.domain.subject.webpage.*;
import com.steambeat.repositories.Repositories;
import com.steambeat.test.fakeFactories.FakeWebPageFactory;
import com.steambeat.test.fakeRepositories.WithFakeRepositories;
import com.steambeat.test.fakeServices.FakeAssociationService;
import com.steambeat.test.testFactories.TestFactories;
import org.junit.*;
import org.junit.rules.ExpectedException;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.*;
import static org.junit.matchers.JUnitMatchers.*;

public class TestsWebPageService {

    @Rule
    public WithFakeRepositories repositories = new WithFakeRepositories();

    @Rule
    public ExpectedException exception = ExpectedException.none();

    @Before
    public void before() {
        webPageService = new WebPageService(new FakeAssociationService(), new FakeWebPageFactory());
    }

    @Test
    public void canGetWebPageFromRepository() {
        final WebPage webPage = TestFactories.webPages().newWebPage("uri");

        final WebPage webPageFound = webPageService.lookUpWebPage(new Uri("uri"));

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
        webPageService.lookUpWebPage(badUri());
    }

    private Uri badUri() {
        return new Uri("a fail uri");
    }

    private WebPageService webPageService;
}
