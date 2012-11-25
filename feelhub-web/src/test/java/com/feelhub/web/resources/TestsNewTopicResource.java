package com.feelhub.web.resources;

import com.feelhub.web.*;
import com.feelhub.web.dto.*;
import com.feelhub.web.representation.ModelAndView;
import com.google.inject.*;
import org.junit.*;
import org.restlet.data.Status;

import static org.fest.assertions.Assertions.*;
import static org.mockito.Mockito.*;

public class TestsNewTopicResource {

    @Rule
    public WebApplicationTester restlet = new WebApplicationTester();

    @Before
    public void before() {
        topicDataFactory = mock(TopicDataFactory.class);
        final Injector injector = Guice.createInjector(new AbstractModule() {
            @Override
            protected void configure() {
                bind(TopicDataFactory.class).toInstance(topicDataFactory);
            }
        });
        newTopicResource = injector.getInstance(NewTopicResource.class);
        ContextTestFactory.initResource(newTopicResource);
        description = "description";
        newTopicResource.getRequestAttributes().put("description", description);
        final TopicData topicData = new TopicData.Builder().description(description).build();
        when(topicDataFactory.getTopicData(description)).thenReturn(topicData);
    }

    @Test
    public void isMapped() {
        final ClientResource newTopicResource = restlet.newClientResource("/newtopic/description");

        newTopicResource.get();

        assertThat(newTopicResource.getStatus()).isEqualTo(Status.SUCCESS_OK);
    }

    @Test
    public void hasGoodTemplate() {
        final ModelAndView modelAndView = newTopicResource.newTopic();

        assertThat(modelAndView.getTemplate()).isEqualTo("newtopic.ftl");
    }

    @Test
    public void hasTopicDataInData() {
        final ModelAndView modelAndView = newTopicResource.newTopic();

        assertThat(modelAndView.getData("topicData")).isNotNull();
    }

    @Test
    public void getTopicDataFromFactory() {
        newTopicResource.newTopic();

        verify(topicDataFactory).getTopicData(description);
    }

    @Test
    public void topicDataContainsTopicName() {
        final ModelAndView modelAndView = newTopicResource.newTopic();

        final TopicData topicData = modelAndView.getData("topicData");
        assertThat(topicData.getDescription()).isEqualTo(description);
    }

    @Test
    public void decodeTopicName() {
        newTopicResource.getRequestAttributes().put("description", "The%20description");
        when(topicDataFactory.getTopicData("The description")).thenReturn(new TopicData.Builder().description("The description").build());

        final ModelAndView modelAndView = newTopicResource.newTopic();

        final TopicData topicData = modelAndView.getData("topicData");
        assertThat(topicData.getDescription()).isEqualTo("The description");
    }

    private NewTopicResource newTopicResource;
    private TopicDataFactory topicDataFactory;
    private String description;
}
