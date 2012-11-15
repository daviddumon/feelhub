package com.feelhub.web.resources;

import com.feelhub.domain.illustration.Illustration;
import com.feelhub.domain.keyword.word.Word;
import com.feelhub.domain.thesaurus.FeelhubLanguage;
import com.feelhub.domain.topic.Topic;
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

public class TestsWordResource {

    @Rule
    public WithFakeRepositories repositories = new WithFakeRepositories();

    @Rule
    public WebApplicationTester restlet = new WebApplicationTester();

    @Test
    public void wordResourceIsMapped() {
        TestFactories.keywords().newWord("Word", FeelhubLanguage.none());
        final ClientResource wordResource = restlet.newClientResource("/word/Word");

        wordResource.get();

        assertThat(wordResource.getStatus(), is(Status.SUCCESS_OK));
    }

    @Test
    public void wordResourceIsMappedWithLanguage() {
        TestFactories.keywords().newWord("Word", FeelhubLanguage.forString("fr"));
        final ClientResource wordResource = restlet.newClientResource("/word/fr/Word");

        wordResource.get();

        assertThat(wordResource.getStatus(), is(Status.SUCCESS_OK));
    }

    @Test
    public void hasKeywordDataInDataModel() {
        final ClientResource wordResource = restlet.newClientResource("/word/en/cool");

        final TemplateRepresentation representation = (TemplateRepresentation) wordResource.get();

        final Map<String, Object> dataModel = (Map<String, Object>) representation.getDataModel();
        assertTrue(dataModel.containsKey("keywordData"));
        assertThat(dataModel.get("keywordData"), notNullValue());
    }

    @Test
    public void lookUpWord() {
        final Word word = TestFactories.keywords().newWord("Word", FeelhubLanguage.forString("de"));
        final ClientResource wordResource = restlet.newClientResource("/word/de/Word");

        final TemplateRepresentation representation = (TemplateRepresentation) wordResource.get();

        final Map<String, Object> dataModel = (Map<String, Object>) representation.getDataModel();
        final KeywordData keywordData = (KeywordData) dataModel.get("keywordData");
        assertThat(keywordData.getKeywordValue(), is(word.getValue()));
        assertThat(keywordData.getLanguageCode(), is(word.getLanguageCode()));
    }

    @Test
    public void lookUpWithNoLanguage() {
        final Word word = TestFactories.keywords().newWord("Word", FeelhubLanguage.none());
        final ClientResource wordResource = restlet.newClientResource("/word/Word");

        final TemplateRepresentation representation = (TemplateRepresentation) wordResource.get();

        final Map<String, Object> dataModel = (Map<String, Object>) representation.getDataModel();
        final KeywordData keywordData = (KeywordData) dataModel.get("keywordData");
        assertThat(keywordData.getKeywordValue(), is(word.getValue()));
        assertThat(keywordData.getLanguageCode(), is(word.getLanguageCode()));
    }

    @Test
    public void canIdentifyWordValueInQuery() {
        final ClientResource wordResource = restlet.newClientResource("/word/fr/Anotherword");

        final TemplateRepresentation representation = (TemplateRepresentation) wordResource.get();

        final Map<String, Object> dataModel = (Map<String, Object>) representation.getDataModel();
        final KeywordData keywordData = (KeywordData) dataModel.get("keywordData");
        assertThat(keywordData.getKeywordValue(), is("Anotherword"));
    }

    @Test
    public void canUseEncodedValueInQuery() {
        final Word word = TestFactories.keywords().newWord("Marie Dumon");
        final ClientResource wordResource = restlet.newClientResource("/word/Marie%20Dumon");

        final TemplateRepresentation representation = (TemplateRepresentation) wordResource.get();

        assertThat(wordResource.getStatus(), is(Status.SUCCESS_OK));
        final Map<String, Object> dataModel = (Map<String, Object>) representation.getDataModel();
        final KeywordData keywordData = (KeywordData) dataModel.get("keywordData");
        assertThat(keywordData.getKeywordValue(), is(word.getValue()));
    }

    @Test
    public void createFakeWordIfItDoesNotExist() {
        final ClientResource wordResource = restlet.newClientResource("/word/es/Word");

        final TemplateRepresentation representation = (TemplateRepresentation) wordResource.get();

        final Map<String, Object> dataModel = (Map<String, Object>) representation.getDataModel();
        assertTrue(dataModel.containsKey("keywordData"));
        final KeywordData keywordData = (KeywordData) dataModel.get("keywordData");
        assertThat(keywordData, notNullValue());
        assertThat(keywordData.getTopicId(), is(""));
        assertThat(wordResource.getStatus(), is(Status.CLIENT_ERROR_NOT_FOUND));
        assertThat(Repositories.keywords().getAll().size(), is(0));
    }

    @Test
    public void keywordDataWithGoodValuesForExistingWordAndIllustration() {
        final Topic topic = TestFactories.topics().newTopic();
        final Word word = TestFactories.keywords().newWord("Word", FeelhubLanguage.forString("es"), topic);
        final Illustration illustration = TestFactories.illustrations().newIllustration(topic, "link");
        final ClientResource wordResource = restlet.newClientResource("/word/es/Word");

        final TemplateRepresentation representation = (TemplateRepresentation) wordResource.get();

        final Map<String, Object> dataModel = (Map<String, Object>) representation.getDataModel();
        assertTrue(dataModel.containsKey("keywordData"));
        final KeywordData keywordData = (KeywordData) dataModel.get("keywordData");
        assertThat(keywordData.getIllustrationLink(), is(illustration.getLink()));
        assertThat(keywordData.getKeywordValue(), is(word.getValue()));
        assertThat(keywordData.getLanguageCode(), is(word.getLanguageCode()));
        assertThat(keywordData.getTopicId(), is(topic.getId().toString()));
    }
}
