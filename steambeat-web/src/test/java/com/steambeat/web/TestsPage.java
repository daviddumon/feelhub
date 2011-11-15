package com.steambeat.web;

import com.steambeat.domain.subject.webpage.WebPage;
import com.steambeat.test.fakeRepositories.WithFakeRepositories;
import com.steambeat.test.fakeSearches.FakeOpinionSearch;
import com.steambeat.test.testFactories.TestFactories;
import org.junit.*;
import org.restlet.Context;

import java.util.List;

import static org.hamcrest.MatcherAssert.*;
import static org.hamcrest.Matchers.*;

public class TestsPage {

    @Rule
    public WebApplicationTester restlet = new WebApplicationTester();

    @Rule
    public WithFakeRepositories repositories = new WithFakeRepositories();

    @Test
    public void testPageHasTotalOpinionsNumber() {
        final WebPage webPage = TestFactories.webPages().newWebPageWithLotOfOpinions("http://www.fake.com", 30);
        final List<Page> pages = Page.forContextAndSubject(Context.getCurrent(), webPage, new FakeOpinionSearch());

        assertThat(pages.size(), is(2));
        assertThat(pages.get(0).getMax(), is(30));
        assertThat(pages.get(1).getMax(), is(30));
    }

    @Test
    public void canParseAndReplaceLinkToOpinion() {
        final WebPage webPage = TestFactories.webPages().newWebPageWithLotOfOpinions("http://www.fake.com", 30);
        final String value = "I really like what @10 said !";
        final Context context = new Context();
        context.getAttributes().put("org.restlet.ext.servlet.ServletContext", restlet.mockServletContext());

        final String result = Page.parse(webPage, value, context);

        assertThat(result, is("I really like what <a href=\"http://thedomain//webpages/http://www.fake.com/opinions/10\">@10</a> said !"));
    }

    @Test
    public void parseOnlyNumbers() {
        final WebPage webPage = TestFactories.webPages().newWebPageWithLotOfOpinions("http://www.fake.com", 30);
        final String value = "I really like what @10mistake said !";
        final Context context = new Context();
        context.getAttributes().put("org.restlet.ext.servlet.ServletContext", restlet.mockServletContext());

        final String result = Page.parse(webPage, value, context);

        assertThat(result, is(value));
    }
}
