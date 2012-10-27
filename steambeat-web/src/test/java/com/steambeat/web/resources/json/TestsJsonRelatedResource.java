package com.steambeat.web.resources.json;

import com.steambeat.domain.illustration.Illustration;
import com.steambeat.domain.keyword.Keyword;
import com.steambeat.domain.reference.Reference;
import com.steambeat.domain.relation.Relation;
import com.steambeat.domain.thesaurus.SteambeatLanguage;
import com.steambeat.repositories.fakeRepositories.WithFakeRepositories;
import com.steambeat.test.TestFactories;
import com.steambeat.web.ClientResource;
import com.steambeat.web.WebApplicationTester;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.junit.Rule;
import org.junit.Test;
import org.restlet.data.Status;
import org.restlet.ext.freemarker.TemplateRepresentation;

import java.io.IOException;

import static org.hamcrest.MatcherAssert.*;
import static org.hamcrest.Matchers.*;

public class TestsJsonRelatedResource {

    @Rule
    public WebApplicationTester restlet = new WebApplicationTester();

    @Rule
    public WithFakeRepositories repositories = new WithFakeRepositories();

    @Test
    public void relatedResourceIsMapped() {
        final ClientResource relatedResource = restlet.newClientResource("/json/related");

        relatedResource.get();

        assertThat(relatedResource.getStatus(), is(Status.SUCCESS_OK));
    }

    @Test
    public void canGetWithQueryString() {
        final ClientResource relatedResource = restlet.newClientResource("/json/related?q=test");

        relatedResource.get();

        assertThat(relatedResource.getStatus(), is(Status.SUCCESS_OK));
    }

    @Test
    public void canGetARelated() throws IOException, JSONException {
        TestFactories.relations().newRelation();
        final ClientResource resource = restlet.newClientResource("/json/related?skip=0&limit=1");

        final TemplateRepresentation representation = (TemplateRepresentation) resource.get();

        assertThat(representation, notNullValue());
        final JSONArray jsonArray = new JSONArray(representation.getText());
        assertThat(jsonArray, notNullValue());
        assertThat(jsonArray.length(), is(1));
    }

    @Test
    public void canGetRelatedForAReference() throws IOException, JSONException {
        final Reference reference = TestFactories.references().newReference();
        TestFactories.relations().newRelations(5, reference);
        TestFactories.relations().newRelations(20);
        final ClientResource resource = restlet.newClientResource("/json/related?referenceId=" + reference.getId());

        final TemplateRepresentation representation = (TemplateRepresentation) resource.get();

        assertThat(representation, notNullValue());
        final JSONArray jsonArray = new JSONArray(representation.getText());
        assertThat(jsonArray, notNullValue());
        assertThat(jsonArray.length(), is(5));
    }

    @Test
    public void canGetRelatedWithSkip() throws IOException, JSONException {
        TestFactories.relations().newRelations(5);
        final ClientResource resource = restlet.newClientResource("/json/related?skip=2");

        final TemplateRepresentation representation = (TemplateRepresentation) resource.get();

        assertThat(representation, notNullValue());
        final JSONArray jsonArray = new JSONArray(representation.getText());
        assertThat(jsonArray, notNullValue());
        assertThat(jsonArray.length(), is(3));
    }

    @Test
    public void canGetRelatedWithLimit() throws IOException, JSONException {
        TestFactories.relations().newRelations(5);
        final ClientResource resource = restlet.newClientResource("/json/related?limit=2");

        final TemplateRepresentation representation = (TemplateRepresentation) resource.get();

        assertThat(representation, notNullValue());
        final JSONArray jsonArray = new JSONArray(representation.getText());
        assertThat(jsonArray, notNullValue());
        assertThat(jsonArray.length(), is(2));
    }

    @Test
    public void defaultLimitIs100() throws IOException, JSONException {
        TestFactories.relations().newRelations(150);
        final ClientResource resource = restlet.newClientResource("/json/related");

        final TemplateRepresentation representation = (TemplateRepresentation) resource.get();

        assertThat(representation, notNullValue());
        final JSONArray jsonArray = new JSONArray(representation.getText());
        assertThat(jsonArray, notNullValue());
        assertThat(jsonArray.length(), is(100));
    }

    @Test
    public void canGetEmptyReferenceData() throws IOException, JSONException {
        final Relation relation = TestFactories.relations().newRelation();
        final ClientResource resource = restlet.newClientResource("/json/related?referenceId=" + relation.getFromId());

        final TemplateRepresentation representation = (TemplateRepresentation) resource.get();

        assertThat(representation, notNullValue());
        final JSONArray jsonArray = new JSONArray(representation.getText());
        final JSONObject referenceDataAsJSON = jsonArray.getJSONObject(0);
        assertThat(referenceDataAsJSON, notNullValue());
        assertThat(referenceDataAsJSON.get("referenceId").toString(), is(relation.getToId().toString()));
        assertThat(referenceDataAsJSON.get("keywordValue").toString(), is("?"));
        assertThat(referenceDataAsJSON.get("languageCode").toString(), is("none"));
        assertThat(referenceDataAsJSON.get("illustrationLink").toString(), is(""));
    }

