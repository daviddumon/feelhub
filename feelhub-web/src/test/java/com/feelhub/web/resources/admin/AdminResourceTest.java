package com.feelhub.web.resources.admin;

import com.feelhub.web.ClientResource;
import com.feelhub.web.FeelhubRouter;
import com.feelhub.web.WebApplicationTester;
import org.hamcrest.MatcherAssert;
import org.junit.Rule;
import org.junit.Test;
import org.restlet.data.ChallengeResponse;
import org.restlet.data.ChallengeScheme;
import org.restlet.data.Status;

import static org.hamcrest.Matchers.is;

public class AdminResourceTest {


    @Rule
    public WebApplicationTester restlet = new WebApplicationTester();

    @Test
    public void isMappedWithSecurity() {
        final ClientResource resource = restlet.newClientResource("/admin", challengeResponse());

        resource.get();

        MatcherAssert.assertThat(resource.getStatus(), is(Status.SUCCESS_OK));
    }

    private ChallengeResponse challengeResponse() {
        return new ChallengeResponse(ChallengeScheme.HTTP_BASIC, FeelhubRouter.ADMIN_USER,
                FeelhubRouter.ADMIN_PASSWORD);
    }
}
