package com.steambeat.domain.thesaurus;

import org.junit.Test;

import static org.hamcrest.MatcherAssert.*;
import static org.hamcrest.Matchers.*;

public class TestsLanguage {

    @Test
    public void canGetFrenchLanguage() {
        final Language french = Language.forString("French");

        assertThat(french.getCode(), is("fr"));
    }

    @Test
    public void canGetGermanLanguage() {
        final Language french = Language.forString("German");

        assertThat(french.getCode(), is("de"));
    }

    @Test
    public void canGetItalianLanguage() {
        final Language french = Language.forString("Italian");

        assertThat(french.getCode(), is("it"));
    }

    @Test
    public void canGetPortugueseLanguage() {
        final Language french = Language.forString("Portuguese");

        assertThat(french.getCode(), is("pt"));
    }

    @Test
    public void canGetRussianLanguage() {
        final Language french = Language.forString("Russian");

        assertThat(french.getCode(), is("ru"));
    }

    @Test
    public void canGetSpanishLanguage() {
        final Language french = Language.forString("Spanish");

        assertThat(french.getCode(), is("es"));
    }

    @Test
    public void canGetSwedishLanguage() {
        final Language french = Language.forString("Swedish");

        assertThat(french.getCode(), is("sv"));
    }

    @Test
    public void canGetEnglishLanguage() {
        final Language french = Language.forString("English");

        assertThat(french.getCode(), is("en"));
    }

    @Test
    public void canLowerCase() {
        final Language fr = Language.forString("FR");

        assertThat(fr.getCode(), is("fr"));
    }

    @Test
    public void canTrim() {
        final Language language = Language.forString(" De ");

        assertThat(language.getCode(), is("de"));
    }

    @Test
    public void justReturnValueIfUnknownLanguage() {
        final Language unknown = Language.forString("unknown");

        assertThat(unknown.getCode(), is("unknown"));
    }

    @Test
    public void canGetMicrosoftLanguageCode() {
        final Language french = Language.forString("French");

        assertThat(french.getMicrosoftLanguage(), is(com.memetix.mst.language.Language.FRENCH));
    }

    @Test
    public void referenceLanguageIsEnglish() {
        final Language english = Language.forString("english");
        final Language reference = Language.reference();
        assertThat(english, is(reference));
    }
}
