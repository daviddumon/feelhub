package com.steambeat.web.resources;

import com.steambeat.application.KeywordService;
import com.steambeat.domain.illustration.Illustration;
import com.steambeat.domain.keyword.Keyword;
import com.steambeat.domain.reference.Reference;
import com.steambeat.domain.thesaurus.SteambeatLanguage;
import com.steambeat.repositories.Repositories;
import com.steambeat.repositories.fakeRepositories.WithFakeRepositories;
import com.steambeat.test.TestFactories;
import com.steambeat.web.ClientResource;
import com.steambeat.web.WebApplicationTester;
import com.steambeat.web.dto.ReferenceData;
import org.junit.Rule;
import org.junit.Test;
import org.restlet.data.Status;
import org.restlet.ext.freemarker.TemplateRepresentation;

import java.util.Map;

import static org.hamcrest.Matchers.*;
import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

public class TestsKeywordResource {

    @Rule
    public WithFakeRepositories repositories = new WithFakeRepositories();

    @Rule
    public WebApplicationTester restlet = new WebApplicationTester();

    @Test
    public void keywordResourceIsMapped() {
        TestFactories.keywords().newKeyword("Keyword", SteambeatLanguage.none());
        final ClientResource keywordResource = restlet.newClientResource("/topic/keyword");

        keywordResource.get();

        assertThat(keywordResource.getStatus(), is(Status.SUCCESS_OK));
    }

    @Test
    public void keywordResourceIsMappedWithLanguage() {
        TestFactories.keywords().newKeyword("Keyword", SteambeatLanguage.forString("fr"));
        final ClientResource keywordResource = restlet.newClientResource("/topic/fr/keyword");

        keywordResource.get();

        assertThat(keywordResource.getStatus(), is(Status.SUCCESS_OK));
    }

    @Test
    public void hasReferenceDataInDataModel() {
        final ClientResource keywordResource = restlet.newClientResource("/topic/en/cool");

        final TemplateRepresentation representation = (TemplateRepresentation) keywordResource.get();

		final Map<String, Object> dataModel = (Map<String, Object>) representation.getDataModel();
		assertTrue(dataModel.containsKey("referenceData"));
        assertThat(dataModel.get("referenceData"), notNullValue());
    }

    @Test
    public void lookUpKeyword() {
        TestFactories.keywords().newKeyword("keyword", SteambeatLanguage.forString("de"));
        final ClientResource keywordResource = restlet.newClientResource("/topic/de/keyword");

        keywordResource.get();

        final KeywordService keywordService = restlet.getModuleGuiceTestModule().getKeywordService();
        verify(keywordService).lookUp("keyword", SteambeatLanguage.forString("de"));
    }

    @Test
    public void lookUpWithNoLanguage() {
        TestFactories.keywords().newKeyword("keyword", SteambeatLanguage.none());
        final ClientResource keywordResource = restlet.newClientResource("/topic/keyword");

        keywordResource.get();

        final KeywordService keywordService = restlet.getModuleGuiceTestModule().getKeywordService();
        verify(keywordService).lookUp("keyword", SteambeatLanguage.none());
    }

    @Test
    public void canIdentifyKeywordValueInUri() {
        final ClientResource keywordResource = restlet.newClientResource("/topic/fr/anotherkeyword");

        keywordResource.get();

        final KeywordService keywordService = restlet.getModuleGuiceTestModule().getKeywordService();
        verify(keywordService).lookUp("anotherkeyword", SteambeatLanguage.forString("fr"));
    }

    @Test
    public void createFakeKeywordIfItDoesNotExist() {
        final ClientResource keywordResource = restlet.newClientResource("/topic/es/keyword");

        final TemplateRepresentation representation = (TemplateRepresentation) keywordResource.get();

		Map<String, Object> dataModel = (Map<String, Object>) representation.getDataModel();
        assertTrue(dataModel.containsKey("referenceData"));
        final ReferenceData referenceData = (ReferenceData) dataModel.get("referenceData");
        assertThat(referenceData, notNullValue());
        assertThat(referenceData.getReferenceId(), is(""));
        assertThat(keywordResource.getStatus(), is(Status.CLIENT_ERROR_NOT_FOUND));
        assertThat(Repositories.keywords().getAll().size(), is(0));
    }

    @Test
    public void referenceDataWithGoodValuesForExistingKeywordAndIllustration() {
        final Reference reference = TestFactories.references().newReference();
        final Keyword keyword = TestFactories.keywords().newKeyword("Keyword", SteambeatLanguage.forString("es"), reference);
        final Illustration illustration = TestFactories.illustrations().newIllustration(reference, "link");
        final ClientResource keywordResource = restlet.newClientResource("/topic/es/keyword");

        final TemplateRepresentation representation = (TemplateRepresentation) keywordResource.get();

		Map<String, Object> dataModel = (Map<String, Object>) representation.getDataModel();
        assertTrue(dataModel.containsKey("referenceData"));
        final ReferenceData referenceData = (ReferenceData) dataModel.get("referenceData");
        assertThat(referenceData.getIllustrationLink(), is(illustration.getLink()));
        assertThat(referenceData.getKeywordValue(), is(keyword.getValue()));
        assertThat(referenceData.getLanguageCode(), is(keyword.getLanguageCode()));
        assertThat(referenceData.getReferenceId(), is(reference.getId().toString()));
    }
}
