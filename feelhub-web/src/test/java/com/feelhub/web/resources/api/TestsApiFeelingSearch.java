package com.feelhub.web.resources.api;

import com.feelhub.domain.feeling.*;
import com.feelhub.domain.user.User;
import com.feelhub.repositories.fakeRepositories.WithFakeRepositories;
import com.feelhub.test.TestFactories;
import com.feelhub.web.authentification.*;
import com.feelhub.web.dto.FeelingData;
import com.feelhub.web.guice.GuiceTestModule;
import com.google.inject.*;
import org.junit.*;
import org.restlet.data.Form;

import java.util.List;

import static org.fest.assertions.Assertions.*;

public class TestsApiFeelingSearch {

    @Rule
    public WithFakeRepositories repositories = new WithFakeRepositories();

    @Before
    public void before() {
        injector = Guice.createInjector(new GuiceTestModule());
        apiFeelingSearch = injector.getInstance(ApiFeelingSearch.class);
    }

    @Test
    public void canSearchAllFeelingsForAUser() {
        final User user = TestFactories.users().createFakeActiveUser("mail@mail.com");
        CurrentUser.set(new WebUser(user, true));
        TestFactories.feelings().newFeeling(user.getId());
        TestFactories.feelings().newFeeling(user.getId());
        TestFactories.feelings().newFeeling();

        final List<FeelingData> feelings = apiFeelingSearch.doSearch(new Form(), user);

        assertThat(feelings.size()).isEqualTo(2);
    }

    @Test
    public void canSearchFeelingsWithSentimentsWithoutTopics() {
        final User user = TestFactories.users().createFakeActiveUser("mail@mail.com");
        CurrentUser.set(new WebUser(user, true));
        final Feeling feeling1 = TestFactories.feelings().newFeelingWithoutSentiments();
        final Feeling feeling2 = TestFactories.feelings().newFeelingWithoutSentiments();
        feeling1.addSentiment(new Sentiment(SentimentValue.good, ""));
        feeling2.addSentiment(new Sentiment(SentimentValue.bad, ""));

        final List<FeelingData> feelings = apiFeelingSearch.doSearch(new Form());

        assertThat(feelings.size()).isEqualTo(2);
    }

    private Injector injector;
    private ApiFeelingSearch apiFeelingSearch;
}
