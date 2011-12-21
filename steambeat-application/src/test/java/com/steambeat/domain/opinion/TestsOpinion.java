package com.steambeat.domain.opinion;

import com.steambeat.domain.subject.webpage.WebPage;
import com.steambeat.test.SystemTime;
import com.steambeat.test.fakeRepositories.WithFakeRepositories;
import com.steambeat.test.testFactories.TestFactories;
import org.junit.Rule;
import org.junit.Test;

import static org.hamcrest.MatcherAssert.*;
import static org.hamcrest.Matchers.*;

public class TestsOpinion {

    @Rule
    public SystemTime time = SystemTime.fixed();

    @Rule
    public WithFakeRepositories fakeRepositories = new WithFakeRepositories();

    @Test
    public void canCreateWithText(){
        final String text = "my opinion";
        
        final Opinion opinion = new Opinion(text);
        
        assertThat(opinion.getText(), is(text));
    }
    
    @Test
    public void canAddJudgementsToOpinion() {
        final Opinion opinion = new Opinion("my opinion");
        final WebPage subject = TestFactories.webPages().newWebPage();

        opinion.addJudgment(subject, Feeling.good);

        assertThat(opinion.getCreationDate(), notNullValue());
        assertThat(opinion.getCreationDate(), is(time.getNow()));
        assertThat(opinion.getJudgments().size(), is(1));
        assertThat(opinion.getJudgments().get(0).getSubject(), is(subject));
        assertThat(opinion.getJudgments().get(0).getFeeling(), is(Feeling.good));
    }

}