    @Test
    public void canGetWithGoodIllustration() throws IOException, JSONException {
        final Reference to = TestFactories.references().newReference();
        final Relation relation = TestFactories.relations().newRelation(TestFactories.references().newReference(), to);
        final Illustration illustration = TestFactories.illustrations().newIllustration(to);
        final ClientResource resource = restlet.newClientResource("/json/related?referenceId=" + relation.getFromId());

        final TemplateRepresentation representation = (TemplateRepresentation) resource.get();

        assertThat(representation, notNullValue());
        final JSONArray jsonArray = new JSONArray(representation.getText());
        final JSONObject referenceDataAsJSON = jsonArray.getJSONObject(0);
        assertThat(referenceDataAsJSON, notNullValue());
        assertThat(referenceDataAsJSON.get("referenceId").toString(), is(to.getId().toString()));
        assertThat(referenceDataAsJSON.get("keywordValue").toString(), is("?"));
        assertThat(referenceDataAsJSON.get("languageCode").toString(), is("none"));
        assertThat(referenceDataAsJSON.get("illustrationLink").toString(), is(illustration.getLink()));
    }

    @Test
    public void canGetWithGoodKeyword() throws IOException, JSONException {
        final Reference to = TestFactories.references().newReference();
        final Relation relation = TestFactories.relations().newRelation(TestFactories.references().newReference(), to);
        final Keyword keyword = TestFactories.keywords().newKeyword("keyword", SteambeatLanguage.none(), to);
        final ClientResource resource = restlet.newClientResource("/json/related?referenceId=" + relation.getFromId());

        final TemplateRepresentation representation = (TemplateRepresentation) resource.get();

        assertThat(representation, notNullValue());
        final JSONArray jsonArray = new JSONArray(representation.getText());
        final JSONObject referenceDataAsJSON = jsonArray.getJSONObject(0);
        assertThat(referenceDataAsJSON, notNullValue());
        assertThat(referenceDataAsJSON.get("referenceId").toString(), is(to.getId().toString()));
        assertThat(referenceDataAsJSON.get("keywordValue").toString(), is(keyword.getValue()));
        assertThat(referenceDataAsJSON.get("languageCode").toString(), is(keyword.getLanguageCode()));
        assertThat(referenceDataAsJSON.get("illustrationLink").toString(), is(""));
    }

    @Test
    public void canGetWithGoodKeywordWithGoodLanguage() throws IOException, JSONException {
        final Reference to = TestFactories.references().newReference();
        final Relation relation = TestFactories.relations().newRelation(TestFactories.references().newReference(), to);
        TestFactories.keywords().newKeyword("eskeyword", SteambeatLanguage.forString("es"), to);
        final Keyword keyword = TestFactories.keywords().newKeyword("keyword", SteambeatLanguage.reference(), to);
        TestFactories.keywords().newKeyword("frkeyword", SteambeatLanguage.forString("fr"), to);
        final ClientResource resource = restlet.newClientResource("/json/related?referenceId=" + relation.getFromId() + "&limit=50&languageCode=" + SteambeatLanguage.reference().getCode());

        final TemplateRepresentation representation = (TemplateRepresentation) resource.get();

        assertThat(representation, notNullValue());
        final JSONArray jsonArray = new JSONArray(representation.getText());
        final JSONObject referenceDataAsJSON = jsonArray.getJSONObject(0);
        assertThat(referenceDataAsJSON, notNullValue());
        assertThat(referenceDataAsJSON.get("referenceId").toString(), is(to.getId().toString()));
        assertThat(referenceDataAsJSON.get("keywordValue").toString(), is(keyword.getValue()));
        assertThat(referenceDataAsJSON.get("languageCode").toString(), is(keyword.getLanguageCode()));
        assertThat(referenceDataAsJSON.get("illustrationLink").toString(), is(""));
    }

    @Test
    public void defaultLanguageIsReference() throws IOException, JSONException {
        final Reference to = TestFactories.references().newReference();
        final Relation relation = TestFactories.relations().newRelation(TestFactories.references().newReference(), to);
        TestFactories.keywords().newKeyword("eskeyword", SteambeatLanguage.forString("es"), to);
        final Keyword keyword = TestFactories.keywords().newKeyword("keyword", SteambeatLanguage.reference(), to);
        TestFactories.keywords().newKeyword("dekeyword", SteambeatLanguage.forString("de"), to);
        final ClientResource resource = restlet.newClientResource("/json/related?referenceId=" + relation.getFromId() + "&limit=50&languageCode=fr");

        final TemplateRepresentation representation = (TemplateRepresentation) resource.get();

        assertThat(representation, notNullValue());
        final JSONArray jsonArray = new JSONArray(representation.getText());
        final JSONObject referenceDataAsJSON = jsonArray.getJSONObject(0);
        assertThat(referenceDataAsJSON, notNullValue());
        assertThat(referenceDataAsJSON.get("referenceId").toString(), is(to.getId().toString()));
        assertThat(referenceDataAsJSON.get("keywordValue").toString(), is(keyword.getValue()));
        assertThat(referenceDataAsJSON.get("languageCode").toString(), is(keyword.getLanguageCode()));
        assertThat(referenceDataAsJSON.get("illustrationLink").toString(), is(""));
    }
}
