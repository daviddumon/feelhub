package com.feelhub.web.resources;

import com.feelhub.domain.tag.Tag;
import com.feelhub.domain.topic.usable.real.RealTopicType;
import com.feelhub.repositories.fakeRepositories.WithFakeRepositories;
import com.feelhub.test.TestFactories;
import com.feelhub.web.*;
import com.feelhub.web.dto.*;
import com.feelhub.web.representation.ModelAndView;
import com.google.inject.*;
import org.junit.*;
import org.restlet.*;
import org.restlet.data.*;

import java.util.List;

import static org.fest.assertions.Assertions.*;
import static org.mockito.Mockito.*;

public class TestsNewTopicResource {

    @Rule
    public WebApplicationTester restlet = new WebApplicationTester();

    @Rule
    public WithFakeRepositories repositories = new WithFakeRepositories();

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
        query = "query";
        newTopicResource.getRequestAttributes().put("query", query);
        final TopicData topicData = new TopicData.Builder().name(query).build();
        when(topicDataFactory.getTopicData(query)).thenReturn(topicData);
    }

    @Test
    public void isMapped() {
        final ClientResource newTopicResource = restlet.newClientResource("/newtopic?q=query");

        newTopicResource.get();

        assertThat(newTopicResource.getStatus()).isEqualTo(Status.SUCCESS_OK);
    }

    @Test
    public void hasGoodTemplate() {
        final Request request = new Request(Method.GET, "http://test.com?q=" + query);
        newTopicResource.init(Context.getCurrent(), request, new Response(request));

        final ModelAndView modelAndView = newTopicResource.newTopic();

        assertThat(modelAndView.getTemplate()).isEqualTo("newtopic.ftl");
    }

    @Test
    public void hasTopicDataInData() {
        final Request request = new Request(Method.GET, "http://test.com?q=" + query);
        newTopicResource.init(Context.getCurrent(), request, new Response(request));

        final ModelAndView modelAndView = newTopicResource.newTopic();

        assertThat(modelAndView.getData("topicData")).isNotNull();
    }

    @Test
    public void getTopicDataFromFactory() {
        final Request request = new Request(Method.GET, "http://test.com?q=" + query);
        newTopicResource.init(Context.getCurrent(), request, new Response(request));

        newTopicResource.newTopic();

        verify(topicDataFactory).getTopicData(query);
    }

    @Test
    public void topicDataContainsTopicName() {
        final Request request = new Request(Method.GET, "http://test.com?q=" + query);
        newTopicResource.init(Context.getCurrent(), request, new Response(request));

        final ModelAndView modelAndView = newTopicResource.newTopic();

        final TopicData topicData = modelAndView.getData("topicData");
        assertThat(topicData.getName()).isEqualTo(query);
    }

    @Test
    public void decodeTopicName() {
        final Request request = new Request(Method.GET, "http://test.com?q=The%20query");
        newTopicResource.init(Context.getCurrent(), request, new Response(request));
        when(topicDataFactory.getTopicData("The query")).thenReturn(new TopicData.Builder().name("The query").build());

        final ModelAndView modelAndView = newTopicResource.newTopic();

        final TopicData topicData = modelAndView.getData("topicData");
        assertThat(topicData.getName()).isEqualTo("The query");
    }

    @Test
    public void insertTypeListInTemplate() {
        final Request request = new Request(Method.GET, "http://test.com?q=" + query);
        newTopicResource.init(Context.getCurrent(), request, new Response(request));

        final ModelAndView modelAndView = newTopicResource.newTopic();

        assertThat(modelAndView.getData("types")).isNotNull();
    }

    @Test
    public void hasGoodListOfTypes() {
        final Tag tag = TestFactories.tags().newTagWithoutTopic(query);
        tag.addTopic(TestFactories.topics().newSimpleRealTopic(RealTopicType.Automobile));
        tag.addTopic(TestFactories.topics().newSimpleRealTopic(RealTopicType.Company));
        tag.addTopic(TestFactories.topics().newSimpleRealTopic(RealTopicType.Country));
        tag.addTopic(TestFactories.topics().newSimpleRealTopic(RealTopicType.Facility));
        final Request request = new Request(Method.GET, "http://test.com?q=" + query);
        newTopicResource.init(Context.getCurrent(), request, new Response(request));

        final ModelAndView modelAndView = newTopicResource.newTopic();

        final List<String> types = modelAndView.getData("types");
        assertThat(types.size()).isEqualTo(29);
    }

    @Test
    public void returnAllTypesIfNoPreviousTopic() {
        final Request request = new Request(Method.GET, "http://test.com?q=" + query);
        newTopicResource.init(Context.getCurrent(), request, new Response(request));

        final ModelAndView modelAndView = newTopicResource.newTopic();

        final List<String> types = modelAndView.getData("types");
        assertThat(types.size()).isEqualTo(31);
    }

    private NewTopicResource newTopicResource;
    private TopicDataFactory topicDataFactory;
    private String query;
}
