package com.feelhub.web.resources.api;

import com.feelhub.application.*;
import com.feelhub.domain.tag.Tag;
import com.feelhub.domain.thesaurus.FeelhubLanguage;
import com.feelhub.domain.topic.Topic;
import com.feelhub.domain.topic.http.HttpTopic;
import com.feelhub.domain.topic.real.*;
import com.feelhub.domain.user.User;
import com.feelhub.repositories.fakeRepositories.WithFakeRepositories;
import com.feelhub.test.TestFactories;
import com.feelhub.web.*;
import com.feelhub.web.authentification.*;
import com.feelhub.web.dto.TopicData;
import com.feelhub.web.representation.ModelAndView;
import com.google.common.collect.Lists;
import com.google.inject.*;
import org.junit.*;
import org.restlet.*;
import org.restlet.data.*;

import java.util.List;

import static org.fest.assertions.Assertions.*;
import static org.mockito.Mockito.*;

public class TestsApiTopicsResource {

    @Rule
    public WithFakeRepositories repositories = new WithFakeRepositories();

    @Before
    public void before() {
        user = TestFactories.users().createFakeUser("mail@mail.com", "full name");
        CurrentUser.set(new WebUser(user, true));
        topicService = mock(TopicService.class);
        tagService = mock(TagService.class);
        final Injector injector = Guice.createInjector(new AbstractModule() {
            @Override
            protected void configure() {
                bind(TagService.class).toInstance(tagService);
                bind(TopicService.class).toInstance(topicService);
            }
        });
        apiTopicsResource = injector.getInstance(ApiTopicsResource.class);
        apiTopicsResource.setResponse(new Response(new Request()));
        ContextTestFactory.initResource(apiTopicsResource);
    }

    @Test
    public void canCreateRealTopicWithCorrectUser() {
        final RealTopic realTopic = TestFactories.topics().newCompleteRealTopic();
        when(topicService.createRealTopic(any(FeelhubLanguage.class), anyString(), any(RealTopicType.class), any(User.class))).thenReturn(realTopic);

        apiTopicsResource.createTopic(getGoodFormWithRealTopic());

        assertThat(apiTopicsResource.getStatus()).isEqualTo(Status.SUCCESS_CREATED);
    }

    @Test
    public void canCreateHttpTopicWithCorrectUser() {
        final HttpTopic httpTopic = TestFactories.topics().newCompleteHttpTopic();
        when(topicService.createHttpTopic(anyString(), any(User.class))).thenReturn(httpTopic);

        apiTopicsResource.createTopic(getGoodFormWithHttpTopic());

        assertThat(apiTopicsResource.getStatus()).isEqualTo(Status.SUCCESS_CREATED);
    }

    @Test
    public void cannotCreateRealTopicWithoutAuthentificatedUser() {
        CurrentUser.set(new AnonymousUser());

        apiTopicsResource.createTopic(getGoodFormWithRealTopic());

        assertThat(apiTopicsResource.getStatus()).isEqualTo(Status.CLIENT_ERROR_UNAUTHORIZED);
    }

    @Test
    public void cannotCreateHttpTopicWithoutAuthentificatedUser() {
        CurrentUser.set(new AnonymousUser());

        apiTopicsResource.createTopic(getGoodFormWithHttpTopic());

        assertThat(apiTopicsResource.getStatus()).isEqualTo(Status.CLIENT_ERROR_UNAUTHORIZED);
    }

    @Test
    public void createTopic() {
        final RealTopic realTopic = TestFactories.topics().newCompleteRealTopic();
        when(topicService.createRealTopic(any(FeelhubLanguage.class), anyString(), any(RealTopicType.class), any(User.class))).thenReturn(realTopic);

        apiTopicsResource.createTopic(getGoodFormWithRealTopic());

        verify(topicService).createRealTopic(CurrentUser.get().getLanguage(), getGoodFormWithRealTopic().getFirstValue("name"), RealTopicType.valueOf(getGoodFormWithRealTopic().getFirstValue("type")), CurrentUser.get().getUser());
    }

    @Test
    public void errorIfMissingType() {
        final Form form = new Form();
        form.add("description", "description");

        apiTopicsResource.createTopic(form);

        assertThat(apiTopicsResource.getStatus()).isEqualTo(Status.CLIENT_ERROR_BAD_REQUEST);
    }

    @Test
    public void errorIfMissingDescription() {
        final Form form = new Form();
        form.add("type", RealTopicType.City.toString());

        apiTopicsResource.createTopic(form);

        assertThat(apiTopicsResource.getStatus()).isEqualTo(Status.CLIENT_ERROR_BAD_REQUEST);
    }

