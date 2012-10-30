package com.feelhub.web.resources.json;

import com.feelhub.domain.eventbus.WithDomainEvent;
import com.feelhub.domain.opinion.*;
import com.feelhub.domain.reference.Reference;
import com.feelhub.domain.thesaurus.FeelhubLanguage;
import com.feelhub.repositories.fakeRepositories.WithFakeRepositories;
import com.feelhub.test.TestFactories;
import com.feelhub.web.*;
import org.json.*;
import org.junit.*;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.restlet.data.Status;
import org.restlet.ext.freemarker.TemplateRepresentation;

import java.io.IOException;

import static org.hamcrest.MatcherAssert.*;
import static org.hamcrest.Matchers.*;

public class TestsJsonOpinionsResource {

    @Rule
    public WithFakeRepositories repositories = new WithFakeRepositories();

    @Rule
    public ExpectedException exception = ExpectedException.none();

    @Rule
    public WebApplicationTester restlet = new WebApplicationTester();

    @Rule
    public WithDomainEvent events = new WithDomainEvent();

    @Test
    public void opinionsResourceIsMapped() {
        final ClientResource opinionsResource = restlet.newClientResource("/json/opinions");

        opinionsResource.get();

        assertThat(opinionsResource.getStatus(), is(Status.SUCCESS_OK));
    }

    @Test
    public void canGetWithQueryString() {
        final ClientResource opinionsResource = restlet.newClientResource("/json/opinions?q=test");

        opinionsResource.get();

        assertThat(opinionsResource.getStatus(), is(Status.SUCCESS_OK));
    }

    @Test
    public void canGetAnOpinion() throws IOException, JSONException {
        TestFactories.opinions().newOpinion();
        final ClientResource resource = restlet.newClientResource("/json/opinions?skip=0&limit=1");

        final TemplateRepresentation representation = (TemplateRepresentation) resource.get();

        assertThat(representation, notNullValue());
        final JSONArray jsonArray = new JSONArray(representation.getText());
        assertThat(jsonArray, notNullValue());
        assertThat(jsonArray.length(), is(1));
    }

    @Test
    public void defaultLimitIs100() throws IOException, JSONException {
        TestFactories.opinions().newOpinions(150);
        final ClientResource clientResource = restlet.newClientResource("/json/opinions?skip=0");

        final TemplateRepresentation representation = (TemplateRepresentation) clientResource.get();

        assertThat(representation, notNullValue());
        final JSONArray jsonArray = new JSONArray(representation.getText());
        assertThat(jsonArray.length(), is(100));
    }

    @Test
    public void defaultSkipIs0() throws IOException, JSONException {
        TestFactories.opinions().newOpinions(100);
        final ClientResource clientResource = restlet.newClientResource("/json/opinions?limit=10");

        final TemplateRepresentation representation = (TemplateRepresentation) clientResource.get();

        assertThat(representation, notNullValue());
        final JSONArray jsonArray = new JSONArray(representation.getText());
        assertThat(((JSONObject) jsonArray.get(0)).get("text").toString(), is("i0"));
    }

    @Test
    public void canGetMultipleOpinions() throws JSONException, IOException {
        final ClientResource resource = restlet.newClientResource("/json/opinions?skip=0&limit=10");
        TestFactories.opinions().newOpinion();
        TestFactories.opinions().newOpinion();
        TestFactories.opinions().newOpinion();

        final TemplateRepresentation representation = (TemplateRepresentation) resource.get();

        final JSONArray jsonArray = new JSONArray(representation.getText());
        assertThat(jsonArray, notNullValue());
        assertThat(jsonArray.length(), is(3));
    }

    @Test
    public void canGetMultipleOpinionsWithSkip() throws JSONException, IOException {
        TestFactories.opinions().newOpinion();
        TestFactories.opinions().newOpinion();
        TestFactories.opinions().newOpinion();
        final ClientResource resource = restlet.newClientResource("/json/opinions?skip=1");

        final TemplateRepresentation representation = (TemplateRepresentation) resource.get();

        final JSONArray jsonArray = new JSONArray(representation.getText());
        assertThat(jsonArray, notNullValue());
        assertThat(jsonArray.length(), is(2));
    }

    @Test
    public void canThrowFeelhubJsonException() {
        final ClientResource resource = restlet.newClientResource("/json/opinions?skip=0&limit=101");

        resource.get();

        assertThat(resource.getStatus(), is(Status.CLIENT_ERROR_BAD_REQUEST));
    }

    @Test
    public void canGetOpinionForSubject() throws IOException, JSONException {
        final Reference reference = TestFactories.references().newReference();
        TestFactories.opinions().newOpinions(10);
        TestFactories.opinions().newOpinions(reference, 10);
        TestFactories.opinions().newOpinions(10);
        final ClientResource clientResource = restlet.newClientResource("/json/opinions?referenceId=" + reference.getId());

        final TemplateRepresentation representation = (TemplateRepresentation) clientResource.get();

        assertThat(representation, notNullValue());
        final JSONArray jsonArray = new JSONArray(representation.getText());
        assertThat(jsonArray.length(), is(10));
    }

