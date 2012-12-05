package com.feelhub.domain.thesaurus;

import org.junit.Test;

import java.util.List;

import static org.fest.assertions.Assertions.*;


public class TestsFeelhubLanguage {

    @Test
    public void canGetFrenchLanguage() {
        final FeelhubLanguage french = FeelhubLanguage.fromCountryName("French");

        assertThat(french.getCode()).isEqualTo("fr");
    }

    @Test
    public void canGetGermanLanguage() {
        final FeelhubLanguage french = FeelhubLanguage.fromCountryName("German");

        assertThat(french.getCode()).isEqualTo("de");
    }

    @Test
    public void canGetItalianLanguage() {
        final FeelhubLanguage french = FeelhubLanguage.fromCountryName("Italian");

        assertThat(french.getCode()).isEqualTo("it");
    }

    @Test
    public void canGetPortugueseLanguage() {
        final FeelhubLanguage french = FeelhubLanguage.fromCountryName("Portuguese");

        assertThat(french.getCode()).isEqualTo("pt");
    }

    @Test
    public void canGetRussianLanguage() {
        final FeelhubLanguage french = FeelhubLanguage.fromCountryName("Russian");

        assertThat(french.getCode()).isEqualTo("ru");
    }

    @Test
    public void canGetSpanishLanguage() {
        final FeelhubLanguage french = FeelhubLanguage.fromCountryName("Spanish");

        assertThat(french.getCode()).isEqualTo("es");
    }

    @Test
    public void canGetSwedishLanguage() {
        final FeelhubLanguage french = FeelhubLanguage.fromCountryName("Swedish");

        assertThat(french.getCode()).isEqualTo("sv");
    }

    @Test
    public void canGetEnglishLanguage() {
        final FeelhubLanguage french = FeelhubLanguage.fromCountryName("English");

        assertThat(french.getCode()).isEqualTo("en");
    }

    @Test
    public void canLowerCase() {
        final FeelhubLanguage fr = FeelhubLanguage.fromCountryName("FR");

        assertThat(fr.getCode()).isEqualTo("fr");
    }

    @Test
    public void canTrim() {
        final FeelhubLanguage feelhubLanguage = FeelhubLanguage.fromCountryName(" De ");

        assertThat(feelhubLanguage.getCode()).isEqualTo("de");
    }

    @Test
    public void returnsNoneOnUnknownLanguage() {
        final FeelhubLanguage unknown = FeelhubLanguage.fromCountryName("unknown");

        assertThat(unknown).isEqualTo(FeelhubLanguage.none());
    }

    @Test
    public void canGetMicrosoftLanguageCode() {
        final FeelhubLanguage french = FeelhubLanguage.fromCountryName("French");

        assertThat(french.getMicrosoftLanguage()).isEqualTo(com.memetix.mst.language.Language.FRENCH);
    }

    @Test
    public void referenceLanguageIsEnglish() {
        final FeelhubLanguage english = FeelhubLanguage.fromCountryName("english");
        final FeelhubLanguage reference = FeelhubLanguage.reference();
        assertThat(english).isEqualTo(reference);
    }

    @Test
    public void canGetName() {
        assertThat(FeelhubLanguage.reference().getName()).isEqualTo("English");
    }

    @Test
    public void canGetAvailableLanguages() {
        final List<FeelhubLanguage> languages = FeelhubLanguage.availables();

        assertThat(languages).isNotEmpty();
        assertThat(languages.get(0).getName()).isEqualTo("Albanian");
    }
}
