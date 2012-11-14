package com.feelhub.web.resources;

import com.feelhub.domain.illustration.Illustration;
import com.feelhub.domain.keyword.uri.Uri;
import com.feelhub.repositories.Repositories;
import com.feelhub.repositories.fakeRepositories.WithFakeRepositories;
import com.feelhub.test.TestFactories;
import com.feelhub.web.*;
import com.feelhub.web.dto.KeywordData;
import org.junit.*;
import org.restlet.data.Status;
import org.restlet.ext.freemarker.TemplateRepresentation;

import java.util.Map;

import static org.hamcrest.Matchers.*;
import static org.junit.Assert.*;

public class TestsUriResource {

    @Rule
    public WithFakeRepositories repositories = new WithFakeRepositories();

    @Rule
    public WebApplicationTester restlet = new WebApplicationTester();

    @Test
    public void wordResourceIsMapped() {
        final Uri uri = TestFactories.keywords().newUri();
        final ClientResource uriResource = restlet.newClientResource("/uri/" + uri.getValue());

        uriResource.get();

        assertThat(uriResource.getStatus(), is(Status.SUCCESS_OK));
    }

    @Test
    public void hasTopicDataInDataModel() {
        final Uri uri = TestFactories.keywords().newUri();
        final ClientResource uriResource = restlet.newClientResource("/uri/" + uri.getValue());

        final TemplateRepresentation representation = (TemplateRepresentation) uriResource.get();

        final Map<String, Object> dataModel = (Map<String, Object>) representation.getDataModel();
        assertTrue(dataModel.containsKey("topicData"));
        assertThat(dataModel.get("topicData"), notNullValue());
    }

    @Test
    public void lookUpUri() {
        final Uri uri = TestFactories.keywords().newUri();
        final ClientResource uriResource = restlet.newClientResource("/uri/" + uri.getValue());

        final TemplateRepresentation representation = (TemplateRepresentation) uriResource.get();

        final Map<String, Object> dataModel = (Map<String, Object>) representation.getDataModel();
        assertTrue(dataModel.containsKey("topicData"));
        final KeywordData keywordData = (KeywordData) dataModel.get("topicData");
        assertThat(keywordData.getKeywordValue(), is(uri.getValue()));
        assertThat(keywordData.getLanguageCode(), is(uri.getLanguageCode()));
    }


    @Test
    public void createFakeUriIfItDoesNotExist() {
        final ClientResource uriResource = restlet.newClientResource("/uri/http://www.fake.com");

        final TemplateRepresentation representation = (TemplateRepresentation) uriResource.get();

        final Map<String, Object> dataModel = (Map<String, Object>) representation.getDataModel();
        assertTrue(dataModel.containsKey("topicData"));
        final KeywordData keywordData = (KeywordData) dataModel.get("topicData");
        assertThat(keywordData, notNullValue());
        assertThat(keywordData.getTopicId(), is(""));
        assertThat(uriResource.getStatus(), is(Status.CLIENT_ERROR_NOT_FOUND));
        assertThat(Repositories.keywords().getAll().size(), is(0));
    }

    @Test
    public void topicDataWithGoodValuesForExistingUriAndIllustration() {
        final Uri uri = TestFactories.keywords().newUri();
        final Illustration illustration = TestFactories.illustrations().newIllustrationFor(uri);
        final ClientResource uriResource = restlet.newClientResource("/uri/" + uri.getValue());

        final TemplateRepresentation representation = (TemplateRepresentation) uriResource.get();

        final Map<String, Object> dataModel = (Map<String, Object>) representation.getDataModel();
        assertTrue(dataModel.containsKey("topicData"));
        final KeywordData keywordData = (KeywordData) dataModel.get("topicData");
        assertThat(keywordData.getIllustrationLink(), is(illustration.getLink()));
        assertThat(keywordData.getKeywordValue(), is(uri.getValue()));
        assertThat(keywordData.getLanguageCode(), is(uri.getLanguageCode()));
        assertThat(keywordData.getTopicId(), is(uri.getTopicId().toString()));
    }
}
