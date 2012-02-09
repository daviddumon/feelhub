package com.steambeat.tools;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public final class URIs {

    private URIs() {
    }

    public static String decode(final String uri) {
        try {
            return URLDecoder.decode(uri, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            return uri;
        }
    }

    public static String extractProtocol(final String uriDecoded) {
        return extractFromPattern(uriDecoded, PROTOCOL_PATTERN, "http");
    }

    public static String extractDomain(final String uriDecoded) {
        return extractFromPattern(uriDecoded, DOMAIN_PATTERN);
    }

    public static String extractAddress(final String uriDecoded) {
        return extractFromPattern(uriDecoded, ADDRESS_PATTERN);
    }

    public static String extractQuery(final String uriDecoded) {
        return extractFromPattern(uriDecoded, QUERY_PATTERN);
    }

    public static String extractFragment(final String uriDecoded) {
        return extractFromPattern(uriDecoded, FRAGMENT_PATTERN);
    }

    public static String extractFromPattern(final String uriDecoded, final Pattern pattern) {
        final String defaultValue = "";
        return extractFromPattern(uriDecoded, pattern, defaultValue);
    }

    public static String extractFromPattern(final String uriDecoded, final Pattern pattern, final String defaultValue) {
        final Matcher matcher = pattern.matcher(uriDecoded);
        return matcher.matches() ? matcher.group(matcher.groupCount()) : defaultValue;
    }

    public static final Pattern PROTOCOL_PATTERN = Pattern.compile("(.*)://.*");
    public static final Pattern DOMAIN_PATTERN = Pattern.compile("(.*://)?([^/#?]*).*$");
    public static final Pattern ADDRESS_PATTERN = Pattern.compile("(.*://)?[^/#?]*([^#?]*).*$");
    public static final Pattern QUERY_PATTERN = Pattern.compile(".*\\?([^#]*).*$");
    public static final Pattern FRAGMENT_PATTERN = Pattern.compile(".*#(.*)$");
}
