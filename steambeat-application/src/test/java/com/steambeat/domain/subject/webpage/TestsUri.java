package com.steambeat.domain.subject.webpage;

import org.junit.Test;

import static org.hamcrest.Matchers.*;
import static org.junit.Assert.*;

public class TestsUri {

    @Test
    public void canIsolateDomainFromAddress() {
        testUri("http://www.lemonde.fr/myaddress/1/29?q#frag");
        testUri("http://lemonde.fr/myaddress/1/29?q#frag");
        testUri("lemonde.fr/myaddress/1/29?q#frag", "http://lemonde.fr/myaddress/1/29?q#frag");
        testUri("www.lemonde.fr/myaddress/1/29?q#frag", "http://www.lemonde.fr/myaddress/1/29?q#frag");
        testUri("http://www.lemonde.fr");
        testUri("http://lemonde.fr");
        testUri("lemonde.fr", "http://lemonde.fr");
        testUri("www.lemonde.fr", "http://www.lemonde.fr");
        testUri("http://www.lemonde.fr?q");
    }

    @Test
    public void canGetEncodedString() {
        final Uri uri = new Uri("http://www.mongodb.org/display/docs/java+language+center");

        assertThat(uri.toString(), is("http://www.mongodb.org/display/docs/java+language+center"));
    }

    @Test
    public void canConvertToString() {
        testUri("http://pute");
    }

    @Test
    public void canDecodeAddress() {
        testUri("http%3A%2F%2Fpute", "http://pute");
    }

    @Test
    public void canPrefixWithProtocol() {
        testUri("pute", "http://pute");
    }

    @Test
    public void removeTrailingSlash() {
        testUri("pute/", "http://pute");
    }

    @Test
    public void canCompare() {
        final Uri uri1 = new Uri("test.com");
        final Uri uri2 = new Uri("test.com");

        assertThat(uri1, is(uri2));
    }

    @Test
    public void canCompareLargerUri() {
        final Uri uri1 = new Uri("test.com/?toto#tata");
        final Uri uri2 = new Uri("test.com");

        assertThat(uri1, not(equalTo(uri2)));
    }

    @Test
    public void hashCodeIsDifferent() {
        final Uri uri1 = new Uri("test.com/?toto#tata");
        final Uri uri2 = new Uri("test.com");

        assertThat(uri1.hashCode(), not(equalTo(uri2.hashCode())));
    }

    @Test
    public void canCreateEmptyUri() {
        final Uri uri = Uri.empty();

        assertThat(uri, notNullValue());
        assertThat(uri.isEmpty(), is(true));
        assertThat(uri.toString(), is(""));
    }

    @Test
    public void anUriWithJustADomainIsNotEmpty() {
        final Uri uri = new Uri("http://www.lemonde.fr");

        assertThat(uri.isEmpty(), is(false));
    }

    @Test
    public void canDealWithHttps() {
        final Uri uri = new Uri("https://lemonde.fr");

        assertThat(uri.toString(), startsWith("https://"));
    }

    @Test
    public void canLowercaseDomain() {
        testUri("http://wwW.youtube.com/watch?abCdEf", "http://www.youtube.com/watch?abCdEf");
    }

    @Test
    public void dontLowerAddressAfterDomain() {
        testUri("http://www.youtube.com/Watch?ab", "http://www.youtube.com/Watch?ab");
    }

    @Test
    public void canLowerWithoutQuery() {
        testUri("http://wwW.YOUTube.com", "http://www.youtube.com");
    }

    @Test
    public void dontLowerAfterAnchor() {
        testUri("http://www.youtube.com/#p/u/isruMMM");
    }

    @Test
    public void canDealWithCompleteUri() {
        testUri("http://www.youtube.com/?uieunrstUI#p/u/isruMMM");
    }

    @Test
    public void canDealWithUppercaseProtocol() {
        testUri("HTTPS://yourmom", "https://yourmom");
    }

    @Test
    public void canHaveQueryOrFragmentWithoutAddress() {
        testUri("http://test.com#p/u/2/xsJ0u7MIxLM");
    }

    @Test
    public void canGetCondensedVersionForDomain() {
        final String address = "http://www.google.fr";
        final Uri uri = new Uri(address);

        assertThat(uri.condensed(), is("www.google.fr"));
    }

    @Test
    public void canGetCondensedVersionForDomainAndShortAddressQueryFragment() {
        final String address = "http://www.google.fr/address?a#1";
        final Uri uri = new Uri(address);

        assertThat(uri.condensed(), is("www.google.fr/address?a#1"));
    }

    @Test
    public void canGetCondensedVersionForDomainAndLongAddress() {
        final String address = "http://www.google.fr/this/is/a/real/too/long/address?damn#right";
        final Uri uri = new Uri(address);

        assertThat(uri.condensed(), is("www.google.fr [...] damn#right"));
    }

    @Test
    public void checkFirstLevelUri() {
        final Uri uri1 = new Uri("http://www.google.fr");
        final Uri uri2 = new Uri("http://www.google.fr/");
        final Uri uri3 = new Uri("http://www.google.fr/?query#fragment");
        final Uri uri4 = new Uri("http://www.google.fr/a");

        assertThat(uri1.isFirstLevelUri(), is(true));
        assertThat(uri2.isFirstLevelUri(), is(true));
        assertThat(uri3.isFirstLevelUri(), is(false));
        assertThat(uri4.isFirstLevelUri(), is(false));
    }

    @Test
    public void canGetDomainWithoutTLD() {
        final String address = "http://www.google.fr/this/is/a/real/too/long/address?damn#right";
        final Uri uri = new Uri(address);

        assertThat(uri.withoutTLD(), is("google"));
    }

    @Test
    public void canGetDomainWithoutTLDFromUriWithoutSubDomain() {
        final String address = "http://google.fr/this/is/a/real/too/long/address?damn#right";
        final Uri uri = new Uri(address);

        assertThat(uri.withoutTLD(), is("google"));
    }

    private void testUri(final String address) {
        testUri(address, address);
    }

    private void testUri(final String address, final String expected) {
        final Uri uri = new Uri(address);

        assertThat(uri.toString(), is(expected));
    }
}