    @Test
    public void setLocationRefToNewRealTopic() {
        final RealTopic realTopic = TestFactories.topics().newCompleteRealTopic();
        when(topicService.createRealTopic(any(FeelhubLanguage.class), anyString(), any(RealTopicType.class), any(User.class))).thenReturn(realTopic);

        apiTopicsResource.createTopic(getGoodFormWithRealTopic());

        assertThat(apiTopicsResource.getLocationRef().toString()).isEqualTo(new WebReferenceBuilder(apiTopicsResource.getContext()).buildUri("/topic/" + realTopic.getId()));
    }

    @Test
    public void setLocationRefToNewHttpTopic() {
        final HttpTopic httpTopic = TestFactories.topics().newCompleteHttpTopic();
        when(topicService.createHttpTopic(anyString(), any(User.class))).thenReturn(httpTopic);

        apiTopicsResource.createTopic(getGoodFormWithHttpTopic());

        assertThat(apiTopicsResource.getLocationRef().toString()).isEqualTo(new WebReferenceBuilder(apiTopicsResource.getContext()).buildUri("/topic/" + httpTopic.getId()));
    }

    @Test
    public void canGetTopics() {
        final Request request = new Request(Method.GET, "http://test.com?q=test");
        apiTopicsResource.init(Context.getCurrent(), request, new Response(request));

        final ModelAndView modelAndView = apiTopicsResource.getTopics();

        assertThat(apiTopicsResource.getStatus()).isEqualTo(Status.SUCCESS_OK);
        assertThat(modelAndView.getTemplate()).isEqualTo("api/topics.json.ftl");
    }

    @Test
    public void modelHasTopicDatas() {
        final Request request = new Request(Method.GET, "http://test.com?q=test");
        apiTopicsResource.init(Context.getCurrent(), request, new Response(request));

        final ModelAndView modelAndView = apiTopicsResource.getTopics();

        assertThat(modelAndView.getData("topicDatas")).isNotNull();
        final List<TopicData> topicDatas = modelAndView.getData("topicDatas");
        assertThat(topicDatas.size()).isZero();
    }

    @Test
    public void returnListOfTopicData() {
        final Tag tag = TestFactories.tags().newTagWithoutTopic();
        final RealTopic topic1 = TestFactories.topics().newCompleteRealTopic();
        tag.addTopic(topic1);
        final RealTopic topic2 = TestFactories.topics().newCompleteRealTopic();
        tag.addTopic(topic2);
        final List<Topic> topics = Lists.newArrayList();
        topics.add(topic1);
        topics.add(topic2);
        when(topicService.getTopics(tag.getId())).thenReturn(topics);
        final Request request = new Request(Method.GET, "http://test.com?q=" + tag.getId());
        apiTopicsResource.init(Context.getCurrent(), request, new Response(request));

        final ModelAndView modelAndView = apiTopicsResource.getTopics();

        assertThat(modelAndView.getData("topicDatas")).isNotNull();
        final List<TopicData> topicDatas = modelAndView.getData("topicDatas");
        assertThat(topicDatas.size()).isEqualTo(2);
    }

    @Test
    public void errorIfNoQuery() {
        final Request request = new Request(Method.GET, "http://test.com");
        apiTopicsResource.init(Context.getCurrent(), request, new Response(request));

        final ModelAndView modelAndView = apiTopicsResource.getTopics();

        assertThat(apiTopicsResource.getStatus()).isEqualTo(Status.CLIENT_ERROR_BAD_REQUEST);
    }

    @Test
    public void canGetTopicsWithGoodStatus() {
        final Request request = new Request(Method.GET, "http://test.com?q=test");
        apiTopicsResource.init(Context.getCurrent(), request, new Response(request));

        final ModelAndView modelAndView = apiTopicsResource.getTopics();

        assertThat(apiTopicsResource.getStatus()).isEqualTo(Status.SUCCESS_OK);
    }

    @Test
    public void canCreateHttpTopic() {
        final HttpTopic httpTopic = TestFactories.topics().newCompleteHttpTopic();
        when(topicService.createHttpTopic(anyString(), any(User.class))).thenReturn(httpTopic);

        apiTopicsResource.createTopic(getGoodFormWithHttpTopic());

        verify(topicService).createHttpTopic(getGoodFormWithHttpTopic().getFirstValue("name"), CurrentUser.get().getUser());
    }

    private Form getGoodFormWithRealTopic() {
        final Form form = new Form();
        form.add("name", "name");
        form.add("type", RealTopicType.Automobile.toString());
        return form;
    }

    private Form getGoodFormWithHttpTopic() {
        final Form form = new Form();
        form.add("name", "http://www.google.fr");
        return form;
    }

    private ApiTopicsResource apiTopicsResource;
    private User user;
    private TopicService topicService;
    private TagService tagService;
}
