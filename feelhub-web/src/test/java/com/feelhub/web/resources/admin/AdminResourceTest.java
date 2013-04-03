package com.feelhub.web.resources.admin;

import com.feelhub.web.*;
import org.hamcrest.MatcherAssert;
import org.junit.*;
import org.restlet.data.*;

import static org.hamcrest.Matchers.*;

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
