package com.feelhub.web.resources;

import com.feelhub.application.TopicService;
import com.feelhub.domain.thesaurus.FeelhubLanguage;
import com.feelhub.domain.topic.Topic;
import com.feelhub.repositories.fakeRepositories.WithFakeRepositories;
import com.feelhub.test.TestFactories;
import com.feelhub.web.*;
import com.feelhub.web.dto.TopicData;
import com.feelhub.web.representation.ModelAndView;
import com.google.common.collect.Lists;
import com.google.inject.*;
import org.junit.*;
import org.restlet.*;
import org.restlet.data.*;

import java.util.*;

import static org.fest.assertions.Assertions.*;
import static org.mockito.Mockito.*;

public class SearchResourceTest {

    @Rule
    public WebApplicationTester restlet = new WebApplicationTester();

    @Rule
    public WithFakeRepositories repositories = new WithFakeRepositories();

    @Before
    public void before() {
        topicService = mock(TopicService.class);
        final Injector injector = Guice.createInjector(new AbstractModule() {
            @Override
            protected void configure() {
                bind(TopicService.class).toInstance(topicService);
            }
        });
        searchResource = injector.getInstance(SearchResource.class);
        ContextTestFactory.initResource(searchResource);
        query = "query";
    }

    @Test
    public void isMapped() {
        final ClientResource searchResource = restlet.newClientResource("/search?q=name");

        searchResource.get();

        assertThat(searchResource.getStatus()).isEqualTo(Status.SUCCESS_OK);
    }

    @Test
    public void errorIfNoQuery() {
        final ClientResource searchResource = restlet.newClientResource("/search");

        searchResource.get();

        assertThat(searchResource.getStatus()).isEqualTo(Status.CLIENT_ERROR_BAD_REQUEST);
    }

    @Test
    public void ifErrorReturnErrorTemplate() {
        final ModelAndView search = searchResource.search();

        assertThat(search.getTemplate()).isEqualTo("error.ftl");
    }

    @Test
    public void putDescriptionInModel() {
        final Request request = new Request(Method.GET, "http://test.com?q=" + query);
        searchResource.init(Context.getCurrent(), request, new Response(request));

        final ModelAndView results = searchResource.search();

        assertThat(results).isNotNull();
        assertThat(searchResource.getStatus()).isEqualTo(Status.SUCCESS_OK);
        final Map<String, Object> data = results.getData();
        assertThat(data.get("q")).isNotNull();
        final String q = data.get("q").toString();
        assertThat(q).isEqualTo(query);
    }

    @Test
    public void returnGoodTemplateForRealTopicQuery() {
        final String realTag = "fruit";
        final Request request = new Request(Method.GET, "http://test.com?q=" + realTag);
        searchResource.init(Context.getCurrent(), request, new Response(request));

        final ModelAndView modelAndView = searchResource.search();

        assertThat(modelAndView.getTemplate()).isEqualTo("search.ftl");
        assertThat(modelAndView.getData("type").toString()).isEqualTo("real");
    }

    @Test
    public void returnGoodTemplateForWebTopicQuery() {
        final String webTag = "http://www.google.fr";
        final Request request = new Request(Method.GET, "http://test.com?q=" + webTag);
        searchResource.init(Context.getCurrent(), request, new Response(request));

        final ModelAndView modelAndView = searchResource.search();

        assertThat(modelAndView.getTemplate()).isEqualTo("search.ftl");
        assertThat(modelAndView.getData("type").toString()).isEqualTo("http");
    }

    @Test
    public void fetchInitialSearchResults() {
        final String webTag = "http://www.google.fr";
        final Request request = new Request(Method.GET, "http://test.com?q=" + webTag);
        searchResource.init(Context.getCurrent(), request, new Response(request));

        final ModelAndView modelAndView = searchResource.search();

        verify(topicService).getTopics(webTag, FeelhubLanguage.reference());
    }

    @Test
    public void hasInitialDatasInModelForHttpTopic() {
        final String webTag = "http://www.google.fr";
        final Request request = new Request(Method.GET, "http://test.com?q=" + webTag);
        searchResource.init(Context.getCurrent(), request, new Response(request));

        final ModelAndView modelAndView = searchResource.search();

        assertThat(modelAndView.getData("topicDatas")).isNotNull();
    }

    @Test
    public void hasInitialDatasInModelForRealTopic() {
        final String realTag = "fruit";
        final Request request = new Request(Method.GET, "http://test.com?q=" + realTag);
        searchResource.init(Context.getCurrent(), request, new Response(request));

        final ModelAndView modelAndView = searchResource.search();

        assertThat(modelAndView.getData("topicDatas")).isNotNull();
    }

    @Test
    public void dataIsSearchResults() {
        final String realTag = "fruit";
        final Request request = new Request(Method.GET, "http://test.com?q=" + realTag);
        searchResource.init(Context.getCurrent(), request, new Response(request));
        final List<Topic> results = Lists.newArrayList();
        results.add(TestFactories.topics().newCompleteRealTopic());
        results.add(TestFactories.topics().newCompleteRealTopic());
        when(topicService.getTopics(realTag, FeelhubLanguage.reference())).thenReturn(results);

        final ModelAndView modelAndView = searchResource.search();

        final List<TopicData> topicDatas = modelAndView.getData("topicDatas");
        assertThat(topicDatas.size()).isEqualTo(2);
    }

    private SearchResource searchResource;
    private String query;
    private TopicService topicService;
}
