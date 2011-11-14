package com.steambeat.tools;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.regex.*;

public final class URIs {

    private URIs() {
    }

    public static String decode(String uri) {
        try {
            return URLDecoder.decode(uri, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            return uri;
        }
    }

    public static String extractProtocol(String uriDecoded) {
        return extractFromPattern(uriDecoded, PROTOCOL_PATTERN, "http");
    }

    public static String extractAddress(String uriDecoded) {
        return extractFromPattern(uriDecoded, ADDRESS_PATTERN);
    }

    public static String extractQuery(String uriDecoded) {
        return extractFromPattern(uriDecoded, QUERY_PATTERN);
    }

    public static String extractFragment(String uriDecoded) {
        return extractFromPattern(uriDecoded, FRAGMENT_PATTERN);
    }

    public static String extractFromPattern(String uriDecoded, Pattern pattern) {
        final String defaultValue = "";
        return extractFromPattern(uriDecoded, pattern, defaultValue);
    }

    public static String extractFromPattern(String uriDecoded, Pattern pattern, String defaultValue) {
        final Matcher matcher = pattern.matcher(uriDecoded);
        return matcher.matches() ? matcher.group(matcher.groupCount()) : defaultValue;
    }

    public static final Pattern FRAGMENT_PATTERN = Pattern.compile(".*#(.*)$");
    public static final Pattern ADDRESS_PATTERN = Pattern.compile("(.*://)?([^#?]*).*$");
    public static final Pattern QUERY_PATTERN = Pattern.compile(".*\\?([^#]*).*$");
    public static final Pattern PROTOCOL_PATTERN = Pattern.compile("(.*)://.*");
}
