package com.steambeat.domain.opinion;

import com.google.common.collect.Lists;
import com.steambeat.domain.subject.webpage.WebPage;
import com.steambeat.test.SystemTime;
import com.steambeat.test.fakeRepositories.WithFakeRepositories;
import com.steambeat.test.testFactories.TestFactories;
import org.junit.*;

import java.util.List;

import static org.hamcrest.MatcherAssert.*;
import static org.hamcrest.Matchers.*;

public class TestsOpinion {

    @Rule
    public SystemTime time = SystemTime.fixed();

    @Rule
    public WithFakeRepositories fakeRepositories = new WithFakeRepositories();

    @Test
    public void canCreateAnOpinionWithJudgments() {
        final String text = "my opinion";
        final List<Judgment> judgments = createSimpleListOfJudgments();

        final Opinion opinion = new Opinion(text, judgments);

        assertThat(opinion, notNullValue());
        assertThat(opinion.getText(), is(text));
        assertThat(opinion.getCreationDate(), notNullValue());
        assertThat(opinion.getCreationDate(), is(time.getNow()));
        assertThat(opinion.getJudgments().size(), is(1));
        assertThat(opinion.getJudgments().get(0).getSubjectId(), is(judgments.get(0).getSubjectId()));
        assertThat(opinion.getJudgments().get(0).getFeeling(), is(judgments.get(0).getFeeling()));
    }

    private List<Judgment> createSimpleListOfJudgments() {
        final WebPage webPage = TestFactories.webPages().newWebPage();
        final Feeling feeling = Feeling.good;
        final List<Judgment> judgments = Lists.newArrayList();
        judgments.add(new Judgment(webPage.getId(), feeling));
        return judgments;
    }
}
