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

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Map;

import static org.fest.assertions.Assertions.*;


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

        assertThat(uriResource.getStatus()).isEqualTo(Status.SUCCESS_OK);
    }

    @Test
    public void hasKeywordDataInDataModel() {
        final Uri uri = TestFactories.keywords().newUri();
        final ClientResource uriResource = restlet.newClientResource("/uri/" + uri.getValue());

        final TemplateRepresentation representation = (TemplateRepresentation) uriResource.get();

        final Map<String, Object> dataModel = (Map<String, Object>) representation.getDataModel();
        assertThat(dataModel.containsKey("keywordData")).isTrue();
        assertThat(dataModel.get("keywordData")).isNotNull();
    }

    @Test
    public void lookUpUri() {
        final Uri uri = TestFactories.keywords().newUri();
        final ClientResource uriResource = restlet.newClientResource("/uri/" + uri.getValue());

        final TemplateRepresentation representation = (TemplateRepresentation) uriResource.get();

        final Map<String, Object> dataModel = (Map<String, Object>) representation.getDataModel();
        assertThat(dataModel.containsKey("keywordData")).isTrue();
        final KeywordData keywordData = (KeywordData) dataModel.get("keywordData");
        assertThat(keywordData.getKeywordValue()).isEqualTo(uri.getValue());
        assertThat(keywordData.getLanguageCode()).isEqualTo(uri.getLanguageCode());
    }

    @Test
    public void createFakeUriIfItDoesNotExist() {
        final ClientResource uriResource = restlet.newClientResource("/uri/http://www.fake.com");

        final TemplateRepresentation representation = (TemplateRepresentation) uriResource.get();

        final Map<String, Object> dataModel = (Map<String, Object>) representation.getDataModel();
        assertThat(dataModel.containsKey("keywordData")).isTrue();
        final KeywordData keywordData = (KeywordData) dataModel.get("keywordData");
        assertThat(keywordData).isNotNull();
        assertThat(keywordData.getTopicId()).isEqualTo("");
        assertThat(uriResource.getStatus()).isEqualTo(Status.CLIENT_ERROR_NOT_FOUND);
        assertThat(Repositories.keywords().getAll().size()).isZero();
    }

    @Test
    public void keywordDataWithGoodValuesForExistingUriAndIllustration() {
        final Uri uri = TestFactories.keywords().newUri();
        final Illustration illustration = TestFactories.illustrations().newIllustrationFor(uri);
        final ClientResource uriResource = restlet.newClientResource("/uri/" + uri.getValue());

        final TemplateRepresentation representation = (TemplateRepresentation) uriResource.get();

        final Map<String, Object> dataModel = (Map<String, Object>) representation.getDataModel();
        assertThat(dataModel.containsKey("keywordData")).isTrue();
        final KeywordData keywordData = (KeywordData) dataModel.get("keywordData");
        assertThat(keywordData.getIllustrationLink()).isEqualTo(illustration.getLink());
        assertThat(keywordData.getKeywordValue()).isEqualTo(uri.getValue());
        assertThat(keywordData.getLanguageCode()).isEqualTo(uri.getLanguageCode());
        assertThat(keywordData.getTopicId()).isEqualTo(uri.getTopicId().toString());
    }

    @Test
    public void keywordDataWithGoodValuesForNonExistingUri() {
        final ClientResource uriResource = restlet.newClientResource("/uri/http://www.google.fr");

        final TemplateRepresentation representation = (TemplateRepresentation) uriResource.get();

        final Map<String, Object> dataModel = (Map<String, Object>) representation.getDataModel();
        assertThat(dataModel.containsKey("keywordData")).isTrue();
        final KeywordData keywordData = (KeywordData) dataModel.get("keywordData");
        assertThat(keywordData.getKeywordValue()).isEqualTo("http://www.google.fr");
    }

    @Test
    public void canUseEncodedUri() throws UnsupportedEncodingException {
        final Uri uri = TestFactories.keywords().newUri();
        final Illustration illustration = TestFactories.illustrations().newIllustrationFor(uri);
        final ClientResource uriResource = restlet.newClientResource("/uri/" + URLEncoder.encode(uri.getValue(), "UTF-8"));

        final TemplateRepresentation representation = (TemplateRepresentation) uriResource.get();

        assertThat(uriResource.getStatus()).isEqualTo(Status.SUCCESS_OK);
        final Map<String, Object> dataModel = (Map<String, Object>) representation.getDataModel();
        assertThat(dataModel.containsKey("keywordData")).isTrue();
        final KeywordData keywordData = (KeywordData) dataModel.get("keywordData");
        assertThat(keywordData.getIllustrationLink()).isEqualTo(illustration.getLink());
        assertThat(keywordData.getKeywordValue()).isEqualTo(uri.getValue());
        assertThat(keywordData.getLanguageCode()).isEqualTo(uri.getLanguageCode());
        assertThat(keywordData.getTopicId()).isEqualTo(uri.getTopicId().toString());
    }
}
