package com.feelhub.web.resources;

import com.feelhub.repositories.SessionProvider;
import com.feelhub.repositories.fakeRepositories.WithFakeRepositories;
import com.feelhub.test.TestFactories;
import com.feelhub.web.*;
import com.feelhub.web.authentification.*;
import com.feelhub.web.dto.TopicData;
import com.feelhub.web.guice.DummySessionProvider;
import com.feelhub.web.representation.ModelAndView;
import com.feelhub.web.resources.api.topics.ApiTopicsLastFeelingsResource;
import com.feelhub.web.search.TopicSearch;
import com.feelhub.web.search.fake.FakeTopicSearch;
import com.google.common.collect.Lists;
import com.google.inject.*;
import org.junit.*;

import java.util.List;

import static org.fest.assertions.Assertions.*;
import static org.mockito.Mockito.*;

public class HomeResourceTest {

    @Rule
    public WithFakeRepositories repositories = new WithFakeRepositories();

    @Rule
    public WebApplicationTester restet = new WebApplicationTester();

    @Before
    public void before() {
        apiTopicsLastFeelingsResource = mock(ApiTopicsLastFeelingsResource.class);
        final Injector injector = Guice.createInjector(new AbstractModule() {
            @Override
            protected void configure() {
                bind(SessionProvider.class).to(DummySessionProvider.class);
                bind(TopicSearch.class).to(FakeTopicSearch.class);
                bind(ApiTopicsLastFeelingsResource.class).toInstance(apiTopicsLastFeelingsResource);
            }
        });
        homeResource = injector.getInstance(HomeResource.class);
        ContextTestFactory.initResource(homeResource);
        CurrentUser.set(new WebUser(TestFactories.users().createActiveUser("test@test.com"), true));
    }

    @Test
    public void hasLocalesInData() {
        final ModelAndView modelAndView = homeResource.represent();

        assertThat(modelAndView.getData("locales")).isNotNull();
    }

    @Test
    public void hasTopicsInData() {
        final List<TopicData> initialDatas = Lists.newArrayList();
        initialDatas.add(new TopicData.Builder().build());
        initialDatas.add(new TopicData.Builder().build());
        when(apiTopicsLastFeelingsResource.getTopicDatas(anyInt(), anyInt())).thenReturn(initialDatas);

        final ModelAndView modelAndView = homeResource.represent();

        assertThat(modelAndView.getData("topicDatas")).isNotNull();
        final List<TopicData> result = modelAndView.getData("topicDatas");
        assertThat(result.size()).isEqualTo(2);
    }

    @Test
    public void hasWelcomePanelShowInDataForTheFirstView() {
        final ModelAndView modelAndView = homeResource.represent();

        assertThat(modelAndView.getData("welcomePanelShow")).isNotNull();
    }

    @Test
    public void canSetWelcomeShowToFalseAfterFirstView() {
        homeResource.represent();

        assertThat(CurrentUser.get().getUser().getWelcomePanelShow()).isFalse();
    }

    @Test
    public void canSetInstalledBookmarkletInDataIfWelcomePanelHasBeenShown() {
        CurrentUser.get().getUser().setWelcomePanelShow(false);

        final ModelAndView modelAndView = homeResource.represent();

        assertThat(modelAndView.getData("bookmarkletShow")).isNotNull();
    }

    private HomeResource homeResource;
    private ApiTopicsLastFeelingsResource apiTopicsLastFeelingsResource;
}
