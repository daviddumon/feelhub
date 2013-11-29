package com.feelhub.web.resources.admin;

import com.feelhub.domain.admin.AdminStatistic;
import com.feelhub.domain.admin.Api;
import com.feelhub.domain.topic.Topic;
import com.feelhub.domain.topic.http.HttpTopic;
import com.feelhub.domain.topic.real.RealTopic;
import com.feelhub.repositories.Repositories;
import com.feelhub.repositories.fakeRepositories.WithFakeRepositories;
import com.feelhub.test.TestFactories;
import com.feelhub.web.ClientResource;
import com.feelhub.web.FeelhubRouter;
import com.feelhub.web.WebApplicationTester;
import com.feelhub.web.dto.TopicData;
import com.feelhub.web.dto.TopicDataFactory;
import com.feelhub.web.representation.ModelAndView;
import org.hamcrest.MatcherAssert;
import org.junit.Rule;
import org.junit.Test;
import org.restlet.data.ChallengeResponse;
import org.restlet.data.ChallengeScheme;
import org.restlet.data.Status;

import java.util.List;

import static org.fest.assertions.Assertions.assertThat;
import static org.hamcrest.Matchers.is;

public class AdminTopicsResourceTest {

    @Rule
    public WithFakeRepositories repositories = new WithFakeRepositories();

    @Rule
    public WebApplicationTester restlet = new WebApplicationTester();

    @Test
    public void isMappedWithSecurity() {
        final ClientResource resource = restlet.newClientResource("/admin/topics", challengeResponse());

        resource.get();

        MatcherAssert.assertThat(resource.getStatus(), is(Status.SUCCESS_OK));
    }

    @Test
    public void hasTopicsWithoutThumbnail() {
        TestFactories.topics().newCompleteHttpTopic();
        Topic topicWithoutThumbnail = TestFactories.topics().newCompleteRealTopic();
        topicWithoutThumbnail.setThumbnail(null);

        final ModelAndView modelAndView = new AdminTopicsResource(new TopicDataFactory()).represent();

        assertThat(modelAndView.getData("topics")).isNotNull();
        final List<TopicData> topics = modelAndView.getData("topics");
        assertThat(topics).hasSize(1);
        assertThat(topics.get(0).getId()).isEqualTo(topicWithoutThumbnail.getId().toString());
    }

    @Test
    public void hasOnlyHttpAndRealTopicsWithoutThumbnail() {
        HttpTopic httpTopic = TestFactories.topics().newCompleteHttpTopic();
        httpTopic.setThumbnail(null);
        RealTopic realTopic = TestFactories.topics().newCompleteRealTopic();
        realTopic.setThumbnail(null);
        Topic topicWithoutThumbnail = TestFactories.topics().newWorldTopic();
        topicWithoutThumbnail.setThumbnail(null);

        final ModelAndView modelAndView = new AdminTopicsResource(new TopicDataFactory()).represent();

        assertThat(modelAndView.getData("topics")).isNotNull();
        final List<TopicData> topics = modelAndView.getData("topics");
        assertThat(topics).hasSize(2);
    }

    private ChallengeResponse challengeResponse() {
        return new ChallengeResponse(ChallengeScheme.HTTP_BASIC, FeelhubRouter.ADMIN_USER,
                FeelhubRouter.ADMIN_PASSWORD);
    }
}
