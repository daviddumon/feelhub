package com.feelhub.domain.thesaurus;

import org.junit.Test;

import static org.hamcrest.MatcherAssert.*;
import static org.hamcrest.Matchers.*;

public class TestsFeelhubLanguage {

    @Test
    public void canGetFrenchLanguage() {
        final FeelhubLanguage french = FeelhubLanguage.forString("French");

        assertThat(french.getCode(), is("fr"));
    }

    @Test
    public void canGetGermanLanguage() {
        final FeelhubLanguage french = FeelhubLanguage.forString("German");

        assertThat(french.getCode(), is("de"));
    }

    @Test
    public void canGetItalianLanguage() {
        final FeelhubLanguage french = FeelhubLanguage.forString("Italian");

        assertThat(french.getCode(), is("it"));
    }

    @Test
    public void canGetPortugueseLanguage() {
        final FeelhubLanguage french = FeelhubLanguage.forString("Portuguese");

        assertThat(french.getCode(), is("pt"));
    }

    @Test
    public void canGetRussianLanguage() {
        final FeelhubLanguage french = FeelhubLanguage.forString("Russian");

        assertThat(french.getCode(), is("ru"));
    }

    @Test
    public void canGetSpanishLanguage() {
        final FeelhubLanguage french = FeelhubLanguage.forString("Spanish");

        assertThat(french.getCode(), is("es"));
    }

    @Test
    public void canGetSwedishLanguage() {
        final FeelhubLanguage french = FeelhubLanguage.forString("Swedish");

        assertThat(french.getCode(), is("sv"));
    }

    @Test
    public void canGetEnglishLanguage() {
        final FeelhubLanguage french = FeelhubLanguage.forString("English");

        assertThat(french.getCode(), is("en"));
    }

    @Test
    public void canLowerCase() {
        final FeelhubLanguage fr = FeelhubLanguage.forString("FR");

        assertThat(fr.getCode(), is("fr"));
    }

    @Test
    public void canTrim() {
        final FeelhubLanguage feelhubLanguage = FeelhubLanguage.forString(" De ");

        assertThat(feelhubLanguage.getCode(), is("de"));
    }

    @Test
    public void justReturnValueIfUnknownLanguage() {
        final FeelhubLanguage unknown = FeelhubLanguage.forString("unknown");

        assertThat(unknown.getCode(), is("unknown"));
    }

    @Test
    public void canGetMicrosoftLanguageCode() {
        final FeelhubLanguage french = FeelhubLanguage.forString("French");

        assertThat(french.getMicrosoftLanguage(), is(com.memetix.mst.language.Language.FRENCH));
    }

    @Test
    public void referenceLanguageIsEnglish() {
        final FeelhubLanguage english = FeelhubLanguage.forString("english");
        final FeelhubLanguage reference = FeelhubLanguage.reference();
        assertThat(english, is(reference));
    }
}
