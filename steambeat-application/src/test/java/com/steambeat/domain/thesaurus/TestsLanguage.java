package com.steambeat.domain.thesaurus;

import org.junit.Test;

import static org.hamcrest.MatcherAssert.*;
import static org.hamcrest.Matchers.*;

public class TestsLanguage {

    @Test
    public void canGetFrenchLanguage() {
        final SteambeatLanguage french = SteambeatLanguage.forString("French");

        assertThat(french.getCode(), is("fr"));
    }

    @Test
    public void canGetGermanLanguage() {
        final SteambeatLanguage french = SteambeatLanguage.forString("German");

        assertThat(french.getCode(), is("de"));
    }

    @Test
    public void canGetItalianLanguage() {
        final SteambeatLanguage french = SteambeatLanguage.forString("Italian");

        assertThat(french.getCode(), is("it"));
    }

    @Test
    public void canGetPortugueseLanguage() {
        final SteambeatLanguage french = SteambeatLanguage.forString("Portuguese");

        assertThat(french.getCode(), is("pt"));
    }

    @Test
    public void canGetRussianLanguage() {
        final SteambeatLanguage french = SteambeatLanguage.forString("Russian");

        assertThat(french.getCode(), is("ru"));
    }

    @Test
    public void canGetSpanishLanguage() {
        final SteambeatLanguage french = SteambeatLanguage.forString("Spanish");

        assertThat(french.getCode(), is("es"));
    }

    @Test
    public void canGetSwedishLanguage() {
        final SteambeatLanguage french = SteambeatLanguage.forString("Swedish");

        assertThat(french.getCode(), is("sv"));
    }

    @Test
    public void canGetEnglishLanguage() {
        final SteambeatLanguage french = SteambeatLanguage.forString("English");

        assertThat(french.getCode(), is("en"));
    }

    @Test
    public void canLowerCase() {
        final SteambeatLanguage fr = SteambeatLanguage.forString("FR");

        assertThat(fr.getCode(), is("fr"));
    }

    @Test
    public void canTrim() {
        final SteambeatLanguage steambeatLanguage = SteambeatLanguage.forString(" De ");

        assertThat(steambeatLanguage.getCode(), is("de"));
    }

    @Test
    public void justReturnValueIfUnknownLanguage() {
        final SteambeatLanguage unknown = SteambeatLanguage.forString("unknown");

        assertThat(unknown.getCode(), is("unknown"));
    }

    @Test
    public void canGetMicrosoftLanguageCode() {
        final SteambeatLanguage french = SteambeatLanguage.forString("French");

        assertThat(french.getMicrosoftLanguage(), is(com.memetix.mst.language.Language.FRENCH));
    }

    @Test
    public void referenceLanguageIsEnglish() {
        final SteambeatLanguage english = SteambeatLanguage.forString("english");
        final SteambeatLanguage reference = SteambeatLanguage.reference();
        assertThat(english, is(reference));
    }
}
