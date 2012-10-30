package com.feelhub.domain.uri;

import com.google.common.base.Strings;
import com.google.common.collect.Lists;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.*;
import java.util.regex.*;

public class UriTokenizer {

    public List<String> getTokensFor(final String uri) {
        final String uriDecoded = decode(uri);
        extractElementsOfUri(uriDecoded);
        final List<String> result = Lists.newArrayList();
        result.add(getCompleteAddress());
        result.add(getAddressWithoutProtocol());
        return result;
    }

    private void extractElementsOfUri(final String uri) {
        protocol = extractProtocol(uri).toLowerCase(Locale.US);
        domain = extractDomain(uri).toLowerCase(Locale.US);
        address = extractAddress(uri);
        query = extractQuery(uri);
        fragment = extractFragment(uri);
    }

    private String extractProtocol(final String uri) {
        return extractFromPattern(uri, PROTOCOL_PATTERN);
    }

    private String extractDomain(final String uri) {
        return extractFromPattern(uri, DOMAIN_PATTERN);
    }

    private String extractAddress(final String uri) {
        return extractFromPattern(uri, ADDRESS_PATTERN);
    }

    private String extractQuery(final String uri) {
        return extractFromPattern(uri, QUERY_PATTERN);
    }

    private String extractFragment(final String uri) {
        return extractFromPattern(uri, FRAGMENT_PATTERN);
    }

    private String extractFromPattern(final String uri, final Pattern pattern) {
        final Matcher matcher = pattern.matcher(uri);
        return matcher.matches() ? matcher.group(matcher.groupCount()) : "";
    }

    private String getCompleteAddress() {
        final StringBuilder stringBuilder = new StringBuilder();
        appendProtocol(stringBuilder);
        stringBuilder.append(getAddressWithoutProtocol());
        return stringBuilder.toString();
    }

    public String getAddressWithoutProtocol() {
        final StringBuilder stringBuilder = new StringBuilder();
        appendDomain(stringBuilder);
        appendAddress(stringBuilder);
        appendQuery(stringBuilder);
        appendFragment(stringBuilder);
        return stringBuilder.toString();
    }

    private void appendProtocol(final StringBuilder builder) {
        if (!Strings.isNullOrEmpty(protocol)) {
            builder.append(protocol);
        }
    }

    private void appendDomain(final StringBuilder builder) {
        if (!Strings.isNullOrEmpty(domain)) {
            builder.append(domain);
        }
    }

    private void appendAddress(final StringBuilder builder) {
        if (!Strings.isNullOrEmpty(address)) {
            builder.append(address);
        }
    }

    private void appendQuery(final StringBuilder builder) {
        if (!Strings.isNullOrEmpty(query)) {
            builder.append(query);
        }
    }

    private void appendFragment(final StringBuilder builder) {
        if (!Strings.isNullOrEmpty(fragment)) {
            builder.append(fragment);
        }
    }

    public String decode(final String uri) {
        try {
            return URLDecoder.decode(uri, "UTF-8").replace(" ", "+");
        } catch (UnsupportedEncodingException e) {
            return uri;
        }
    }

    private String protocol;
    private String domain;
    private String address;
    private String query;
    private String fragment;

    private static final Pattern PROTOCOL_PATTERN = Pattern.compile("(.*://).*");
    private static final Pattern DOMAIN_PATTERN = Pattern.compile("(.*://)?([^/#?]*).*$");
    private static final Pattern ADDRESS_PATTERN = Pattern.compile("(.*://)?[^/#?]*([^#?]*).*$");
    private static final Pattern QUERY_PATTERN = Pattern.compile(".*(\\?[^#]*).*$");
    private static final Pattern FRAGMENT_PATTERN = Pattern.compile(".*(#.*)$");
}
