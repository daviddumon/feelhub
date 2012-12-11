package com.feelhub.web.resources.api;

import com.feelhub.application.*;
import com.feelhub.domain.tag.Tag;
import com.feelhub.domain.thesaurus.FeelhubLanguage;
import com.feelhub.domain.topic.usable.real.*;
import com.feelhub.domain.user.User;
import com.feelhub.repositories.fakeRepositories.WithFakeRepositories;
import com.feelhub.test.TestFactories;
import com.feelhub.web.*;
import com.feelhub.web.authentification.*;
import com.feelhub.web.dto.TopicData;
import com.feelhub.web.representation.ModelAndView;
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
                bind(TopicService.class).toInstance(topicService);
                bind(TagService.class).toInstance(tagService);
            }
        });
        apiTopicsResource = injector.getInstance(ApiTopicsResource.class);
        apiTopicsResource.setResponse(new Response(new Request()));
        ContextTestFactory.initResource(apiTopicsResource);
    }

    @Test
    public void canCreateWithCorrectUser() {
        final RealTopic realTopic = TestFactories.topics().newCompleteRealTopic();
        when(topicService.createTopic(any(FeelhubLanguage.class), anyString(), any(RealTopicType.class), any(User.class))).thenReturn(realTopic);

        apiTopicsResource.createTopic(getGoodForm());

        assertThat(apiTopicsResource.getStatus()).isEqualTo(Status.SUCCESS_CREATED);
    }

    @Test
    public void cannotCreateWithoutAuthentificatedUser() {
        CurrentUser.set(new AnonymousUser());

        apiTopicsResource.createTopic(getGoodForm());

        assertThat(apiTopicsResource.getStatus()).isEqualTo(Status.CLIENT_ERROR_UNAUTHORIZED);
    }

    @Test
    public void createTopic() {
        final RealTopic realTopic = TestFactories.topics().newCompleteRealTopic();
        when(topicService.createTopic(any(FeelhubLanguage.class), anyString(), any(RealTopicType.class), any(User.class))).thenReturn(realTopic);

        apiTopicsResource.createTopic(getGoodForm());

        verify(topicService).createTopic(CurrentUser.get().getLanguage(), getGoodForm().getFirstValue("description"), RealTopicType.valueOf(getGoodForm().getFirstValue("type")), CurrentUser.get().getUser());
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
    public void setLocationRefToNewTopic() {
        final RealTopic realTopic = TestFactories.topics().newCompleteRealTopic();
        when(topicService.createTopic(any(FeelhubLanguage.class), anyString(), any(RealTopicType.class), any(User.class))).thenReturn(realTopic);

        apiTopicsResource.createTopic(getGoodForm());

        assertThat(apiTopicsResource.getLocationRef().toString()).isEqualTo(new WebReferenceBuilder(apiTopicsResource.getContext()).buildUri("/topic/" + realTopic.getId()));
    }

    @Test
    public void canGetTopics() {
        when(tagService.lookUp(anyString())).thenReturn(new Tag());
        final Request request = new Request(Method.GET, "http://test.com?q=test");
        apiTopicsResource.init(Context.getCurrent(), request, new Response(request));

        final ModelAndView modelAndView = apiTopicsResource.getTopics();

        assertThat(apiTopicsResource.getStatus()).isEqualTo(Status.SUCCESS_OK);
        assertThat(modelAndView.getTemplate()).isEqualTo("api/topics.json.ftl");
    }

    @Test
    public void modelHasTopicDatas() {
        when(tagService.lookUp(anyString())).thenReturn(new Tag());
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
        when(tagService.lookUp(tag.getId())).thenReturn(tag);
        when(topicService.lookUp(topic1.getId())).thenReturn(topic1);
        when(topicService.lookUp(topic2.getId())).thenReturn(topic2);
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
        when(tagService.lookUp(anyString())).thenReturn(new Tag());
        final Request request = new Request(Method.GET, "http://test.com?q=test");
        apiTopicsResource.init(Context.getCurrent(), request, new Response(request));

        final ModelAndView modelAndView = apiTopicsResource.getTopics();

        assertThat(apiTopicsResource.getStatus()).isEqualTo(Status.SUCCESS_OK);
    }

    private Form getGoodForm() {
        final Form form = new Form();
        form.add("description", "description");
        form.add("type", RealTopicType.Automobile.toString());
        return form;
    }

    private ApiTopicsResource apiTopicsResource;
    private User user;
    private TopicService topicService;
    private TagService tagService;
}