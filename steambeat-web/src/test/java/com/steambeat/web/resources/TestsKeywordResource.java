package com.steambeat.web.resources;

import com.steambeat.application.KeywordService;
import com.steambeat.domain.keyword.Keyword;
import com.steambeat.domain.thesaurus.SteambeatLanguage;
import com.steambeat.repositories.fakeRepositories.WithFakeRepositories;
import com.steambeat.test.TestFactories;
import com.steambeat.web.*;
import com.steambeat.web.representation.SteambeatTemplateRepresentation;
import org.junit.*;
import org.restlet.data.Status;

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
        TestFactories.keywords().newKeyword("keyword", SteambeatLanguage.none());
        final ClientResource keywordResource = restlet.newClientResource("/topic/keyword");

        keywordResource.get();

        assertThat(keywordResource.getStatus(), is(Status.SUCCESS_OK));
    }

    @Test
    public void keywordResourceIsMappedWithLanguage() {
        TestFactories.keywords().newKeyword("keyword", SteambeatLanguage.forString("fr"));
        final ClientResource keywordResource = restlet.newClientResource("/topic/fr/keyword");

        keywordResource.get();

        assertThat(keywordResource.getStatus(), is(Status.SUCCESS_OK));
    }

    @Test
    public void hasKeywordInDataModel() {
        final ClientResource keywordResource = restlet.newClientResource("/topic/en/cool");

        final SteambeatTemplateRepresentation representation = (SteambeatTemplateRepresentation) keywordResource.get();

        assertTrue(representation.getDataModel().containsKey("keyword"));
        assertThat(representation.getDataModel().get("keyword"), notNullValue());
    }

    @Test
    public void hasLanguageInDataModel() {
        final ClientResource keywordResource = restlet.newClientResource("/topic/fr/cool");

        final SteambeatTemplateRepresentation representation = (SteambeatTemplateRepresentation) keywordResource.get();

        assertTrue(representation.getDataModel().containsKey("language"));
        assertThat(SteambeatLanguage.forString("fr"), is(representation.getDataModel().get("language")));
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

        final SteambeatTemplateRepresentation representation = (SteambeatTemplateRepresentation) keywordResource.get();

        assertTrue(representation.getDataModel().containsKey("keyword"));
        final Keyword keyword = (Keyword) representation.getDataModel().get("keyword");
        assertThat(keyword, notNullValue());
        assertThat(keyword.getReferenceId(), nullValue());
        assertThat(keywordResource.getStatus(), is(Status.CLIENT_ERROR_NOT_FOUND));
    }
}
