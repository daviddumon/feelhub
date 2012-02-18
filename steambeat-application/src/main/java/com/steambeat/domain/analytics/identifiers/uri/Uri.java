package com.steambeat.domain.analytics.identifiers.uri;

import com.google.common.base.*;
import com.steambeat.domain.analytics.identifiers.Identifier;

import java.util.Locale;

public class Uri extends Identifier {

    private Uri() {
        protocol = "";
        domain = "";
        address = "";
        query = "";
        fragment = "";
    }

    public Uri(final String uri) {
        final String uriDecoded = URIs.decode(uri).replace(" ", "+");
        protocol = URIs.extractProtocol(uriDecoded).toLowerCase(Locale.US);
        domain = URIs.extractDomain(uriDecoded).toLowerCase(Locale.US);
        address = URIs.extractAddress(uriDecoded);
        query = URIs.extractQuery(uriDecoded);
        fragment = URIs.extractFragment(uriDecoded);
        if (Strings.isNullOrEmpty(query) && Strings.isNullOrEmpty(fragment) && address.endsWith("/")) {
            address = address.substring(0, address.length() - 1);
        }
    }

    @Override
    public String toString() {
        if (isEmpty()) {
            return "";
        }
        final StringBuilder builder = new StringBuilder();
        appendProtocol(builder);
        appendDomain(builder);
        appendAddress(builder);
        appendQuery(builder);
        appendFragment(builder);
        return builder.toString();
    }

    private void appendProtocol(final StringBuilder builder) {
        builder.append(protocol);
        builder.append(PROTOCOL_TOKEN);
    }

    private void appendDomain(final StringBuilder builder) {
        builder.append(domain);
    }

    private void appendAddress(final StringBuilder builder) {
        builder.append(address);
    }

    private void appendQuery(final StringBuilder builder) {
        if (!Strings.isNullOrEmpty(query)) {
            builder.append(QUERY_TOKEN);
            builder.append(query);
        }
    }

    private void appendFragment(final StringBuilder builder) {
        if (!Strings.isNullOrEmpty(fragment)) {
            builder.append(FRAGMENT_TOKEN);
            builder.append(fragment);
        }
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final Uri uri = (Uri) o;
        return Objects.equal(protocol, uri.protocol)
                && Objects.equal(domain, uri.domain)
                && Objects.equal(address, uri.address)
                && Objects.equal(query, uri.query)
                && Objects.equal(fragment, uri.fragment);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(protocol, domain, address, query, fragment);
    }

    public static Uri empty() {
        return new Uri();
    }

    public boolean isEmpty() {
        return Strings.isNullOrEmpty(domain);
    }

    public String condensed() {
        if (isFirstLevelUri() || toString().length() < 40) {
            final StringBuilder builder = new StringBuilder();
            appendDomain(builder);
            appendAddress(builder);
            appendQuery(builder);
            appendFragment(builder);
            return builder.toString();
        } else {
            final String beginningOfUri = getBeginningOfUri();
            return beginningOfUri + CONDENSED_TOKEN;
        }
    }

    private String getBeginningOfUri() {
        final StringBuilder builder = new StringBuilder();
        appendDomain(builder);
        return builder.toString();
    }

    public boolean isFirstLevelUri() {
        return (this.address.isEmpty() || this.address.equals("/"))
                && this.query.isEmpty()
                && this.fragment.isEmpty();
    }

    public String withoutTLD() {
        final String[] tokens = domain.split("(\\.|\\:)");
        return tokens[tokens.length >= 2 ? tokens.length - 2 : 0];
    }

    private final String protocol;
    private final String domain;
    private String address;
    private final String query;
    private final String fragment;
    private static final String PROTOCOL_TOKEN = "://";
    private static final String QUERY_TOKEN = "?";
    private static final String FRAGMENT_TOKEN = "#";
    private static final String CONDENSED_TOKEN = "[...]";
}
