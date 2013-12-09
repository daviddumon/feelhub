package com.feelhub.web.resources.api.topics;

import com.feelhub.domain.topic.Topic;
import com.feelhub.domain.topic.real.RealTopic;
import com.feelhub.domain.topic.real.RealTopicType;
import com.feelhub.domain.user.User;
import com.feelhub.web.ContextTestFactory;
import com.feelhub.web.authentification.CurrentUser;
import com.feelhub.web.authentification.WebUser;
import com.feelhub.web.dto.TopicData;
import com.feelhub.web.dto.TopicDataFactory;
import com.feelhub.web.representation.ModelAndView;
import com.feelhub.web.search.TopicFullTextSearch;
import com.google.common.collect.Lists;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.List;
import java.util.UUID;

import static org.fest.assertions.Assertions.*;
import static org.mockito.Mockito.*;

public class ApiTopicsFullTextSearchResourceTest {

    @Before
    public void setUp() throws Exception {
        search = mock(TopicFullTextSearch.class);
        resource = new ApiTopicsFullTextSearchResource(search, new TopicDataFactory());
        ContextTestFactory.initResource(resource);
        CurrentUser.set(new WebUser(new User(), true));
    }

    @After
    public void tearDown() throws Exception {
        CurrentUser.set(null);
    }

    @Test
    public void peutDemanderARechercher() {
        givenASearchWith("arpinum");

        resource.search();

        verify(search).execute("arpinum");
    }

    @Test
    public void retourneDesTopicsData() {
        givenASearchWith("arpinum");
        RealTopic topic = new RealTopic(UUID.randomUUID(), RealTopicType.Anniversary);
        givenSearchReturns(topic);

        ModelAndView modelAndView = resource.search();

        assertThat(modelAndView.getData().containsKey("topicDatas")).isTrue();
        List<TopicData> topicsData = (List<TopicData>) modelAndView.getData().get("topicDatas");
        assertThat(topicsData).hasSize(1);
        assertThat(topicsData).onProperty("id").contains(topic.getId().toString());
    }

    private void givenSearchReturns(RealTopic topic) {
        when(search.execute(anyString())).thenReturn(Lists.<Topic>newArrayList(topic));
    }

    private void givenASearchWith(String searchString) {
        resource.getRequest().setResourceRef("search?q=" +
                searchString);
    }

    private TopicFullTextSearch search;
    private ApiTopicsFullTextSearchResource resource;
}
