package com.steambeat.domain.subject.webpage;

import com.google.common.base.*;
import com.steambeat.tools.URIs;

import java.util.Locale;

public class Uri {

    private Uri() {

    }

    public Uri(final String uri) {
        final String uriDecoded = URIs.decode(uri);
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
        final StringBuilder builder = new StringBuilder();
        builder.append(protocol);
        builder.append(PROTOCOL_TOKEN);
        builder.append(domain);
        builder.append(address);
        if (!Strings.isNullOrEmpty(query)) {
            builder.append(QUERY_TOKEN);
            builder.append(query);
        }
        if (!Strings.isNullOrEmpty(fragment)) {
            builder.append(FRAGMENT_TOKEN);
            builder.append(fragment);
        }
        return builder.toString();
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

    private String protocol;
    private String domain;
    private String address;
    private String query;
    private String fragment;
    private static final String PROTOCOL_TOKEN = "://";
    private static final String QUERY_TOKEN = "?";
    private static final String FRAGMENT_TOKEN = "#";
}
