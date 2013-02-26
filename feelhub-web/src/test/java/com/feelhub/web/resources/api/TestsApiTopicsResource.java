package com.feelhub.web.resources.api;

import com.feelhub.application.TopicService;
import com.feelhub.application.command.Command;
import com.feelhub.application.command.CommandBus;
import com.feelhub.application.command.topic.CreateHttpTopicCommand;
import com.feelhub.application.command.topic.CreateRealTopicCommand;
import com.feelhub.domain.tag.Tag;
import com.feelhub.domain.topic.Topic;
import com.feelhub.domain.topic.real.RealTopic;
import com.feelhub.domain.topic.real.RealTopicType;
import com.feelhub.domain.user.User;
import com.feelhub.repositories.fakeRepositories.WithFakeRepositories;
import com.feelhub.test.TestFactories;
import com.feelhub.web.ContextTestFactory;
import com.feelhub.web.WebReferenceBuilder;
import com.feelhub.web.authentification.AnonymousUser;
import com.feelhub.web.authentification.CurrentUser;
import com.feelhub.web.authentification.WebUser;
import com.feelhub.web.dto.TopicData;
import com.feelhub.web.dto.TopicDataFactory;
import com.feelhub.web.representation.ModelAndView;
import com.google.common.collect.Lists;
import com.google.common.util.concurrent.Futures;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.restlet.Context;
import org.restlet.Request;
import org.restlet.Response;
import org.restlet.data.Form;
import org.restlet.data.Method;
import org.restlet.data.Status;

import java.util.List;
import java.util.UUID;

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
        commandBus = mock(CommandBus.class);
        apiTopicsResource = new ApiTopicsResource(topicService, mock(TopicDataFactory.class), commandBus);
        apiTopicsResource.setResponse(new Response(new Request()));
        ContextTestFactory.initResource(apiTopicsResource);
    }

    @Test
    public void canCreateRealTopicWithCorrectUser() {
        when(commandBus.execute(any(Command.class))).thenReturn(Futures.immediateCheckedFuture(UUID.randomUUID()));

        apiTopicsResource.createTopic(getGoodFormWithRealTopic());

        ArgumentCaptor<CreateRealTopicCommand> captor = ArgumentCaptor.forClass(CreateRealTopicCommand.class);
        verify(commandBus).execute(captor.capture());
        CreateRealTopicCommand command = captor.getValue();
        assertThat(command.name).isEqualTo("name");
        assertThat(command.userID).isEqualTo(user.getId());
        assertThat(command.feelhubLanguage).isEqualTo(user.getLanguage());
        assertThat(command.type).isEqualTo(RealTopicType.Automobile);
        assertThat(apiTopicsResource.getStatus()).isEqualTo(Status.SUCCESS_CREATED);
    }

    @Test
    public void setLocationRefToNewRealTopic() {
        UUID topicId = UUID.randomUUID();
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
        UUID id = UUID.randomUUID();
        when(commandBus.execute(any(CreateHttpTopicCommand.class))).thenReturn(Futures.immediateCheckedFuture(id));


        apiTopicsResource.createTopic(getGoodFormWithHttpTopic());

        assertThat(apiTopicsResource.getLocationRef().toString()).isEqualTo(new WebReferenceBuilder(apiTopicsResource.getContext()).buildUri("/topic/" + id));
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
        final Request request = new Request(Method.GET, "http://test.com");
        apiTopicsResource.init(Context.getCurrent(), request, new Response(request));

        apiTopicsResource.getTopics();

        assertThat(apiTopicsResource.getStatus()).isEqualTo(Status.CLIENT_ERROR_BAD_REQUEST);
    }

    @Test
    public void canGetTopicsWithGoodStatus() {
        final Request request = new Request(Method.GET, "http://test.com?q=test");
        apiTopicsResource.init(Context.getCurrent(), request, new Response(request));

        apiTopicsResource.getTopics();

        assertThat(apiTopicsResource.getStatus()).isEqualTo(Status.SUCCESS_OK);
    }

    @Test
    public void canCreateHttpTopic() {
        when(commandBus.execute(any(CreateHttpTopicCommand.class))).thenReturn(Futures.immediateCheckedFuture(UUID.randomUUID()));

        apiTopicsResource.createTopic(getGoodFormWithHttpTopic());

        ArgumentCaptor<CreateHttpTopicCommand> captor = ArgumentCaptor.forClass(CreateHttpTopicCommand.class);
        verify(commandBus).execute(captor.capture());
        CreateHttpTopicCommand value = captor.getValue();
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
    private TopicService topicService;
    private CommandBus commandBus;
}
