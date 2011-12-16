package com.steambeat.web.search;

import com.steambeat.domain.opinion.Opinion;
import com.steambeat.domain.subject.webpage.WebPage;
import com.steambeat.repositories.TestWithMongoRepository;
import com.steambeat.test.SystemTime;
import com.steambeat.test.testFactories.TestFactories;
import org.junit.*;

import java.util.List;

import static org.hamcrest.MatcherAssert.*;
import static org.hamcrest.Matchers.*;

@Ignore
public class TestsOpinionSearch extends TestWithMongoRepository {

    @Rule
    public SystemTime time = SystemTime.fixed();

    @Before
    public void before() {
        opinionSearch = new OpinionSearch(getProvider());
    }

    @Test
    public void canGetAllOpinions() {
        TestFactories.opinions().newOpinions(20);

        final List<Opinion> opinions = opinionSearch.execute();

        assertThat(opinions.size(), is(20));
    }

    @Test
    public void canSkip() {
        TestFactories.opinions().newOpinions(20);

        opinionSearch.withSkip(10);

        final List<Opinion> opinions = opinionSearch.execute();
        assertThat(opinions.size(), is(10));
    }

    @Test
    public void canLimit() {
        TestFactories.opinions().newOpinions(20);

        opinionSearch.withLimit(5);

        final List<Opinion> opinions = opinionSearch.execute();
        assertThat(opinions.size(), is(5));
    }

    @Test
    public void canLimitAndSkip() {
        TestFactories.opinions().newOpinions(30);

        opinionSearch.withLimit(5).withSkip(10);

        final List<Opinion> opinions = opinionSearch.execute();
        assertThat(opinions.size(), is(5));
        assertThat(opinions.get(0).getText(), is("i10"));
        assertThat(opinions.get(1).getText(), is("i11"));
        assertThat(opinions.get(2).getText(), is("i12"));
        assertThat(opinions.get(3).getText(), is("i13"));
        assertThat(opinions.get(4).getText(), is("i14"));
    }

    @Test
    public void canGetOpinionsForSubject() {
        final WebPage webPage = TestFactories.webPages().newWebPage();
        TestFactories.opinions().newOpinions(webPage, 10);
        TestFactories.opinions().newOpinions(20);

        opinionSearch.withSubject(webPage);

        final List<Opinion> opinions = opinionSearch.execute();
        assertThat(opinions.size(), is(10));
    }

    @Test
    public void canLimitAndSkipForSubject() {
        final WebPage webPage = TestFactories.webPages().newWebPage();
        TestFactories.opinions().newOpinions(webPage, 10);
        TestFactories.opinions().newOpinions(20);

        opinionSearch.withSubject(webPage).withSkip(3).withLimit(4);

        final List<Opinion> opinions = opinionSearch.execute();
        assertThat(opinions.size(), is(4));
        assertThat(opinions.get(0).getText(), is("i3"));
        assertThat(opinions.get(1).getText(), is("i4"));
        assertThat(opinions.get(2).getText(), is("i5"));
        assertThat(opinions.get(3).getText(), is("i6"));
    }

    @Test
    public void alwaysIgnoreEmptyOpinions() {
        TestFactories.opinions().newOpinions(10);
        TestFactories.opinions().newOpinionWithText("");

        final List<Opinion> opinions = opinionSearch.execute();
        assertThat(opinions.size(), is(10));
    }

    private OpinionSearch opinionSearch;
}
