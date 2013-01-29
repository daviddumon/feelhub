package com.feelhub.web.resources.admin;

import com.feelhub.domain.admin.AlchemyStatistic;
import com.feelhub.repositories.Repositories;
import com.feelhub.repositories.fakeRepositories.WithFakeRepositories;
import com.feelhub.web.*;
import com.feelhub.web.representation.ModelAndView;
import org.hamcrest.MatcherAssert;
import org.junit.*;
import org.restlet.data.*;

import java.util.List;

import static org.fest.assertions.Assertions.*;
import static org.hamcrest.Matchers.*;

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
