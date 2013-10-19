package com.feelhub.domain.topic.http.uri;

import org.junit.Test;

import static org.fest.assertions.Assertions.*;

public class UriTest {

    @Test
    public void canCreateAnUri() {
        final String value = "http://www.google.fr";

        final Uri uri = new Uri(value);

        assertThat(uri.getValue()).isEqualTo(value);
    }

    @Test
    public void overrideToString() {
        final String value = "http://www.google.fr";

        final Uri uri = new Uri(value);

        assertThat(uri.toString()).isEqualTo(value);
    }

    @Test
    public void canCompare() {
        final Uri uri1 = new Uri("http://www.google.fr");
        final Uri uri2 = new Uri("http://www.GOOGLE.fr");
        final Uri uri3 = new Uri("http://www.feelhub.fr");

        assertThat(uri1).isEqualTo(uri2);
        assertThat(uri1).isNotEqualTo(uri3);
    }

    @Test
    public void canExtractProtocolHttp() {
        final String value = "http://www.google.fr";

        final Uri uri = new Uri(value);

        assertThat(uri.getCorrectProtocol()).isEqualTo("http://");
    }

    @Test
    public void canExtractProtocolHttps() {
        final String value = "https://www.google.fr";

        final Uri uri = new Uri(value);

        assertThat(uri.getCorrectProtocol()).isEqualTo("https://");
    }

    @Test
    public void canExtractDomain() {
        final String value = "http://www.google.fr";

        final Uri uri = new Uri(value);

        assertThat(uri.getDomain()).isEqualTo("www.google.fr");
    }

    @Test
    public void canExtractAddress() {
        final String value = "http://www.google.fr/address";

        final Uri uri = new Uri(value);

        assertThat(uri.getAddress()).isEqualTo("/address");
    }

    @Test
    public void canExtractQuery() {
        final String value = "http://www.google.fr/address?query";

        final Uri uri = new Uri(value);

        assertThat(uri.getQuery()).isEqualTo("?query");
    }

    @Test
    public void canExtractFragment() {
        final String value = "http://www.google.fr/address?query#fragment";

        final Uri uri = new Uri(value);

        assertThat(uri.getFragment()).isEqualTo("#fragment");
    }

    @Test
    public void baseProtocolIsHttp() {
        final String value = "google.fr";

        final Uri uri = new Uri(value);

        assertThat(uri.getCorrectProtocol()).isEqualTo("http://");
    }

    @Test
    public void alwaysAppendProtocol() {
        final String value = "google.fr";

        final Uri uri = new Uri(value);

        assertThat(uri.getValue()).isEqualTo("http://" + value);
    }

    @Test
    public void onlyAppendProtocolIfNotPresent() {
        final String value = "http://google.fr";

        final Uri uri = new Uri(value);

        assertThat(uri.getValue()).isEqualTo(value);
    }

    @Test
    public void alwaysDecode() {
        final String value = "https%3A%2F%2Fgoogle.fr";

        final Uri uri = new Uri(value);

        assertThat(uri.getValue()).isEqualTo("https://google.fr");
    }

    @Test
    public void onlyLowerCaseProtocolAndDomain() {
        final String value = "hTTp://GOOGLE.fr/adress?Q=Yeah#12A";

        final Uri uri = new Uri(value);

        assertThat(uri.getValue()).isEqualTo("http://google.fr/adress?Q=Yeah#12A");
    }

    @Test
    public void canGetValueWithoutProtocol() {
        final String value = "hTTp://GOOGLE.fr/adress?Q=Yeah#12A";

        final Uri uri = new Uri(value);

        assertThat(uri.getValueWithoutProtocol()).isEqualTo("google.fr/adress?Q=Yeah#12A");
    }

    @Test
    public void canGetAllVariations() {
        final String value = "http://google.fr/address";

        final Uri uri = new Uri(value);

        assertThat(uri.getVariations().size()).isEqualTo(4);
        assertThat(uri.getVariations().get(0)).isEqualTo(uri.getValue());
        assertThat(uri.getVariations().get(1)).isEqualTo(uri.getValueWithoutProtocol());
        assertThat(uri.getVariations().get(2)).isEqualTo(uri.getValueWithEndingSlash());
        assertThat(uri.getVariations().get(3)).isEqualTo(uri.getValueWithoutProtocolWithEndingSlash());
    }

    @Test
    public void alwaysRemoveEndingSlash() {
        final String value = "http://google.fr/address/";

        final Uri uri = new Uri(value);

        assertThat(uri.getValue()).isEqualTo(value.substring(0, value.length() - 1));
    }

    @Test
    public void canGetValueWithEndingSlash() {
        final String value = "http://google.fr/address";

        final Uri uri = new Uri(value);

        assertThat(uri.getValueWithEndingSlash()).isEqualTo("http://google.fr/address/");
    }
}
