package com.feelhub.web.resources.admin;

import com.feelhub.domain.admin.*;
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
        final ClientResource resource = restlet.newClientResource("/admin/statistics", challengeResponse());

        resource.get();

        MatcherAssert.assertThat(resource.getStatus(), is(Status.SUCCESS_OK));
    }

    @Test
    public void hasStatisticsInData() {
        Repositories.adminStatistics().add(new AdminStatistic("012012", Api.Alchemy));
        Repositories.adminStatistics().add(new AdminStatistic("022012", Api.BingSearch));
        Repositories.adminStatistics().add(new AdminStatistic("032012", Api.BingSearch));

        final ModelAndView modelAndView = new AdminStatisticsResource().represent();

        assertThat(modelAndView.getData("statistics")).isNotNull();
        final List<AdminStatisticsByApi> stats = modelAndView.getData("statistics");
        assertThat(stats).hasSize(Api.values().length);
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
