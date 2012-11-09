package com.feelhub.web.migration;

import com.feelhub.web.*;
import org.junit.*;
import org.restlet.Context;
import org.restlet.data.Status;

import static org.hamcrest.MatcherAssert.*;
import static org.hamcrest.Matchers.*;

public class TestsMigrationResource {

    @Rule
    public WebApplicationTester restlet = new WebApplicationTester();

    @Test
    @Ignore("refacto migration system")
    public void onlyMigrationResourceIsMapped() {
        final FeelhubApplication application = restlet.getApplication();
        final Context context = application.getContext();
        context.getAttributes().replace("com.feelhub.ready", false);
        final ClientResource anyResource = restlet.newClientResource("/ahaha/yeah");

        anyResource.get();

        assertThat(anyResource.getStatus(), is(Status.SUCCESS_OK));
    }
}