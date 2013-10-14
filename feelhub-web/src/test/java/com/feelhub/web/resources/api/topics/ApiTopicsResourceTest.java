package com.feelhub.web.resources.api.topics;

import com.feelhub.application.TopicService;
import com.feelhub.application.command.*;
import com.feelhub.application.command.topic.*;
import com.feelhub.domain.tag.Tag;
import com.feelhub.domain.topic.Topic;
import com.feelhub.domain.topic.real.*;
import com.feelhub.domain.user.User;
import com.feelhub.repositories.fakeRepositories.WithFakeRepositories;
import com.feelhub.test.TestFactories;
import com.feelhub.web.*;
import com.feelhub.web.authentification.*;
import com.feelhub.web.dto.*;
import com.feelhub.web.representation.ModelAndView;
import com.feelhub.web.resources.api.FeelhubApiException;
import com.google.common.collect.Lists;
import com.google.common.util.concurrent.Futures;
import org.junit.*;
import org.junit.rules.ExpectedException;
import org.mockito.ArgumentCaptor;
import org.restlet.*;
import org.restlet.data.*;

import java.util.*;

import static org.fest.assertions.Assertions.*;
import static org.mockito.Mockito.*;

public class ApiTopicsResourceTest {

    @Rule
    public WithFakeRepositories repositories = new WithFakeRepositories();

    @Rule
    public ExpectedException exception = ExpectedException.none();

    @Before
    public void before() {
        user = TestFactories.users().createFakeUser("mail@mail.com", "full name");
        CurrentUser.set(new WebUser(user, true));
        topicService = mock(TopicService.class);
        commandBus = mock(CommandBus.class);
        apiTopicsResource = new ApiTopicsResource(topicService, mock(TopicDataFactory.class), commandBus);
        apiTopicsResource.setResponse(new Response(new Request()));
        ContextTestFactory.initResource(apiTopicsResource);
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
        tag.addTopic(topic1, CurrentUser.get().getLanguage());
        final RealTopic topic2 = TestFactories.topics().newCompleteRealTopic();
        tag.addTopic(topic2, CurrentUser.get().getLanguage());
        final List<Topic> topics = Lists.newArrayList();
        topics.add(topic1);
        topics.add(topic2);
        when(topicService.getTopics(tag.getId(), CurrentUser.get().getLanguage())).thenReturn(topics);
        final Request request = new Request(Method.GET, "http://test.com?q=" + tag.getId());
        apiTopicsResource.init(Context.getCurrent(), request, new Response(request));

        final ModelAndView modelAndView = apiTopicsResource.getTopics();

        assertThat(modelAndView.getData("topicDatas")).isNotNull();
        final List<TopicData> topicDatas = modelAndView.getData("topicDatas");
        assertThat(topicDatas.size()).isEqualTo(2);
    }

    @Test
    public void errorIfNoQuery() {
        exception.expect(FeelhubApiException.class);
        final Request request = new Request(Method.GET, "http://test.com");
        apiTopicsResource.init(Context.getCurrent(), request, new Response(request));

        apiTopicsResource.getTopics();
    }

    @Test
    public void canGetTopicsWithGoodStatus() {
        final Request request = new Request(Method.GET, "http://test.com?q=test");
        apiTopicsResource.init(Context.getCurrent(), request, new Response(request));

        apiTopicsResource.getTopics();

        assertThat(apiTopicsResource.getStatus()).isEqualTo(Status.SUCCESS_OK);
    }

    @Test
    public void canCreateRealTopicWithCorrectUser() {
        when(commandBus.execute(any(Command.class))).thenReturn(Futures.immediateCheckedFuture(UUID.randomUUID()));

        apiTopicsResource.createTopic(getGoodFormWithRealTopic());

        final ArgumentCaptor<CreateRealTopicCommand> captor = ArgumentCaptor.forClass(CreateRealTopicCommand.class);
        verify(commandBus).execute(captor.capture());
        final CreateRealTopicCommand command = captor.getValue();
        assertThat(command.name).isEqualTo("name");
        assertThat(command.userID).isEqualTo(user.getId());
        assertThat(command.feelhubLanguage).isEqualTo(user.getLanguage());
        assertThat(command.type).isEqualTo(RealTopicType.Automobile);
        assertThat(apiTopicsResource.getStatus()).isEqualTo(Status.SUCCESS_CREATED);
    }

    @Test
    public void setLocationRefToNewRealTopic() {
        final UUID topicId = UUID.randomUUID();
        when(commandBus.execute(any(Command.class))).thenReturn(Futures.immediateCheckedFuture(topicId));

        apiTopicsResource.createTopic(getGoodFormWithRealTopic());

        assertThat(apiTopicsResource.getLocationRef().toString()).isEqualTo(new WebReferenceBuilder(apiTopicsResource.getContext()).buildUri("/topic/" + topicId));
    }

    @Test
    public void canCreateHttpTopicWithCorrectUser() {
        when(commandBus.execute(any(CreateHttpTopicCommand.class))).thenReturn(Futures.immediateCheckedFuture(UUID.randomUUID()));

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
    public void setLocationRefToNewHttpTopic() {
        final UUID id = UUID.randomUUID();
        when(commandBus.execute(any(CreateHttpTopicCommand.class))).thenReturn(Futures.immediateCheckedFuture(id));


        apiTopicsResource.createTopic(getGoodFormWithHttpTopic());

        assertThat(apiTopicsResource.getLocationRef().toString()).isEqualTo(new WebReferenceBuilder(apiTopicsResource.getContext()).buildUri("/topic/" + id));
    }

    @Test
    public void canCreateHttpTopic() {
        when(commandBus.execute(any(CreateHttpTopicCommand.class))).thenReturn(Futures.immediateCheckedFuture(UUID.randomUUID()));

        apiTopicsResource.createTopic(getGoodFormWithHttpTopic());

        final ArgumentCaptor<CreateHttpTopicCommand> captor = ArgumentCaptor.forClass(CreateHttpTopicCommand.class);
        verify(commandBus).execute(captor.capture());
        final CreateHttpTopicCommand value = captor.getValue();
        assertThat(value.userId).isEqualTo(user.getId());
        assertThat(value.value).isEqualTo("http://www.google.fr");
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
    private CommandBus commandBus;
    private TopicService topicService;
}
