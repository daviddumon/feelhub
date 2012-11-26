package com.feelhub.web.resources;

import com.feelhub.application.TagService;
import com.feelhub.domain.topic.Topic;
import com.feelhub.repositories.fakeRepositories.WithFakeRepositories;
import com.feelhub.web.*;
import com.feelhub.web.representation.ModelAndView;
import org.junit.*;
import org.restlet.data.Status;

import java.util.*;

import static org.fest.assertions.Assertions.*;

public class TestsSearchResource {

    @Rule
    public WebApplicationTester restlet = new WebApplicationTester();

    @Rule
    public WithFakeRepositories repositories = new WithFakeRepositories();

    @Before
    public void before() {
        searchResource = new SearchResource();
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
    public void canSearch() {
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
    private TagService tagService;
    private String query;
    private List<Topic> topics;
}
