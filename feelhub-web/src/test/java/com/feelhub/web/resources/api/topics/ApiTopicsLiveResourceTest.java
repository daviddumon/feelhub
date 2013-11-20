package com.feelhub.web.resources.api.topics;

import com.feelhub.domain.topic.real.RealTopic;
import com.feelhub.repositories.SessionProvider;
import com.feelhub.repositories.fakeRepositories.WithFakeRepositories;
import com.feelhub.test.*;
import com.feelhub.web.*;
import com.feelhub.web.authentification.*;
import com.feelhub.web.dto.TopicData;
import com.feelhub.web.guice.DummySessionProvider;
import com.feelhub.web.representation.ModelAndView;
import com.feelhub.web.search.*;
import com.feelhub.web.search.fake.*;
import com.google.inject.*;
import org.junit.*;

import java.util.List;

import static org.fest.assertions.Assertions.*;

public class ApiTopicsLiveResourceTest {

    @Rule
    public WithFakeRepositories repositories = new WithFakeRepositories();

    @Rule
    public WebApplicationTester restlet = new WebApplicationTester();

    @Rule
    public SystemTime time = SystemTime.fixed();

    @Before
    public void before() {
        final Injector injector = Guice.createInjector(new AbstractModule() {
            @Override
            protected void configure() {
                bind(SessionProvider.class).to(DummySessionProvider.class);
                bind(TopicSearch.class).to(FakeTopicSearch.class);
                bind(FeelingSearch.class).to(FakeFeelingSearch.class);
            }
        });
        apiTopicsLiveResource = injector.getInstance(ApiTopicsLiveResource.class);
        ContextTestFactory.initResource(apiTopicsLiveResource);
        CurrentUser.set(new WebUser(TestFactories.users().createActiveUser("test@test.com"), true));
    }

    @Test
    public void returnTopicsFromLastFeelingsInData() {
        final RealTopic topicA = TestFactories.topics().newCompleteRealTopicWithHasFeelings();
        time.waitDays(1);
        final RealTopic topicB = TestFactories.topics().newCompleteRealTopicWithHasFeelings();
        TestFactories.topics().newCompleteRealTopic();

        final ModelAndView modelAndView = apiTopicsLiveResource.represent();

        assertThat(modelAndView.getTemplate()).isEqualTo("api/topics.json.ftl");
        final List<TopicData> topicDatas = (List<TopicData>) modelAndView.getData("topicDatas");
        assertThat(topicDatas.size()).isEqualTo(2);
        assertThat(topicDatas.get(0).getId()).isEqualTo(topicA.getId().toString());
        assertThat(topicDatas.get(1).getId()).isEqualTo(topicB.getId().toString());
    }

    private ApiTopicsLiveResource apiTopicsLiveResource;
}
