package com.feelhub.web.resources;

import com.feelhub.repositories.fakeRepositories.WithFakeRepositories;
import com.feelhub.web.*;
import com.feelhub.web.representation.ModelAndView;
import com.google.inject.*;
import org.junit.*;
import org.restlet.*;
import org.restlet.data.*;

import java.util.Map;

import static org.fest.assertions.Assertions.*;

public class TestsSearchResource {

    @Rule
    public WebApplicationTester restlet = new WebApplicationTester();

    @Rule
    public WithFakeRepositories repositories = new WithFakeRepositories();

    @Before
    public void before() {
        final Injector injector = Guice.createInjector(new AbstractModule() {
            @Override
            protected void configure() {
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

    private SearchResource searchResource;
    private String query;
}
