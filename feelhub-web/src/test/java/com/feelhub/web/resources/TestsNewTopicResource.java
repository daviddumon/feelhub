package com.feelhub.web.resources;

import com.feelhub.web.*;
import com.feelhub.web.representation.ModelAndView;
import org.junit.*;
import org.restlet.data.Status;

import static org.fest.assertions.Assertions.*;

public class TestsNewTopicResource {

    @Rule
    public WebApplicationTester restlet = new WebApplicationTester();

    @Before
    public void before() {
        newTopicResource = new NewTopicResource();
        ContextTestFactory.initResource(newTopicResource);
        newTopicResource.getRequestAttributes().put("topicname", "topicname");
    }

    @Test
    public void isMapped() {
        final ClientResource newTopicResource = restlet.newClientResource("/newtopic/topicName");

        newTopicResource.get();

        assertThat(newTopicResource.getStatus()).isEqualTo(Status.SUCCESS_OK);
    }

    @Test
    public void hasGoodTemplate() {
        final ModelAndView modelAndView = newTopicResource.newTopic();

        assertThat(modelAndView.getTemplate()).isEqualTo("newtopic.ftl");
    }

    @Test
    public void hasTopicNameInData() {
        final ModelAndView modelAndView = newTopicResource.newTopic();

        assertThat(modelAndView.getData("topicname")).isNotNull();
    }

    private NewTopicResource newTopicResource;
}
