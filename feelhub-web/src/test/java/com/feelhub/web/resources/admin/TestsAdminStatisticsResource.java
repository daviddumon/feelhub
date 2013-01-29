package com.feelhub.web.resources.admin;

import com.feelhub.domain.admin.AlchemyStatistic;
import com.feelhub.repositories.Repositories;
import com.feelhub.repositories.fakeRepositories.WithFakeRepositories;
import com.feelhub.web.ClientResource;
import com.feelhub.web.FeelhubRouter;
import com.feelhub.web.WebApplicationTester;
import com.feelhub.web.representation.ModelAndView;
import org.hamcrest.MatcherAssert;
import org.junit.Rule;
import org.junit.Test;
import org.restlet.Context;
import org.restlet.Request;
import org.restlet.Response;
import org.restlet.data.ChallengeResponse;
import org.restlet.data.ChallengeScheme;
import org.restlet.data.Method;
import org.restlet.data.Status;

import java.util.List;

import static org.fest.assertions.Assertions.assertThat;
import static org.hamcrest.Matchers.is;

public class TestsAdminStatisticsResource {

    @Rule
    public WithFakeRepositories repositories = new WithFakeRepositories();

    @Rule
    public WebApplicationTester restlet = new WebApplicationTester();

    @Test
    public void isMappedWithSecurity() {
        final ClientResource help = restlet.newClientResource("/admin/statistics", challengeResponse());

        help.get();

        MatcherAssert.assertThat(help.getStatus(), is(Status.SUCCESS_OK));
    }

    @Test
    public void hasStatisticsInData() {
        Repositories.alchemyStatistics().add(new AlchemyStatistic("022012"));

        final ModelAndView modelAndView = new AdminStatisticsResource().represent();

        assertThat(modelAndView.getData("alchemyStatistics")).isNotNull();
        List<AlchemyStatistic> stats = modelAndView.getData("alchemyStatistics");
        assertThat(stats).hasSize(1);
    }

    private ChallengeResponse challengeResponse() {
        return new ChallengeResponse(ChallengeScheme.HTTP_BASIC, FeelhubRouter.ADMIN_USER,
                FeelhubRouter.ADMIN_PASSWORD);
    }
}
