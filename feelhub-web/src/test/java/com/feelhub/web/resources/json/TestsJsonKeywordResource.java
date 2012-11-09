package com.feelhub.web.resources.json;

import com.feelhub.domain.keyword.Keyword;
import com.feelhub.repositories.fakeRepositories.WithFakeRepositories;
import com.feelhub.test.TestFactories;
import com.feelhub.web.*;
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
    public void canGetTopicIdOfKeyword() {
        final Keyword keyword = TestFactories.keywords().newKeyword();
        final ClientResource clientResource = restlet.newClientResource("/json/keyword?keywordValue=" + keyword.getValue() + "&languageCode=" + keyword.getLanguageCode());

        clientResource.get();

        assertThat(clientResource.getStatus(), is(Status.SUCCESS_OK));
    }
}
