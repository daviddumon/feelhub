package com.steambeat.web.resources;

import com.steambeat.domain.subject.webpage.WebPage;
import com.steambeat.test.fakeRepositories.WithFakeRepositories;
import com.steambeat.test.testFactories.TestFactories;
import com.steambeat.web.*;
import org.json.*;
import org.junit.*;
import org.junit.Test;
import org.restlet.data.Status;

import java.io.IOException;

import static org.hamcrest.MatcherAssert.*;
import static org.hamcrest.Matchers.*;

public class TestsSubjectsResource {

    @Rule
    public WebApplicationTester restlet = new WebApplicationTester();

    @Rule
    public WithFakeRepositories repositories = new WithFakeRepositories();

    @Test
    public void canSearchWebpage() throws IOException, JSONException {
        final WebPage webPage = TestFactories.webPages().newWebPage();
        final ClientResource subjectResource = restlet.newClientResource("/subjects?q=" + webPage.getRealUri().toString());

        final SteambeatTemplateRepresentation representation = (SteambeatTemplateRepresentation) subjectResource.get();

        assertThat(subjectResource.getStatus(), is(Status.SUCCESS_OK));
        final JSONObject jsonObject = new JSONObject(representation.getText());
        assertThat(jsonObject, notNullValue());
    }

    @Test
    public void return404OnUnfoundSubject() {
        final ClientResource subjectResource = restlet.newClientResource("/subjects?q=unfoundsubject");

        final SteambeatTemplateRepresentation representation = (SteambeatTemplateRepresentation) subjectResource.get();

        assertThat(subjectResource.getStatus(), is(Status.CLIENT_ERROR_NOT_FOUND));
    }
}
