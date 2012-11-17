package com.feelhub.web.resources.api;

import com.feelhub.domain.illustration.Illustration;
import com.feelhub.domain.keyword.Keyword;
import com.feelhub.domain.relation.Relation;
import com.feelhub.domain.thesaurus.FeelhubLanguage;
import com.feelhub.domain.topic.Topic;
import com.feelhub.repositories.fakeRepositories.WithFakeRepositories;
import com.feelhub.test.TestFactories;
import com.feelhub.web.*;
import org.json.*;
import org.junit.*;
import org.junit.Test;
import org.restlet.data.Status;
import org.restlet.ext.freemarker.TemplateRepresentation;

import java.io.IOException;

import static org.hamcrest.MatcherAssert.*;
import static org.hamcrest.Matchers.*;

public class TestsApiRelatedResource {

    @Rule
    public WebApplicationTester restlet = new WebApplicationTester();

    @Rule
    public WithFakeRepositories repositories = new WithFakeRepositories();

    @Test
    public void relatedResourceIsMapped() {
        final ClientResource relatedResource = restlet.newClientResource("/api/related");

        relatedResource.get();

        assertThat(relatedResource.getStatus(), is(Status.SUCCESS_OK));
    }

    @Test
    public void canGetWithQueryString() {
        final ClientResource relatedResource = restlet.newClientResource("/api/related?q=test");

        relatedResource.get();

        assertThat(relatedResource.getStatus(), is(Status.SUCCESS_OK));
    }

    @Test
    public void canGetARelated() throws IOException, JSONException {
        TestFactories.relations().newRelation();
        final ClientResource resource = restlet.newClientResource("/api/related?skip=0&limit=1");

        final TemplateRepresentation representation = (TemplateRepresentation) resource.get();

        assertThat(representation, notNullValue());
        final JSONArray jsonArray = new JSONArray(representation.getText());
        assertThat(jsonArray, notNullValue());
        assertThat(jsonArray.length(), is(1));
    }

    @Test
    public void canGetRelatedForATopic() throws IOException, JSONException {
        final Topic topic = TestFactories.topics().newTopic();
        TestFactories.relations().newRelations(5, topic.getId());
        TestFactories.relations().newRelations(20);
        final ClientResource resource = restlet.newClientResource("/api/related?topicId=" + topic.getId());

        final TemplateRepresentation representation = (TemplateRepresentation) resource.get();

        assertThat(representation, notNullValue());
        final JSONArray jsonArray = new JSONArray(representation.getText());
        assertThat(jsonArray, notNullValue());
        assertThat(jsonArray.length(), is(5));
    }

    @Test
    public void canGetRelatedWithSkip() throws IOException, JSONException {
        TestFactories.relations().newRelations(5);
        final ClientResource resource = restlet.newClientResource("/api/related?skip=2");

        final TemplateRepresentation representation = (TemplateRepresentation) resource.get();

        assertThat(representation, notNullValue());
        final JSONArray jsonArray = new JSONArray(representation.getText());
        assertThat(jsonArray, notNullValue());
        assertThat(jsonArray.length(), is(3));
    }

    @Test
    public void canGetRelatedWithLimit() throws IOException, JSONException {
        TestFactories.relations().newRelations(5);
        final ClientResource resource = restlet.newClientResource("/api/related?limit=2");

        final TemplateRepresentation representation = (TemplateRepresentation) resource.get();

        assertThat(representation, notNullValue());
        final JSONArray jsonArray = new JSONArray(representation.getText());
        assertThat(jsonArray, notNullValue());
        assertThat(jsonArray.length(), is(2));
    }

    @Test
    public void defaultLimitIs100() throws IOException, JSONException {
        TestFactories.relations().newRelations(150);
        final ClientResource resource = restlet.newClientResource("/api/related");

        final TemplateRepresentation representation = (TemplateRepresentation) resource.get();

        assertThat(representation, notNullValue());
        final JSONArray jsonArray = new JSONArray(representation.getText());
        assertThat(jsonArray, notNullValue());
        assertThat(jsonArray.length(), is(100));
    }

    @Test
    public void canGetEmptyKeywordData() throws IOException, JSONException {
        final Relation relation = TestFactories.relations().newRelation();
        final ClientResource resource = restlet.newClientResource("/api/related?topicId=" + relation.getFromId());

        final TemplateRepresentation representation = (TemplateRepresentation) resource.get();

        assertThat(representation, notNullValue());
        final JSONArray jsonArray = new JSONArray(representation.getText());
        final JSONObject keywordDataAsJson = jsonArray.getJSONObject(0);
        assertThat(keywordDataAsJson, notNullValue());
        assertThat(keywordDataAsJson.get("topicId").toString(), is(relation.getToId().toString()));
        assertThat(keywordDataAsJson.get("keywordValue").toString(), is("?"));
        assertThat(keywordDataAsJson.get("languageCode").toString(), is("none"));
        assertThat(keywordDataAsJson.get("illustrationLink").toString(), is(""));
    }