    @Test
    public void canGetOpinionWithALanguage() throws IOException, JSONException {
        final Reference reference = TestFactories.references().newReference();
        TestFactories.keywords().newKeyword("keyword", FeelhubLanguage.reference(), reference);
        TestFactories.keywords().newKeyword("mot", FeelhubLanguage.forString("fr"), reference);
        final Judgment judgment = TestFactories.judgments().newJudgment(reference, Feeling.good);
        TestFactories.opinions().newOpinion("my opinion", judgment);
        final ClientResource clientResource = restlet.newClientResource("/json/opinions?referenceId=" + reference.getId() + "&languageCode=fr");

        final TemplateRepresentation representation = (TemplateRepresentation) clientResource.get();

        final JSONArray jsonArray = new JSONArray(representation.getText());
        final JSONObject jsonOpinion = jsonArray.getJSONObject(0);
        assertThat(jsonOpinion.get("text").toString(), is("my opinion"));
        final JSONArray jsonReferenceDatas = jsonOpinion.getJSONArray("referenceDatas");
        assertThat(jsonReferenceDatas.length(), is(1));
        final JSONObject jsonReferenceData = jsonReferenceDatas.getJSONObject(0);
        assertThat(jsonReferenceData.get("referenceId").toString(), is(reference.getId().toString()));
        assertThat(jsonReferenceData.get("feeling").toString(), is(judgment.getFeeling().toString()));
        assertThat(jsonReferenceData.get("keywordValue").toString(), is("mot"));
        assertThat(jsonReferenceData.get("languageCode").toString(), is("fr"));
    }

    @Test
    public void useDefaultLanguage() throws IOException, JSONException {
        final Reference reference = TestFactories.references().newReference();
        TestFactories.keywords().newKeyword("keyword", FeelhubLanguage.reference(), reference);
        TestFactories.keywords().newKeyword("mot", FeelhubLanguage.forString("fr"), reference);
        final Judgment judgment = TestFactories.judgments().newJudgment(reference, Feeling.good);
        TestFactories.opinions().newOpinion("my opinion", judgment);
        final ClientResource clientResource = restlet.newClientResource("/json/opinions?referenceId=" + reference.getId());

        final TemplateRepresentation representation = (TemplateRepresentation) clientResource.get();

        final JSONArray jsonArray = new JSONArray(representation.getText());
        final JSONObject jsonOpinion = jsonArray.getJSONObject(0);
        assertThat(jsonOpinion.get("text").toString(), is("my opinion"));
        final JSONArray jsonReferenceDatas = jsonOpinion.getJSONArray("referenceDatas");
        assertThat(jsonReferenceDatas.length(), is(1));
        final JSONObject jsonReferenceData = jsonReferenceDatas.getJSONObject(0);
        assertThat(jsonReferenceData.get("referenceId").toString(), is(reference.getId().toString()));
        assertThat(jsonReferenceData.get("feeling").toString(), is(judgment.getFeeling().toString()));
        assertThat(jsonReferenceData.get("keywordValue").toString(), is("keyword"));
        assertThat(jsonReferenceData.get("languageCode").toString(), is("en"));
    }

    @Test
    public void hasIllustrationData() throws IOException, JSONException {
        final Reference reference = TestFactories.references().newReference();
        TestFactories.illustrations().newIllustration(reference, "link");
        TestFactories.keywords().newKeyword("keyword", FeelhubLanguage.reference(), reference);
        final Judgment judgment = TestFactories.judgments().newJudgment(reference, Feeling.good);
        TestFactories.opinions().newOpinion("my opinion", judgment);
        final ClientResource clientResource = restlet.newClientResource("/json/opinions?referenceId=" + reference.getId());

        final TemplateRepresentation representation = (TemplateRepresentation) clientResource.get();

        final JSONArray jsonArray = new JSONArray(representation.getText());
        final JSONObject jsonReferenceData = jsonArray.getJSONObject(0).getJSONArray("referenceDatas").getJSONObject(0);
        assertThat(jsonReferenceData.get("illustrationLink").toString(), is("link"));
    }

    @Test
    public void hasLanguageCodeData() throws IOException, JSONException {
        final Reference reference = TestFactories.references().newReference();
        TestFactories.illustrations().newIllustration(reference, "link");
        TestFactories.keywords().newKeyword("keyword", FeelhubLanguage.reference(), reference);
        final Judgment judgment = TestFactories.judgments().newJudgment(reference, Feeling.good);
        TestFactories.opinions().newOpinion("my opinion", judgment);
        final ClientResource clientResource = restlet.newClientResource("/json/opinions?referenceId=" + reference.getId());

        final TemplateRepresentation representation = (TemplateRepresentation) clientResource.get();

        final JSONArray jsonArray = new JSONArray(representation.getText());
        assertThat(jsonArray.getJSONObject(0).get("languageCode").toString(), is(FeelhubLanguage.reference().getCode()));
    }

    @Test
    public void hasUserIdData() throws IOException, JSONException {
        final Reference reference = TestFactories.references().newReference();
        TestFactories.illustrations().newIllustration(reference, "link");
        TestFactories.keywords().newKeyword("keyword", FeelhubLanguage.reference(), reference);
        final Judgment judgment = TestFactories.judgments().newJudgment(reference, Feeling.good);
        final Opinion opinion = TestFactories.opinions().newOpinion("my opinion", judgment);
        final ClientResource clientResource = restlet.newClientResource("/json/opinions?referenceId=" + reference.getId());

        final TemplateRepresentation representation = (TemplateRepresentation) clientResource.get();

        final JSONArray jsonArray = new JSONArray(representation.getText());
        assertThat(jsonArray.getJSONObject(0).get("userId").toString(), is(opinion.getUserId()));
    }
}
