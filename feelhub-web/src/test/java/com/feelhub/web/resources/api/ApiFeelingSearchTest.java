package com.feelhub.web.resources.api;

import com.feelhub.domain.topic.real.RealTopic;
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

public class ApiFeelingSearchTest {

    @Rule
    public WithFakeRepositories repositories = new WithFakeRepositories();

    @Before
    public void before() {
        user = TestFactories.users().createFakeActiveUser("mail@mail.com");
        CurrentUser.set(new WebUser(user, true));
        final Injector injector = Guice.createInjector(new GuiceTestModule());
        apiFeelingSearch = injector.getInstance(ApiFeelingSearch.class);
    }

    @Test
    public void canSearchAllFeelingsForAUser() {
        TestFactories.feelings().newFeeling(user.getId());
        TestFactories.feelings().newFeeling(user.getId());
        TestFactories.feelings().newFeeling();

        final List<FeelingData> feelingDatas = apiFeelingSearch.doSearch(new Form(), user);

        assertThat(feelingDatas.size()).isEqualTo(2);
    }

    @Test
    public void canGetAFeelingForATopic() {
        final Form parameters = new Form();
        parameters.add("skip", "0");
        parameters.add("limit", "1");
        final RealTopic realTopic = TestFactories.topics().newCompleteRealTopic();
        TestFactories.feelings().newFeeling(realTopic.getId(), "text");

        final List<FeelingData> feelingDatas = apiFeelingSearch.doSearch(realTopic, parameters);

        assertThat(feelingDatas.size()).isEqualTo(1);
    }

    @Test
    public void defaultLimitIs100() {
        final Form parameters = new Form();
        parameters.add("skip", "0");
        final RealTopic realTopic = TestFactories.topics().newCompleteRealTopic();
        TestFactories.feelings().newFeelings(realTopic.getId(), 150);

        final List<FeelingData> feelingDatas = apiFeelingSearch.doSearch(realTopic, parameters);

        assertThat(feelingDatas.size()).isEqualTo(100);
    }

    @Test
    public void defaultSkipIs0() {
        final Form parameters = new Form();
        parameters.add("limit", "10");
        final RealTopic realTopic = TestFactories.topics().newCompleteRealTopic();
        TestFactories.feelings().newFeelings(realTopic.getId(), 100);

        final List<FeelingData> feelingDatas = apiFeelingSearch.doSearch(realTopic, parameters);

        assertThat(feelingDatas.get(0).getText().toString()).isEqualTo("[i0]");
    }

    @Test
    public void canGetMultipleFeelings() {
        final Form parameters = new Form();
        parameters.add("skip", "0");
        parameters.add("limit", "10");
        final RealTopic realTopic = TestFactories.topics().newCompleteRealTopic();
        TestFactories.feelings().newFeeling(realTopic.getId(), "text");
        TestFactories.feelings().newFeeling(realTopic.getId(), "text");
        TestFactories.feelings().newFeeling(realTopic.getId(), "text");

        final List<FeelingData> feelingDatas = apiFeelingSearch.doSearch(realTopic, parameters);

        assertThat(feelingDatas.size()).isEqualTo(3);
    }

    @Test
    public void canGetMultipleFeelingsWithSkip() {
        final Form parameters = new Form();
        parameters.add("skip", "1");
        final RealTopic realTopic = TestFactories.topics().newCompleteRealTopic();
        TestFactories.feelings().newFeeling(realTopic.getId(), "text");
        TestFactories.feelings().newFeeling(realTopic.getId(), "text");
        TestFactories.feelings().newFeeling(realTopic.getId(), "text");

        final List<FeelingData> feelingDatas = apiFeelingSearch.doSearch(realTopic, parameters);

        assertThat(feelingDatas.size()).isEqualTo(2);
    }

    @Test
    public void canGetFeelingForTopic() {
        final RealTopic realTopic = TestFactories.topics().newCompleteRealTopic();
        TestFactories.feelings().newFeelings(10);
        TestFactories.feelings().newFeelings(realTopic.getId(), 10);
        TestFactories.feelings().newFeelings(10);

        final List<FeelingData> feelingDatas = apiFeelingSearch.doSearch(realTopic, new Form());

        assertThat(feelingDatas.size()).isEqualTo(10);
    }

    private ApiFeelingSearch apiFeelingSearch;
    private User user;
}
