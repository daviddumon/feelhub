package com.steambeat.domain.subject.feed;

import com.google.common.base.*;
import com.steambeat.tools.URIs;

import java.util.Locale;

public class Uri {

    private Uri() {

    }

    public Uri(final String uri) {
        final String uriDecoded = URIs.decode(uri);
        protocol = URIs.extractProtocol(uriDecoded).toLowerCase(Locale.US);
        address = URIs.extractAddress(uriDecoded).toLowerCase(Locale.US);
        query = URIs.extractQuery(uriDecoded);
        fragment = URIs.extractFragment(uriDecoded);
        if (address.endsWith("/")) {
            address = address.substring(0, address.length() - 1);
        }
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append(protocol);
        builder.append(PROTOCOL_TOKEN);
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
        return Objects.equal(address, uri.address)
                && Objects.equal(protocol, uri.protocol)
                && Objects.equal(query, uri.query)
                && Objects.equal(fragment, uri.fragment);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(address, protocol, query, fragment);
    }

    public static Uri empty() {
        return new Uri();
    }

    public boolean isEmpty() {
        return Strings.isNullOrEmpty(address);
    }

    private String address;
    private String protocol;
    private String query;
    private String fragment;
    private static final String QUERY_TOKEN = "?";
    private static final String FRAGMENT_TOKEN = "#";
    private static final String PROTOCOL_TOKEN = "://";
}
