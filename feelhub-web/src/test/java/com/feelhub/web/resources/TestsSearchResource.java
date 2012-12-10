package com.feelhub.web.resources;

import com.feelhub.repositories.fakeRepositories.WithFakeRepositories;
import com.feelhub.web.*;
import com.feelhub.web.representation.ModelAndView;
import com.google.inject.*;
import org.junit.*;
import org.restlet.data.Status;

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
        final ClientResource searchResource = restlet.newClientResource("/search/" + query);

        searchResource.get();

        assertThat(searchResource.getStatus()).isEqualTo(Status.SUCCESS_OK);
    }

    @Test
    public void returnGoodTemplate() {
        searchResource.getRequest().getAttributes().put("q", query);

        final ModelAndView search = searchResource.search();

        assertThat(search.getTemplate()).isEqualTo("search.ftl");
    }

    @Test
    public void putDescriptionInModel() {
        searchResource.getRequest().getAttributes().put("q", query);

        final ModelAndView results = searchResource.search();

        assertThat(results).isNotNull();
        assertThat(searchResource.getStatus()).isEqualTo(Status.SUCCESS_OK);
        final Map<String, Object> data = results.getData();
        assertThat(data.get("q")).isNotNull();
        final String q = data.get("q").toString();
        assertThat(q).isEqualTo(query);
    }

    private SearchResource searchResource;
    private String query;
}
