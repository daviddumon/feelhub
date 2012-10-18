package com.steambeat.web.resources.json;

import com.steambeat.domain.keyword.Keyword;
import com.steambeat.repositories.fakeRepositories.WithFakeRepositories;
import com.steambeat.test.TestFactories;
import com.steambeat.web.*;
import com.steambeat.web.representation.SteambeatTemplateRepresentation;
import org.junit.*;
import org.restlet.data.Status;

import static org.hamcrest.Matchers.*;
import static org.junit.Assert.*;

public class TestsJsonKeywordResource {

    @Rule
    public WebApplicationTester restlet = new WebApplicationTester();

    @Rule
    public WithFakeRepositories repositories = new WithFakeRepositories();

    @Test
    public void canGetReferenceIdOfKeyword() {
        final Keyword keyword = TestFactories.keywords().newKeyword();
        final ClientResource clientResource = restlet.newClientResource("/json/keyword?keywordValue=" + keyword.getValue() + "&languageCode=" + keyword.getLanguageCode());

        final SteambeatTemplateRepresentation representation = (SteambeatTemplateRepresentation) clientResource.get();

        assertThat(clientResource.getStatus(), is(Status.SUCCESS_OK));
        assertThat(representation, notNullValue());
    }
}
