package com.feelhub.web.resources.api;

import com.feelhub.domain.keyword.word.Word;
import com.feelhub.repositories.fakeRepositories.WithFakeRepositories;
import com.feelhub.test.TestFactories;
import com.feelhub.web.*;
import org.junit.*;
import org.restlet.data.Status;

import static org.hamcrest.Matchers.*;
import static org.junit.Assert.*;

public class TestsApiWordResource {

    @Rule
    public WebApplicationTester restlet = new WebApplicationTester();

    @Rule
    public WithFakeRepositories repositories = new WithFakeRepositories();

    @Test
    public void canGetTopicIdOfKeyword() {
        final Word word = TestFactories.keywords().newWord();
        final ClientResource clientResource = restlet.newClientResource("/api/word?value=" + word.getValue() + "&languageCode=" + word.getLanguageCode());

        clientResource.get();

        assertThat(clientResource.getStatus(), is(Status.SUCCESS_OK));
    }
}
