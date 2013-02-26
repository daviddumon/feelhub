package com.feelhub.application.command.topic;

import com.feelhub.domain.eventbus.WithDomainEvent;
import com.feelhub.domain.topic.http.HttpTopic;
import com.feelhub.domain.topic.http.uri.ResolverResult;
import com.feelhub.domain.topic.http.uri.Uri;
import com.feelhub.domain.topic.http.uri.UriResolver;
import com.feelhub.domain.user.User;
import com.feelhub.repositories.Repositories;
import com.feelhub.repositories.fakeRepositories.WithFakeRepositories;
import com.feelhub.test.TestFactories;
import org.junit.Rule;
import org.junit.Test;

import java.util.UUID;

import static org.fest.assertions.Assertions.*;
import static org.mockito.Mockito.*;

public class CreateHttpTopicCommandTest {

    @Rule
    public WithFakeRepositories repositories = new WithFakeRepositories();

    @Rule
    public WithDomainEvent events = new WithDomainEvent();

    @Test
    public void canCreateAHttpTopic() {
        final User user = TestFactories.users().createFakeActiveUser("mail@mail.com");
        CreateHttpTopicCommand command = new CreateHttpTopicCommand("value", user.getId());
        command.resolver = mock(UriResolver.class);
        ResolverResult resolverResult = new ResolverResult();
        resolverResult.getPath().add(new Uri("http://class"));
        when(command.resolver.resolve(any(Uri.class))).thenReturn(resolverResult);

        UUID id = command.execute();

        HttpTopic httpTopic = Repositories.topics().getHttpTopic(id);
        assertThat(httpTopic).isNotNull();
        assertThat(httpTopic.getUserId()).isEqualTo(user.getId());
    }
}
