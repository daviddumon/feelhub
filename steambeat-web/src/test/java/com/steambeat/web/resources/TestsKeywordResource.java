package com.steambeat.web.resources;

import com.steambeat.application.KeywordService;
import com.steambeat.domain.keyword.Keyword;
import com.steambeat.domain.thesaurus.Language;
import com.steambeat.web.*;
import com.steambeat.web.representation.SteambeatTemplateRepresentation;
import org.junit.*;
import org.restlet.data.Status;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.*;
import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

public class TestsKeywordResource {

    @Rule
    public WebApplicationTester restlet = new WebApplicationTester();

    @Test
    public void keywordResourceIsMapped() {
        final ClientResource keywordResource = restlet.newClientResource("/topic/keyword");

        keywordResource.get();

        assertThat(keywordResource.getStatus(), is(Status.SUCCESS_OK));
    }

    @Test
    public void keywordResourceIsMappedWithLanguage() {
        final ClientResource keywordResource = restlet.newClientResource("/topic/fr/keyword");

        keywordResource.get();

        assertThat(keywordResource.getStatus(), is(Status.SUCCESS_OK));
    }

    @Test
    public void hasKeywordInDataModel() {
        final ClientResource keywordResource = restlet.newClientResource("/topic/en/cool");

        final SteambeatTemplateRepresentation representation = (SteambeatTemplateRepresentation) keywordResource.get();

        assertTrue(representation.getDataModel().containsKey("keyword"));
        assertThat(new Keyword("cool", Language.forString("en")), is((Keyword) representation.getDataModel().get("keyword")));
    }

    @Test
    public void hasLanguageInDataModel() {
        final ClientResource keywordResource = restlet.newClientResource("/topic/fr/cool");

        final SteambeatTemplateRepresentation representation = (SteambeatTemplateRepresentation) keywordResource.get();

        assertTrue(representation.getDataModel().containsKey("language"));
        assertThat(Language.forString("fr"), is(representation.getDataModel().get("language")));
    }

    @Test
    public void lookUpKeyword() {
        final ClientResource keywordResource = restlet.newClientResource("/topic/de/keyword");

        keywordResource.get();

        final KeywordService keywordService = restlet.getModuleGuiceModule().getKeywordService();
        verify(keywordService).lookUp("keyword", Language.forString("de"));
    }

    @Test
    public void canIdentifyKeywordValueInUri() {
        final ClientResource keywordResource = restlet.newClientResource("/topic/fr/anotherkeyword");

        keywordResource.get();

        final KeywordService keywordService = restlet.getModuleGuiceModule().getKeywordService();
        verify(keywordService).lookUp("anotherkeyword", Language.forString("fr"));
    }
}
