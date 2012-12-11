package com.feelhub.web.resources;

import com.feelhub.domain.meta.Illustration;
import com.feelhub.domain.thesaurus.FeelhubLanguage;
import com.feelhub.domain.topic.*;
import com.feelhub.domain.topic.usable.real.RealTopic;
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
        final RealTopic realTopic = TestFactories.topics().newCompleteRealTopic();
        final ClientResource clientResource = restlet.newClientResource("/topic/" + realTopic.getId());

        clientResource.get();

        assertThat(clientResource.getStatus()).isEqualTo(Status.SUCCESS_OK);
    }

    @Test
    public void hasTopicDataInDataModel() {
        final RealTopic realTopic = TestFactories.topics().newCompleteRealTopic();
        topicResource.getRequest().getAttributes().put("id", realTopic.getId());

        final ModelAndView modelAndView = topicResource.getTopic();

        assertThat(modelAndView.getData("topicData")).isNotNull();
    }

    @Test
    @Ignore
    public void lookUpTopic() {
        final RealTopic realTopic = TestFactories.topics().newCompleteRealTopic();
        topicResource.getRequest().getAttributes().put("id", realTopic.getId());

        final ModelAndView modelAndView = topicResource.getTopic();

        final TopicData topicData = modelAndView.getData("topicData");
        assertThat(topicData.getId()).isEqualTo(realTopic.getId().toString());
        assertThat(topicData.getName()).isEqualTo(realTopic.getDescription(FeelhubLanguage.reference()));
        assertThat(topicData.getType()).isEqualTo(realTopic.getType().toString());
        assertThat(topicData.getSubTypes()).isEqualTo(realTopic.getSubTypes());
        //assertThat(topicData.getUrls()).isEqualTo(realTopic.getUrls());
    }

    @Test
    public void setCorrectStatusOnSuccess() {
        final RealTopic realTopic = TestFactories.topics().newCompleteRealTopic();
        topicResource.getRequest().getAttributes().put("id", realTopic.getId());

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
        final RealTopic realTopic = TestFactories.topics().newCompleteRealTopic();
        final Illustration illustration = TestFactories.illustrations().newIllustration(realTopic.getId());
        topicResource.getRequest().getAttributes().put("id", realTopic.getId());

        final ModelAndView modelAndView = topicResource.getTopic();

        final TopicData topicData = modelAndView.getData("topicData");
        assertThat(topicData.getIllustrationLink()).isEqualTo(illustration.getLink());
    }

    private User user;
    private Injector injector;
    private TopicResource topicResource;
}
