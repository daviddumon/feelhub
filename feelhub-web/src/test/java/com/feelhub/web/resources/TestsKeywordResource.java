package com.feelhub.web.resources;

import com.feelhub.domain.illustration.Illustration;
import com.feelhub.domain.keyword.Keyword;
import com.feelhub.domain.thesaurus.FeelhubLanguage;
import com.feelhub.domain.topic.Topic;
import com.feelhub.repositories.Repositories;
import com.feelhub.repositories.fakeRepositories.WithFakeRepositories;
import com.feelhub.test.TestFactories;
import com.feelhub.web.*;
import com.feelhub.web.dto.TopicData;
import org.junit.*;
import org.restlet.data.Status;
import org.restlet.ext.freemarker.TemplateRepresentation;

import java.util.Map;

import static org.hamcrest.Matchers.*;
import static org.junit.Assert.*;

public class TestsKeywordResource {

    @Rule
    public WithFakeRepositories repositories = new WithFakeRepositories();

    @Rule
    public WebApplicationTester restlet = new WebApplicationTester();

    @Test
    public void keywordResourceIsMapped() {
        TestFactories.keywords().newWord("Keyword", FeelhubLanguage.none());
        final ClientResource keywordResource = restlet.newClientResource("/topic/keyword");

        keywordResource.get();

        assertThat(keywordResource.getStatus(), is(Status.SUCCESS_OK));
    }

    @Test
    public void keywordResourceIsMappedWithLanguage() {
        TestFactories.keywords().newWord("Keyword", FeelhubLanguage.forString("fr"));
        final ClientResource keywordResource = restlet.newClientResource("/topic/fr/keyword");

        keywordResource.get();

        assertThat(keywordResource.getStatus(), is(Status.SUCCESS_OK));
    }

    @Test
    public void hasTopicDataInDataModel() {
        final ClientResource keywordResource = restlet.newClientResource("/topic/en/cool");

        final TemplateRepresentation representation = (TemplateRepresentation) keywordResource.get();

        final Map<String, Object> dataModel = (Map<String, Object>) representation.getDataModel();
        assertTrue(dataModel.containsKey("topicData"));
        assertThat(dataModel.get("topicData"), notNullValue());
    }

    @Test
    public void lookUpKeyword() {
        final Keyword keyword = TestFactories.keywords().newWord("keyword", FeelhubLanguage.forString("de"));
        final ClientResource keywordResource = restlet.newClientResource("/topic/de/keyword");

        final TemplateRepresentation representation = (TemplateRepresentation) keywordResource.get();

        final Map<String, Object> dataModel = (Map<String, Object>) representation.getDataModel();
        final TopicData topicData = (TopicData) dataModel.get("topicData");
        assertThat(topicData.getKeywordValue(), is(keyword.getValue()));
        assertThat(topicData.getLanguageCode(), is(keyword.getLanguageCode()));
    }

    @Test
    public void lookUpWithNoLanguage() {
        final Keyword keyword = TestFactories.keywords().newWord("keyword", FeelhubLanguage.none());
        final ClientResource keywordResource = restlet.newClientResource("/topic/keyword");

        final TemplateRepresentation representation = (TemplateRepresentation) keywordResource.get();

        final Map<String, Object> dataModel = (Map<String, Object>) representation.getDataModel();
        final TopicData topicData = (TopicData) dataModel.get("topicData");
        assertThat(topicData.getKeywordValue(), is(keyword.getValue()));
        assertThat(topicData.getLanguageCode(), is(keyword.getLanguageCode()));
    }

    @Test
    public void canIdentifyKeywordValueInUri() {
        final ClientResource keywordResource = restlet.newClientResource("/topic/fr/anotherkeyword");

        final TemplateRepresentation representation = (TemplateRepresentation) keywordResource.get();

        final Map<String, Object> dataModel = (Map<String, Object>) representation.getDataModel();
        final TopicData topicData = (TopicData) dataModel.get("topicData");
        assertThat(topicData.getKeywordValue(), is("anotherkeyword"));
    }

    @Test
    public void createFakeKeywordIfItDoesNotExist() {
        final ClientResource keywordResource = restlet.newClientResource("/topic/es/keyword");

        final TemplateRepresentation representation = (TemplateRepresentation) keywordResource.get();

        final Map<String, Object> dataModel = (Map<String, Object>) representation.getDataModel();
        assertTrue(dataModel.containsKey("topicData"));
        final TopicData topicData = (TopicData) dataModel.get("topicData");
        assertThat(topicData, notNullValue());
        assertThat(topicData.getTopicId(), is(""));
        assertThat(keywordResource.getStatus(), is(Status.CLIENT_ERROR_NOT_FOUND));
        assertThat(Repositories.keywords().getAll().size(), is(0));
    }

    @Test
    public void topicDataWithGoodValuesForExistingKeywordAndIllustration() {
        final Topic topic = TestFactories.topics().newTopic();
        final Keyword keyword = TestFactories.keywords().newWord("Keyword", FeelhubLanguage.forString("es"), topic);
        final Illustration illustration = TestFactories.illustrations().newIllustration(topic, "link");
        final ClientResource keywordResource = restlet.newClientResource("/topic/es/keyword");

        final TemplateRepresentation representation = (TemplateRepresentation) keywordResource.get();

        final Map<String, Object> dataModel = (Map<String, Object>) representation.getDataModel();
        assertTrue(dataModel.containsKey("topicData"));
        final TopicData topicData = (TopicData) dataModel.get("topicData");
        assertThat(topicData.getIllustrationLink(), is(illustration.getLink()));
        assertThat(topicData.getKeywordValue(), is(keyword.getValue()));
        assertThat(topicData.getLanguageCode(), is(keyword.getLanguageCode()));
        assertThat(topicData.getTopicId(), is(topic.getId().toString()));
    }
}
