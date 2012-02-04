package com.steambeat.domain.subject.webpage;

import com.steambeat.test.FakeWebPageScraper;
import org.junit.*;

import java.util.HashMap;

import static org.hamcrest.MatcherAssert.*;
import static org.hamcrest.core.Is.*;

public class TestsWebPage {

    @Before
    public void setUp() throws Exception {
        tags = new HashMap<String, String>();
        fakeWebPageScraper = new FakeWebPageScraper();
        association = new Association(new Uri("http://www.lemonde.fr/lol/yeah/article.html"), null);
    }

    @Test
    public void canGetSimpleTitle() {
        tags.put("title", "titletag");
        fakeWebPageScraper.setScrappedTags(tags);

        final WebPage webPage = new WebPage(association, fakeWebPageScraper);

        assertThat(webPage.getTitle(), is("titletag"));
    }

    @Test
    public void titleIsMoreRelevantThantH1H2H3() {
        tags.put("title", "titletag");
        tags.put("h1", "h1tag");
        tags.put("h2", "h2tag");
        fakeWebPageScraper.setScrappedTags(tags);

        final WebPage webPage = new WebPage(association, fakeWebPageScraper);

        assertThat(webPage.getTitle(), is("titletag"));
    }

    @Test
    public void canUseH1ForTitle() {
        tags.put("title", "");
        tags.put("h1", "h1tag");
        tags.put("h2", "h2tag");
        fakeWebPageScraper.setScrappedTags(tags);

        final WebPage webPage = new WebPage(association, fakeWebPageScraper);

        assertThat(webPage.getTitle(), is("h1tag"));
    }

    @Test
    public void canUseH2ForTitle() {
        tags.put("title", "");
        tags.put("h1", "");
        tags.put("h2", "h2tag");
        fakeWebPageScraper.setScrappedTags(tags);

        final WebPage webPage = new WebPage(association, fakeWebPageScraper);

        assertThat(webPage.getTitle(), is("h2tag"));
    }

    @Test
    public void skipTitlesTooLong() {
        tags.put("title", "abcdefghijabcdefghijabcdefghijabcdefghijabcdefghijabcdefghijabcdefghijabcdefghijabcdefghijabcdefghijK");
        tags.put("h1", "");
        tags.put("h2", "h2tag");
        fakeWebPageScraper.setScrappedTags(tags);

        final WebPage webPage = new WebPage(association, fakeWebPageScraper);

        assertThat(webPage.getTitle(), is("h2tag"));
    }

    @Test
    public void canGetThumbnailUrl() {
        tags.put("logoUrl", "logoUrl");
        tags.put("firstImageUrl", "firstImageUrl");
        fakeWebPageScraper.setScrappedTags(tags);

        final WebPage webPage = new WebPage(association, fakeWebPageScraper);

        assertThat(webPage.getThumbnailUrl(), is("logoUrl"));
    }

    @Test
    public void useFirstImageIfNoLogo() {
        tags.put("logoUrl", "");
        tags.put("firstImageUrl", "firstImageUrl");
        fakeWebPageScraper.setScrappedTags(tags);

        final WebPage webPage = new WebPage(association, fakeWebPageScraper);

        assertThat(webPage.getThumbnailUrl(), is("firstImageUrl"));
    }

    @Test
    public void useOnlySmallTagsForShortTitle() {
        tags.put("title", "abcdefghijabcdefghijabcdefghijK");
        tags.put("h1", "");
        tags.put("h2", "h2tag");
        fakeWebPageScraper.setScrappedTags(tags);

        final WebPage webPage = new WebPage(association, fakeWebPageScraper);

        assertThat(webPage.getShortTitle(), is("h2tag"));
    }

    @Test
    public void useUriForShortTitleInLastResort() {
        tags.put("title", "");
        tags.put("h1", "");
        tags.put("h2", "");
        fakeWebPageScraper.setScrappedTags(tags);

        final WebPage webPage = new WebPage(association, fakeWebPageScraper);

        assertThat(webPage.getShortTitle(), is("www.lemonde.fr ... ah/article.html"));
    }

    private HashMap<String, String> tags;
    private FakeWebPageScraper fakeWebPageScraper;
    private Association association;
}
