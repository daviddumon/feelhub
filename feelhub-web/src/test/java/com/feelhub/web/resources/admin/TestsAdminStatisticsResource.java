package com.feelhub.web.resources.admin;

import com.feelhub.domain.admin.AdminStatistic;
import com.feelhub.domain.admin.Api;
import com.feelhub.repositories.Repositories;
import com.feelhub.repositories.fakeRepositories.WithFakeRepositories;
import com.feelhub.web.ClientResource;
import com.feelhub.web.FeelhubRouter;
import com.feelhub.web.WebApplicationTester;
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
        Repositories.adminStatistics().add(new AdminStatistic("012012", Api.Alchemy));
        Repositories.adminStatistics().add(new AdminStatistic("022012", Api.BingSearch));
        Repositories.adminStatistics().add(new AdminStatistic("032012", Api.BingSearch));

        final ModelAndView modelAndView = new AdminStatisticsResource().represent();

        assertThat(modelAndView.getData("statistics")).isNotNull();
        List<AdminStatisticsByApi> stats = modelAndView.getData("statistics");
        assertThat(stats).hasSize(2);
        assertThat(stats.get(0).getApi()).isEqualTo(Api.Alchemy);
        assertThat(stats.get(0).getStatistics()).hasSize(1);
        assertThat(stats.get(0).getStatistics().get(0).getMonth()).isEqualTo("012012");
        assertThat(stats.get(1).getApi()).isEqualTo(Api.BingSearch);
        assertThat(stats.get(1).getStatistics()).hasSize(2);
    }

    private ChallengeResponse challengeResponse() {
        return new ChallengeResponse(ChallengeScheme.HTTP_BASIC, FeelhubRouter.ADMIN_USER,
                FeelhubRouter.ADMIN_PASSWORD);
    }
}
