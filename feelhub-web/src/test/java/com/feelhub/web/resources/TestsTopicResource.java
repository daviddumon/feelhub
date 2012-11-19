package com.feelhub.web.resources;

import com.feelhub.domain.meta.Illustration;
import com.feelhub.domain.thesaurus.FeelhubLanguage;
import com.feelhub.domain.topic.*;
import com.feelhub.domain.user.User;
import com.feelhub.repositories.fakeRepositories.WithFakeRepositories;
import com.feelhub.test.TestFactories;
import com.feelhub.web.*;
import com.feelhub.web.authentification.*;
import com.feelhub.web.dto.TopicData;
import com.feelhub.web.guice.GuiceTestModule;
import com.feelhub.web.representation.ModelAndView;
import com.google.inject.*;
import org.junit.*;
import org.junit.rules.ExpectedException;
import org.restlet.data.Status;

import java.util.UUID;

import static org.fest.assertions.Assertions.*;

public class TestsTopicResource {

    @Rule
    public WithFakeRepositories repositories = new WithFakeRepositories();

    @Rule
    public WebApplicationTester restlet = new WebApplicationTester();

    @Rule
    public ExpectedException exception = ExpectedException.none();

    @Before
    public void before() {
        user = TestFactories.users().createFakeUser("mail@mail.com", "full name");
        CurrentUser.set(new WebUser(user, true));
        injector = Guice.createInjector(new GuiceTestModule());
        topicResource = injector.getInstance(TopicResource.class);
        ContextTestFactory.initResource(topicResource);
    }

    @Test
    public void isMapped() {
        final Topic topic = TestFactories.topics().newTopic();
        final ClientResource clientResource = restlet.newClientResource("/topic/" + topic.getId());

        clientResource.get();

        assertThat(clientResource.getStatus()).isEqualTo(Status.SUCCESS_OK);
    }

    @Test
    public void hasTopicDataInDataModel() {
        final Topic topic = TestFactories.topics().newTopic();
        topicResource.getRequest().getAttributes().put("id", topic.getId());

        final ModelAndView modelAndView = topicResource.getTopic();

        assertThat(modelAndView.getData("topicData")).isNotNull();
    }

    @Test
    public void lookUpTopic() {
        final Topic topic = TestFactories.topics().newTopic();
        topicResource.getRequest().getAttributes().put("id", topic.getId());

        final ModelAndView modelAndView = topicResource.getTopic();

        final TopicData topicData = modelAndView.getData("topicData");
        assertThat(topicData.getId()).isEqualTo(topic.getId().toString());
        assertThat(topicData.getDescription()).isEqualTo(topic.getDescription(FeelhubLanguage.reference()));
        assertThat(topicData.getType()).isEqualTo(topic.getType());
        assertThat(topicData.getSubTypes()).isEqualTo(topic.getSubTypes());
        assertThat(topicData.getUrls()).isEqualTo(topic.getUrls());
    }

    @Test
    public void setCorrectStatusOnSuccess() {
        final Topic topic = TestFactories.topics().newTopic();
        topicResource.getRequest().getAttributes().put("id", topic.getId());

        topicResource.getTopic();

        assertThat(topicResource.getStatus()).isEqualTo(Status.SUCCESS_OK);
    }

    @Test
    public void canThrowTopicNotFound() {
        exception.expect(TopicNotFound.class);
        topicResource.getRequest().getAttributes().put("id", UUID.randomUUID());

        topicResource.getTopic();
    }

    @Test
    public void topicDataWithGoodValuesForExistingTopicAndIllustration() {
        final Topic topic = TestFactories.topics().newTopic();
        final Illustration illustration = TestFactories.illustrations().newIllustration(topic.getId());
        topicResource.getRequest().getAttributes().put("id", topic.getId());

        final ModelAndView modelAndView = topicResource.getTopic();

        final TopicData topicData = modelAndView.getData("topicData");
        assertThat(topicData.getIllustrationLink()).isEqualTo(illustration.getLink());
    }

    private User user;
    private Injector injector;
    private TopicResource topicResource;
}
