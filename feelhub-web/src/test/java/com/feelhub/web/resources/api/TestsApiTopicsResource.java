package com.feelhub.web.resources.api;

import com.feelhub.application.TopicService;
import com.feelhub.domain.thesaurus.FeelhubLanguage;
import com.feelhub.domain.topic.*;
import com.feelhub.domain.user.User;
import com.feelhub.repositories.fakeRepositories.WithFakeRepositories;
import com.feelhub.test.TestFactories;
import com.feelhub.web.*;
import com.feelhub.web.authentification.*;
import com.google.inject.*;
import org.junit.*;
import org.restlet.*;
import org.restlet.data.*;

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
        final Injector injector = Guice.createInjector(new AbstractModule() {
            @Override
            protected void configure() {
                bind(TopicService.class).toInstance(topicService);
            }
        });
        apiTopicsResource = injector.getInstance(ApiTopicsResource.class);
        apiTopicsResource.setResponse(new Response(new Request()));
        ContextTestFactory.initResource(apiTopicsResource);
    }

    @Test
    public void canCreateWithCorrectUser() {
        final Topic topic = TestFactories.topics().newTopic();
        when(topicService.createTopic(any(FeelhubLanguage.class), anyString(), any(TopicType.class))).thenReturn(topic);

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
        final Topic topic = TestFactories.topics().newTopic();
        when(topicService.createTopic(any(FeelhubLanguage.class), anyString(), any(TopicType.class))).thenReturn(topic);

        apiTopicsResource.createTopic(getGoodForm());

        verify(topicService).createTopic(CurrentUser.get().getLanguage(), getGoodForm().getFirstValue("description"), TopicType.valueOf(getGoodForm().getFirstValue("type")));
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
        form.add("type", TopicType.City.toString());

        apiTopicsResource.createTopic(form);

        assertThat(apiTopicsResource.getStatus()).isEqualTo(Status.CLIENT_ERROR_BAD_REQUEST);
    }

    @Test
    public void setLocationRefToNewTopic() {
        final Topic topic = TestFactories.topics().newTopic();
        when(topicService.createTopic(any(FeelhubLanguage.class), anyString(), any(TopicType.class))).thenReturn(topic);

        apiTopicsResource.createTopic(getGoodForm());

        assertThat(apiTopicsResource.getLocationRef().toString()).isEqualTo(new WebReferenceBuilder(apiTopicsResource.getContext()).buildUri("/topic/" + topic.getId()));
    }

    private Form getGoodForm() {
        final Form form = new Form();
        form.add("description", "description");
        form.add("type", TopicType.Audio.toString());
        return form;
    }

    private ApiTopicsResource apiTopicsResource;
    private User user;
    private TopicService topicService;
}
