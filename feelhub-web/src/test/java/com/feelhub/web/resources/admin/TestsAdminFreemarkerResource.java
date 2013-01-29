package com.feelhub.web.resources.admin;

import com.feelhub.web.*;
import org.hamcrest.MatcherAssert;
import org.junit.*;
import org.restlet.data.*;

import static org.hamcrest.Matchers.*;

public class TestsAdminFreemarkerResource {

    @Rule
    public WebApplicationTester restlet = new WebApplicationTester();

    @Test
    public void isMappedWithSecurity() {
        final ClientResource help = restlet.newClientResource("/admin/ftl/error", challengeResponse());

        help.get();

        MatcherAssert.assertThat(help.getStatus(), is(Status.SUCCESS_OK));
    }

    private ChallengeResponse challengeResponse() {
        return new ChallengeResponse(ChallengeScheme.HTTP_BASIC, FeelhubRouter.ADMIN_USER,
                FeelhubRouter.ADMIN_PASSWORD);
    }
}
