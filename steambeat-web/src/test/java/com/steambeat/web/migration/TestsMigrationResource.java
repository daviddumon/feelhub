package com.steambeat.web.migration;

import com.steambeat.web.*;
import org.junit.*;
import org.restlet.data.Status;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

public class TestsMigrationResource {

    @Rule
    public WebApplicationTester restlet = new WebApplicationTester();
    
    @Test
    @Ignore
    public void onlyMigrationResourceIsMapped() {
        final ClientResource anyResource = restlet.newClientResource("/ahaha/yeah");

        anyResource.get();

        assertThat(anyResource.getStatus(), is(Status.SUCCESS_OK));
    }
}
