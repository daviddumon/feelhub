package com.feelhub.web.resources.api.topics;

import com.feelhub.application.search.TopicSearch;
import com.feelhub.domain.eventbus.WithDomainEvent;
import com.feelhub.domain.topic.Topic;
import com.feelhub.domain.topic.real.RealTopic;
import com.feelhub.repositories.fakeRepositories.WithFakeRepositories;
import com.feelhub.test.TestFactories;
import com.feelhub.web.*;
import com.feelhub.web.api.ApiFeelingSearch;
import com.feelhub.web.dto.FeelingData;
import com.feelhub.web.representation.ModelAndView;
import com.google.common.collect.Lists;
import com.google.inject.*;
import org.json.JSONException;
import org.junit.*;
import org.junit.rules.ExpectedException;
import org.restlet.*;
import org.restlet.data.*;

import java.io.IOException;
import java.util.*;

import static org.fest.assertions.Assertions.*;
import static org.mockito.Mockito.*;

public class ApiTopicFeelingsResourceTest {

    @Rule
    public WithFakeRepositories repositories = new WithFakeRepositories();

    @Rule
    public ExpectedException exception = ExpectedException.none();

    @Rule
    public WebApplicationTester restlet = new WebApplicationTester();

    @Rule
    public WithDomainEvent events = new WithDomainEvent();

    @Before
    public void before() {
        topicSearch = mock(TopicSearch.class);
        apiFeelingSearch = mock(ApiFeelingSearch.class);
        final Injector injector = Guice.createInjector(new AbstractModule() {
            @Override
            protected void configure() {
                bind(TopicSearch.class).toInstance(topicSearch);
                bind(ApiFeelingSearch.class).toInstance(apiFeelingSearch);
            }
        });
        apiTopicFeelingsResource = injector.getInstance(ApiTopicFeelingsResource.class);
    }

    @Test
    public void feelingsResourceIsMapped() {
        final RealTopic realTopic = TestFactories.topics().newCompleteRealTopic();
        final ClientResource feelingsResource = restlet.newClientResource("/api/topic/" + realTopic.getId() + "/feelings");

        feelingsResource.get();

        assertThat(feelingsResource.getStatus()).isEqualTo(Status.SUCCESS_OK);
    }

    @Test
    public void canGetWithQueryString() {
        final RealTopic realTopic = TestFactories.topics().newCompleteRealTopic();
        final ClientResource feelingsResource = restlet.newClientResource("/api/topic/" + realTopic.getId() + "/feelings?q=test");

        feelingsResource.get();

        assertThat(feelingsResource.getStatus()).isEqualTo(Status.SUCCESS_OK);
    }

    @Test
    public void canSetBadRequestIfUnknownTopic() {
        final ClientResource feelingsResource = restlet.newClientResource("/api/topic/" + UUID.randomUUID() + "/feelings");

        feelingsResource.get();

        assertThat(feelingsResource.getStatus()).isEqualTo(Status.CLIENT_ERROR_BAD_REQUEST);
    }

    @Test
    public void canThrowFeelhubJsonException() {
        final RealTopic realTopic = TestFactories.topics().newCompleteRealTopic();
        final ClientResource resource = restlet.newClientResource("/api/topic/" + realTopic.getId() + "/feelings?skip=0&limit=101");

        resource.get();

        assertThat(resource.getStatus()).isEqualTo(Status.CLIENT_ERROR_BAD_REQUEST);
    }

    @Test
    public void returnFeelingDatas() throws IOException, JSONException {
        final List<FeelingData> feelingDatas = Lists.newArrayList();
        feelingDatas.add(new FeelingData.Builder().build());
        when(apiFeelingSearch.doSearchForATopic(any(Topic.class), any(Form.class))).thenReturn(feelingDatas);
        when(topicSearch.lookUpCurrent(any(UUID.class))).thenReturn(TestFactories.topics().newCompleteRealTopic());
        apiTopicFeelingsResource.setResponse(new Response(new Request()));
        ContextTestFactory.initResource(apiTopicFeelingsResource);
        final HashMap<String, Object> attributes = new HashMap<String, Object>();
        attributes.put("topicId", UUID.randomUUID().toString());
        apiTopicFeelingsResource.getRequest().setAttributes(attributes);

        final ModelAndView modelAndView = apiTopicFeelingsResource.getFeelings();

        assertThat(modelAndView.getData("feelingDatas")).isNotNull();
    }

    private ApiTopicFeelingsResource apiTopicFeelingsResource;
    private TopicSearch topicSearch;
    private ApiFeelingSearch apiFeelingSearch;
}