    @Test
    public void canGetWithGoodIllustration() throws IOException, JSONException {
        final Topic to = TestFactories.topics().newTopic();
        final Relation relation = TestFactories.relations().newRelation(TestFactories.topics().newTopic().getId(), to.getId());
        final Illustration illustration = TestFactories.illustrations().newIllustration(to.getId());
        final ClientResource resource = restlet.newClientResource("/api/related?topicId=" + relation.getFromId());

        final TemplateRepresentation representation = (TemplateRepresentation) resource.get();

        assertThat(representation, notNullValue());
        final JSONArray jsonArray = new JSONArray(representation.getText());
        final JSONObject keywordDataAsJson = jsonArray.getJSONObject(0);
        assertThat(keywordDataAsJson, notNullValue());
        assertThat(keywordDataAsJson.get("topicId").toString(), is(to.getId().toString()));
        assertThat(keywordDataAsJson.get("keywordValue").toString(), is("?"));
        assertThat(keywordDataAsJson.get("languageCode").toString(), is("none"));
        assertThat(keywordDataAsJson.get("illustrationLink").toString(), is(illustration.getLink()));
    }

    @Test
    public void canGetWithGoodKeyword() throws IOException, JSONException {
        final Topic to = TestFactories.topics().newTopic();
        final Relation relation = TestFactories.relations().newRelation(TestFactories.topics().newTopic().getId(), to.getId());
        final Keyword keyword = TestFactories.keywords().newWord("keyword", FeelhubLanguage.none(), to.getId());
        final ClientResource resource = restlet.newClientResource("/api/related?topicId=" + relation.getFromId());

        final TemplateRepresentation representation = (TemplateRepresentation) resource.get();

        assertThat(representation, notNullValue());
        final JSONArray jsonArray = new JSONArray(representation.getText());
        final JSONObject keywordDataAsJson = jsonArray.getJSONObject(0);
        assertThat(keywordDataAsJson, notNullValue());
        assertThat(keywordDataAsJson.get("topicId").toString(), is(to.getId().toString()));
        assertThat(keywordDataAsJson.get("keywordValue").toString(), is(keyword.getValue()));
        assertThat(keywordDataAsJson.get("languageCode").toString(), is(keyword.getLanguageCode()));
        assertThat(keywordDataAsJson.get("illustrationLink").toString(), is(""));
    }

    @Test
    public void canGetWithGoodKeywordWithGoodLanguage() throws IOException, JSONException {
        final Topic to = TestFactories.topics().newTopic();
        final Relation relation = TestFactories.relations().newRelation(TestFactories.topics().newTopic().getId(), to.getId());
        TestFactories.keywords().newWord("eskeyword", FeelhubLanguage.forString("es"), to.getId());
        final Keyword keyword = TestFactories.keywords().newWord("keyword", FeelhubLanguage.reference(), to.getId());
        TestFactories.keywords().newWord("frkeyword", FeelhubLanguage.forString("fr"), to.getId());
        final ClientResource resource = restlet.newClientResource("/api/related?topicId=" + relation.getFromId() + "&limit=50&languageCode=" + FeelhubLanguage.reference().getCode());

        final TemplateRepresentation representation = (TemplateRepresentation) resource.get();

        assertThat(representation, notNullValue());
        final JSONArray jsonArray = new JSONArray(representation.getText());
        final JSONObject keywordDataAsJson = jsonArray.getJSONObject(0);
        assertThat(keywordDataAsJson, notNullValue());
        assertThat(keywordDataAsJson.get("topicId").toString(), is(to.getId().toString()));
        assertThat(keywordDataAsJson.get("keywordValue").toString(), is(keyword.getValue()));
        assertThat(keywordDataAsJson.get("languageCode").toString(), is(keyword.getLanguageCode()));
        assertThat(keywordDataAsJson.get("illustrationLink").toString(), is(""));
    }

    @Test
    public void defaultLanguageIsReference() throws IOException, JSONException {
        final Topic to = TestFactories.topics().newTopic();
        final Relation relation = TestFactories.relations().newRelation(TestFactories.topics().newTopic().getId(), to.getId());
        TestFactories.keywords().newWord("eskeyword", FeelhubLanguage.forString("es"), to.getId());
        final Keyword keyword = TestFactories.keywords().newWord("keyword", FeelhubLanguage.reference(), to.getId());
        TestFactories.keywords().newWord("dekeyword", FeelhubLanguage.forString("de"), to.getId());
        final ClientResource resource = restlet.newClientResource("/api/related?topicId=" + relation.getFromId() + "&limit=50&languageCode=fr");

        final TemplateRepresentation representation = (TemplateRepresentation) resource.get();

        assertThat(representation, notNullValue());
        final JSONArray jsonArray = new JSONArray(representation.getText());
        final JSONObject keywordDataAsJson = jsonArray.getJSONObject(0);
        assertThat(keywordDataAsJson, notNullValue());
        assertThat(keywordDataAsJson.get("topicId").toString(), is(to.getId().toString()));
        assertThat(keywordDataAsJson.get("keywordValue").toString(), is(keyword.getValue()));
        assertThat(keywordDataAsJson.get("languageCode").toString(), is(keyword.getLanguageCode()));
        assertThat(keywordDataAsJson.get("illustrationLink").toString(), is(""));
    }
}
