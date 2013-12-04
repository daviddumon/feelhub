package com.feelhub.application.command.topic;

import com.feelhub.domain.eventbus.WithDomainEvent;
import com.feelhub.domain.topic.Topic;
import com.feelhub.domain.topic.http.HttpTopic;
import com.feelhub.domain.topic.http.uri.ResolverResult;
import com.feelhub.domain.topic.http.uri.Uri;
import com.feelhub.domain.topic.http.uri.UriResolver;
import com.feelhub.domain.topic.real.RealTopic;
import com.feelhub.domain.topic.real.RealTopicType;
import com.feelhub.domain.user.User;
import com.feelhub.repositories.Repositories;
import com.feelhub.repositories.fakeRepositories.WithFakeRepositories;
import com.feelhub.test.TestFactories;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.restlet.data.MediaType;

import java.util.UUID;

import static org.fest.assertions.Assertions.*;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.*;

public class SearchOrCreateTopicCommandTest {

    @Rule
    public WithFakeRepositories repositories = new WithFakeRepositories();

    @Rule
    public WithDomainEvent events = new WithDomainEvent();

    @Before
    public void setUp() throws Exception {
        user = TestFactories.users().createActiveUser("jb@jb.com");
    }

    @Test
    public void canCreateAnHttpTopic() {
        SearchOrCreateTopicCommand command = new SearchOrCreateTopicCommand("http://test.com", user.getId());
        command.resolver = aResolver("http://class");

        UUID topicId = command.execute();

        Topic topic = Repositories.topics().get(topicId);
        assertThat(topic).isNotNull();
        assertThat(topic).isInstanceOf(HttpTopic.class);
    }

    @Test
    public void canCreateRealTopic() {
        SearchOrCreateTopicCommand command = new SearchOrCreateTopicCommand("Nicolas Sarkozy", user.getId());

        UUID topicId = command.execute();

        Topic topic = Repositories.topics().get(topicId);
        assertThat(topic).isNotNull();
        assertThat(topic).isInstanceOf(RealTopic.class);
        RealTopic realTopic = (RealTopic) topic;
        assertThat(realTopic.getName(user.getLanguage())).isEqualTo("Nicolas Sarkozy");
        assertThat(realTopic.getType()).isEqualTo(RealTopicType.Other);
    }

    @Test
    public void doNotCreateAnHttpTopicTwice() {
        HttpTopic httpTopic = givenAHttpTopic();
        SearchOrCreateTopicCommand command = new SearchOrCreateTopicCommand(httpTopic.getUris().get(0).toString(), user.getId());
        command.resolver = aResolver(httpTopic.getUris().get(0).toString());

        UUID idTopic = command.execute();

        assertThat(idTopic).isEqualTo(httpTopic.getId());
        assertThat(Repositories.topics().getAll()).hasSize(1);
    }

    private HttpTopic givenAHttpTopic() {
        HttpTopic topic = TestFactories.topics().newCompleteHttpTopic();
        TestFactories.tags().newTag(topic.getUris().get(0).toString(), topic);
        return topic;

    }

    private UriResolver aResolver(String value) {
        UriResolver resolver = mock(UriResolver.class);
        final ResolverResult resolverResult = new ResolverResult();
        resolverResult.setMediaType(MediaType.TEXT_HTML);
        resolverResult.getPath().add(new Uri(value));
        when(resolver.resolve(any(Uri.class))).thenReturn(resolverResult);
        return resolver;
    }

    private User user;
}
