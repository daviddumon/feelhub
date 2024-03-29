package com.feelhub.web.resources;

import com.feelhub.repositories.SessionProvider;
import com.feelhub.repositories.fakeRepositories.WithFakeRepositories;
import com.feelhub.test.TestFactories;
import com.feelhub.web.*;
import com.feelhub.web.authentification.*;
import com.feelhub.web.guice.DummySessionProvider;
import com.feelhub.web.representation.ModelAndView;
import com.feelhub.web.resources.api.topics.ApiTopicsSearchResource;
import com.feelhub.web.search.TopicSearch;
import com.feelhub.web.search.fake.FakeTopicSearch;
import com.google.inject.*;
import org.junit.*;

import static org.fest.assertions.Assertions.*;
import static org.mockito.Mockito.*;

public class HomeResourceTest {

    @Rule
    public WithFakeRepositories repositories = new WithFakeRepositories();

    @Rule
    public WebApplicationTester restet = new WebApplicationTester();

    @Before
    public void before() {
        apiTopicsLiveResource = mock(ApiTopicsSearchResource.class);
        final Injector injector = Guice.createInjector(new AbstractModule() {
            @Override
            protected void configure() {
                bind(SessionProvider.class).to(DummySessionProvider.class);
                bind(TopicSearch.class).to(FakeTopicSearch.class);
                bind(ApiTopicsSearchResource.class).toInstance(apiTopicsLiveResource);
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
    public void canSetBookmarkletShow() {
        final ModelAndView modelAndView = homeResource.represent();

        assertThat(modelAndView.getData("bookmarkletShow")).isNotNull();
    }

    private HomeResource homeResource;
    private ApiTopicsSearchResource apiTopicsLiveResource;
}
