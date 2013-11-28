package com.feelhub.web.resources.admin;

import com.feelhub.application.command.Command;
import com.feelhub.application.command.CommandBus;
import com.feelhub.application.command.topic.UpdateThumbnailTopicCommand;
import com.feelhub.domain.topic.Topic;
import com.feelhub.repositories.fakeRepositories.WithFakeRepositories;
import com.feelhub.test.TestFactories;
import com.feelhub.web.ClientResource;
import com.feelhub.web.ContextTestFactory;
import com.feelhub.web.FeelhubRouter;
import com.feelhub.web.WebApplicationTester;
import com.google.common.util.concurrent.Futures;
import org.hamcrest.MatcherAssert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.restlet.data.*;

import java.util.UUID;

import static org.fest.assertions.Assertions.assertThat;
import static org.hamcrest.Matchers.is;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class AdminTopicThumbnailResourceTest {

    @Rule
    public WithFakeRepositories repositories = new WithFakeRepositories();

    @Rule
    public WebApplicationTester restlet = new WebApplicationTester();

    @Before
    public void before() {
        commandBus = mock(CommandBus.class);
    }

    @Test
    public void isMappedWithSecurity() {
        Topic topic = TestFactories.topics().newCompleteHttpTopic();
        ClientResource resource = restlet.newClientResource("/admin/topics/" + topic.getId() + "/thumbnail", challengeResponse());

        resource.put(new Form());

        MatcherAssert.assertThat(resource.getStatus(), is(Status.SUCCESS_OK));
    }

    @Test
    public void canLaunchThumbnailUpdate() {
        String topicId = "b25ca69d-be2c-44b4-b2a1-ca4b965308d1";
        when(commandBus.execute(any(Command.class))).thenReturn(Futures.immediateCheckedFuture(UUID.randomUUID()));

        createResourceWith(topicId).put(new Form());

        ArgumentCaptor<UpdateThumbnailTopicCommand> captor = ArgumentCaptor.forClass(UpdateThumbnailTopicCommand.class);
        verify(commandBus).execute(captor.capture());
        UpdateThumbnailTopicCommand command = captor.getValue();
        assertThat(command.topicId).isEqualTo(UUID.fromString(topicId));
    }

    private AdminTopicThumbnailResource createResourceWith(String topicId) {
        AdminTopicThumbnailResource resource = new AdminTopicThumbnailResource(commandBus);
        ContextTestFactory.initResource(resource);
        resource.getRequest().getAttributes().put("topicId", topicId);
        return resource;
    }

    private ChallengeResponse challengeResponse() {
        return new ChallengeResponse(ChallengeScheme.HTTP_BASIC, FeelhubRouter.ADMIN_USER,
                FeelhubRouter.ADMIN_PASSWORD);
    }

    private CommandBus commandBus;
}
