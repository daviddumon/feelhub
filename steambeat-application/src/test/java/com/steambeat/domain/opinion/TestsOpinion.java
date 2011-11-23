package com.steambeat.domain.opinion;

import com.steambeat.domain.subject.Subject;
import com.steambeat.test.SystemTime;
import com.steambeat.test.fakeRepositories.WithFakeRepositories;
import com.steambeat.test.testFactories.TestFactories;
import org.junit.*;

import static org.hamcrest.MatcherAssert.*;
import static org.hamcrest.Matchers.*;

public class TestsOpinion {

    @Rule
    public SystemTime time = SystemTime.fixed();

    @Rule
    public WithFakeRepositories fakeRepositories = new WithFakeRepositories();

    @Test
    public void canCreateAnOpinion() {
        final String text = "my opinion";
        final Feeling feeling = Feeling.good;
        final Subject subject = TestFactories.webPages().newWebPage();

        final Opinion opinion = new Opinion(text, feeling, subject);

        assertThat(opinion, notNullValue());
        assertThat(opinion.getText(), is(text));
        assertThat(opinion.getFeeling(), is(feeling));
        assertThat(opinion.getCreationDate(), notNullValue());
        assertThat(opinion.getCreationDate(), is(time.getNow()));
        assertThat(opinion.getSubject(), is(subject));
    }

    //
    //@Test
    //public void canParseAndReplaceLinkToOpinion() {
    //    final WebPage webPage = TestFactories.webPages().newWebPageWithLotOfOpinions("http://www.fake.com", 30);
    //    final String value = "I really like what @10 said !";
    //    final Context context = new Context();
    //    context.getAttributes().put("org.restlet.ext.servlet.ServletContext", restlet.mockServletContext());
    //
    //    final String result = Page.parse(webPage, value, context);
    //
    //    assertThat(result, is("I really like what <a href=\"http://thedomain//webpages/http://www.fake.com/opinions/10\">@10</a> said !"));
    //}
    //
    //@Test
    //public void parseOnlyNumbers() {
    //    final WebPage webPage = TestFactories.webPages().newWebPageWithLotOfOpinions("http://www.fake.com", 30);
    //    final String value = "I really like what @10mistake said !";
    //    final Context context = new Context();
    //    context.getAttributes().put("org.restlet.ext.servlet.ServletContext", restlet.mockServletContext());
    //
    //    final String result = Page.parse(webPage, value, context);
    //
    //    assertThat(result, is(value));
    //}
}
