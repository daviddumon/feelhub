package com.feelhub.web.resources;

import com.feelhub.domain.topic.*;
import com.feelhub.domain.topic.real.RealTopic;
import com.feelhub.domain.user.User;
import com.feelhub.repositories.SessionProvider;
import com.feelhub.repositories.fakeRepositories.WithFakeRepositories;
import com.feelhub.test.TestFactories;
import com.feelhub.web.*;
import com.feelhub.web.api.ApiFeelingSearch;
import com.feelhub.web.authentification.*;
import com.feelhub.web.dto.*;
import com.feelhub.web.guice.DummySessionProvider;
import com.feelhub.web.representation.ModelAndView;
import com.feelhub.web.search.RelatedSearch;
import com.feelhub.web.search.fake.FakeRelatedSearch;
import com.google.common.collect.Lists;
import com.google.inject.*;
import org.junit.*;
import org.junit.rules.ExpectedException;
import org.restlet.data.*;

import java.util.*;

import static org.fest.assertions.Assertions.*;
import static org.mockito.Mockito.*;

public class TopicResourceTest {

    @Rule
    public WithFakeRepositories repositories = new WithFakeRepositories();

    @Rule
    public WebApplicationTester restlet = new WebApplicationTester();

    @Rule
    public ExpectedException exception = ExpectedException.none();

    @Before
    public void before() {
        final User user = TestFactories.users().createFakeUser("mail@mail.com", "full name");
        CurrentUser.set(new WebUser(user, true));
        apiFeelingSearch = mock(ApiFeelingSearch.class);
        final Injector injector = Guice.createInjector(new AbstractModule() {
            @Override
            protected void configure() {
                bind(SessionProvider.class).to(DummySessionProvider.class);
                bind(ApiFeelingSearch.class).toInstance(apiFeelingSearch);
                bind(RelatedSearch.class).to(FakeRelatedSearch.class);
            }
        });
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
        topicResource.getRequest().getAttributes().put("topicId", realTopic.getId());

        final ModelAndView modelAndView = topicResource.getTopic();

        assertThat(modelAndView.getData("topicData")).isNotNull();
    }

    @Test
    public void lookUpTopic() {
        final RealTopic realTopic = TestFactories.topics().newCompleteRealTopic();
        topicResource.getRequest().getAttributes().put("topicId", realTopic.getId());

        final ModelAndView modelAndView = topicResource.getTopic();

        final TopicData topicData = modelAndView.getData("topicData");
        assertThat(topicData.getId()).isEqualTo(realTopic.getId().toString());
    }

    @Test
    public void setCorrectStatusOnSuccess() {
        final RealTopic realTopic = TestFactories.topics().newCompleteRealTopic();
        topicResource.getRequest().getAttributes().put("topicId", realTopic.getId());

        topicResource.getTopic();

        assertThat(topicResource.getStatus()).isEqualTo(Status.SUCCESS_OK);
    }

    @Test
    public void onTopicNotFoundSetErrorCodeToGone() {
        topicResource.getRequest().getAttributes().put("topicId", UUID.randomUUID());

        topicResource.getTopic();

        assertThat(topicResource.getStatus()).isEqualTo(Status.CLIENT_ERROR_GONE);
    }

    @Test
    public void topicDataWithGoodValuesForExistingTopicAndIllustration() {
        final RealTopic realTopic = TestFactories.topics().newCompleteRealTopic();
        final String illustration = "illustration";
        realTopic.setThumbnail(illustration);
        topicResource.getRequest().getAttributes().put("topicId", realTopic.getId());

        final ModelAndView modelAndView = topicResource.getTopic();

        final TopicData topicData = modelAndView.getData("topicData");
        assertThat(topicData.getId()).isEqualTo(realTopic.getCurrentId().toString());
        assertThat(topicData.getThumbnail()).isEqualTo(illustration);
    }

    @Test
    public void redirectIfCurrentIdIncorrect() {
        final RealTopic realTopic = TestFactories.topics().newCompleteRealTopic();
        final RealTopic newTopic = TestFactories.topics().newCompleteRealTopic();
        realTopic.changeCurrentId(newTopic.getId());
        final ClientResource clientResource = restlet.newClientResource("/topic/" + realTopic.getId());

        clientResource.get();

        assertThat(clientResource.getStatus()).isEqualTo(Status.REDIRECTION_PERMANENT);
    }

    @Test
    public void hasLocalesInModelData() {
        final RealTopic realTopic = TestFactories.topics().newCompleteRealTopic();
        topicResource.getRequest().getAttributes().put("topicId", realTopic.getId());

        final ModelAndView modelAndView = topicResource.getTopic();

        assertThat(modelAndView.getData("locales")).isNotNull();
    }

    @Test
    public void fetchInitialFeelingsForTopic() {
        final RealTopic realTopic = TestFactories.topics().newCompleteRealTopic();
        topicResource.getRequest().getAttributes().put("topicId", realTopic.getId());

        final ModelAndView modelAndView = topicResource.getTopic();

        verify(apiFeelingSearch).doSearchForATopic(any(Topic.class), any(Form.class));
    }

    @Test
    public void hasFeelingDatasInModel() {
        final RealTopic realTopic = TestFactories.topics().newCompleteRealTopic();
        topicResource.getRequest().getAttributes().put("topicId", realTopic.getId());

        final ModelAndView modelAndView = topicResource.getTopic();

        assertThat(modelAndView.getData("feelingDatas")).isNotNull();
    }

    @Test
    public void feelingDatasIsFeelingsForTopic() {
        final List<FeelingData> initialDatas = Lists.newArrayList();
        initialDatas.add(new FeelingData.Builder().build());
        initialDatas.add(new FeelingData.Builder().build());
        when(apiFeelingSearch.doSearchForATopic(any(Topic.class), any(Form.class))).thenReturn(initialDatas);
        final RealTopic realTopic = TestFactories.topics().newCompleteRealTopic();
        topicResource.getRequest().getAttributes().put("topicId", realTopic.getId());

        final ModelAndView modelAndView = topicResource.getTopic();

        final List<FeelingData> result = modelAndView.getData("feelingDatas");
        assertThat(result.size()).isEqualTo(2);
    }

    @Test
    public void returnNormalFtl() {
        final RealTopic realTopic = TestFactories.topics().newCompleteRealTopic();
        topicResource.getRequest().getAttributes().put("topicId", realTopic.getId());

        final ModelAndView modelAndView = topicResource.getTopic();

        assertThat(modelAndView.getTemplate()).isEqualTo("topic.ftl");
    }

    private TopicResource topicResource;
    private ApiFeelingSearch apiFeelingSearch;
}
