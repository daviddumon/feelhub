package com.feelhub.web.resources;

import com.feelhub.application.TagService;
import com.feelhub.domain.topic.Topic;
import com.feelhub.repositories.fakeRepositories.WithFakeRepositories;
import com.feelhub.test.TestFactories;
import com.feelhub.web.*;
import com.feelhub.web.representation.ModelAndView;
import com.google.common.collect.Lists;
import org.junit.*;
import org.restlet.Context;
import org.restlet.data.Status;

import java.util.*;

import static org.fest.assertions.Assertions.*;
import static org.mockito.Mockito.*;

public class TestsSearchResource {

    @Rule
    public WebApplicationTester restlet = new WebApplicationTester();

    @Rule
    public WithFakeRepositories repositories = new WithFakeRepositories();

    @Before
    public void before() {
        tagService = mock(TagService.class);
        searchResource = new SearchResource(tagService);
        ContextTestFactory.initResource(searchResource);
        query = "query";
        topics = Lists.newArrayList();
        when(tagService.search(query)).thenReturn(topics);
    }

    @Test
    public void canSearch() {
        topics.add(TestFactories.topics().newTopic());
        topics.add(TestFactories.topics().newTopic());
        searchResource.getRequest().getAttributes().put("q", query);

        final ModelAndView results = searchResource.search();

        assertThat(results).isNotNull();
        assertThat(searchResource.getStatus()).isEqualTo(Status.SUCCESS_OK);
        final Map<String, Object> data = results.getData();
        assertThat(data.get("topics")).isNotNull();
        final List<Topic> topicList = (List<Topic>) data.get("topics");
        assertThat(topicList.size()).isEqualTo(2);
    }

    @Test
    public void isMapped() {
        final ClientResource searchResource = restlet.newClientResource("/search/" + query);

        searchResource.get();

        assertThat(searchResource.getStatus()).isEqualTo(Status.REDIRECTION_SEE_OTHER);
    }

    @Test
    public void doTagSearch() {
        searchResource.getRequest().getAttributes().put("q", query);

        searchResource.search();

        verify(tagService).search(query);
    }

    @Test
    public void whenMultipleResultsTemplateIsSearch() {
        topics.add(TestFactories.topics().newTopic());
        topics.add(TestFactories.topics().newTopic());
        searchResource.getRequest().getAttributes().put("q", query);

        final ModelAndView search = searchResource.search();

        assertThat(search.getTemplate()).isEqualTo("search.ftl");
    }

    @Test
    public void whenOnlyOneResultRedirectToTopicResource() {
        topics.add(TestFactories.topics().newTopic());
        searchResource.getRequest().getAttributes().put("q", query);

        searchResource.search();

        assertThat(searchResource.getStatus()).isEqualTo(Status.REDIRECTION_SEE_OTHER);
        final WebReferenceBuilder webReferenceBuilder = new WebReferenceBuilder(Context.getCurrent());
        assertThat(searchResource.getLocationRef().toString()).isEqualTo(webReferenceBuilder.buildUri("/topic/" + topics.get(0).getId()));
    }

    @Test
    public void whenNoResultsRedirectToNewResource() {
        searchResource.getRequest().getAttributes().put("q", query);

        searchResource.search();

        assertThat(searchResource.getStatus()).isEqualTo(Status.REDIRECTION_SEE_OTHER);
        final WebReferenceBuilder webReferenceBuilder = new WebReferenceBuilder(Context.getCurrent());
        assertThat(searchResource.getLocationRef().toString()).isEqualTo(webReferenceBuilder.buildUri("/newtopic/" + query));
    }

    private SearchResource searchResource;
    private TagService tagService;
    private String query;
    private List<Topic> topics;
}
