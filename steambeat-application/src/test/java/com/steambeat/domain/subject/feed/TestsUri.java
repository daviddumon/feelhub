package com.steambeat.domain.subject.feed;

import org.junit.Test;

import static org.hamcrest.Matchers.*;
import static org.junit.Assert.*;

public class TestsUri {

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
        final Uri uri1 = new Uri("test.com?toto#tata");
        final Uri uri2 = new Uri("test.com");

        assertThat(uri1, not(equalTo(uri2)));
    }

    @Test
    public void hashCodeIsDifferent() {
        final Uri uri1 = new Uri("test.com?toto#tata");
        final Uri uri2 = new Uri("test.com");

        assertThat(uri1.hashCode(), not(equalTo(uri2.hashCode())));
    }

    @Test
    public void canCreateEmptyUri() {
        final Uri uri = Uri.empty();

        assertThat(uri, notNullValue());
        assertThat(uri.isEmpty(), is(true));
    }

    @Test
    public void canDealWithHttps() {
        final Uri uri = new Uri("https://lomonde.fr");

        assertThat(uri.toString(), startsWith("https://"));
    }

    @Test
    public void canLowercaseBeforQuery() {
        testUri("http://wwW.youtube.com/watch?abCdEf", "http://www.youtube.com/watch?abCdEf");
    }

    @Test
    public void canLowerWithoutQuery() {

        testUri("http://wwW.YOUTube.com", "http://www.youtube.com");
    }

    @Test
    public void dontLowerAfterAnchor() {
        testUri("http://www.youtube.com#p/u/isruMMM");
    }

    @Test
    public void canDealWithCompleteUri() {
        testUri("http://www.youtube.com?uieunrstUI#p/u/isruMMM");
    }

    @Test
    public void canDealWithUppercaseProtocol() {
        testUri("HTTPS://yourmom", "https://yourmom");
    }

    private void testUri(String address) {
        testUri(address, address);
    }

    private void testUri(String address, String expected) {
        final Uri uri = new Uri(address);

        assertThat(uri.toString(), is(expected));
    }
}
