package com.steambeat.web.search;

import com.steambeat.domain.eventbus.WithDomainEvent;
import com.steambeat.domain.opinion.Opinion;
import com.steambeat.domain.reference.Reference;
import com.steambeat.repositories.TestWithMongoRepository;
import com.steambeat.test.*;
import org.junit.*;

import java.util.List;

import static org.hamcrest.MatcherAssert.*;
import static org.hamcrest.Matchers.*;

public class TestsOpinionSearch extends TestWithMongoRepository {

    @Rule
    public WithDomainEvent event = new WithDomainEvent();

    @Rule
    public SystemTime time = SystemTime.fixed();

    @Before
    public void before() {
        opinionSearch = new OpinionSearch(getProvider());
    }

    @Test
    public void canGetOneOpinion() {
        TestFactories.opinions().newOpinion();

        final List<Opinion> opinions = opinionSearch.execute();

        assertThat(opinions.size(), is(1));
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

    @Ignore
    @Test
    public void canGetOpinionsForSubject() {
        final Reference reference = TestFactories.references().newReference();
        TestFactories.opinions().newOpinions(reference, 10);
        TestFactories.opinions().newOpinions(20);

        opinionSearch.withReference(reference);

        final List<Opinion> opinions = opinionSearch.execute();
        assertThat(opinions.size(), is(10));
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
