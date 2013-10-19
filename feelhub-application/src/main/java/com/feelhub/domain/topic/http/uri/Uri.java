package com.feelhub.domain.topic.http.uri;

import com.google.common.base.Objects;
import com.google.common.collect.Lists;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.*;
import java.util.regex.*;

public class Uri {

    //mongolink
    protected Uri() {
    }

    public Uri(final String value) {
        this.value = sanitize(value);
    }

    private String sanitize(final String value) {
        if (value.substring(value.length() - 1, value.length()).equals("/")) {
            return decode(value.substring(0, value.length() - 1));
        }
        return decode(value);
    }

    private String decode(final String uri) {
        try {
            return URLDecoder.decode(uri, "UTF-8").replace(" ", "+");
        } catch (UnsupportedEncodingException e) {
            return uri;
        }
    }

    public String getValue() {
        final StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(getCorrectProtocol());
        stringBuilder.append(getDomain());
        stringBuilder.append(getAddress());
        stringBuilder.append(getQuery());
        stringBuilder.append(getFragment());
        return stringBuilder.toString();
    }

    public List<String> getVariations() {
        final ArrayList<String> variations = Lists.newArrayList();
        variations.add(getValue());
        variations.add(getValueWithoutProtocol());
        variations.add(getValueWithEndingSlash());
        variations.add(getValueWithoutProtocolWithEndingSlash());
        return variations;
    }

    @Override
    public String toString() {
        return getValue();
    }

    @Override
    public boolean equals(final Object o) {
        if (o == this) {
            return true;
        }
        if (o == null || !getClass().isAssignableFrom(o.getClass())) {
            return false;
        }
        final Uri entity = (Uri) o;
        return Objects.equal(entity.getValue(), this.getValue());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getValue());
    }

    protected String getValueWithoutProtocol() {
        final StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(getDomain());
        stringBuilder.append(getAddress());
        stringBuilder.append(getQuery());
        stringBuilder.append(getFragment());
        return stringBuilder.toString();
    }

    public String getDomainAndAddressOnly() {
        final StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(getDomain());
        stringBuilder.append(getAddress());
        return stringBuilder.toString();
    }

    protected String getValueWithEndingSlash() {
        return getValue() + "/";
    }

    protected String getValueWithoutProtocolWithEndingSlash() {
        return getValueWithoutProtocol() + "/";
    }

    public String getCorrectProtocol() {
        final String protocol = extractFromPattern(PROTOCOL_PATTERN).toLowerCase(Locale.US);
        if (!protocol.isEmpty()) {
            return protocol;
        } else {
            return "http://";
        }
    }

    public boolean hasProtocol() {
        return !extractFromPattern(PROTOCOL_PATTERN).toLowerCase(Locale.US).isEmpty();
    }

    public String getDomain() {
        return extractFromPattern(DOMAIN_PATTERN).toLowerCase(Locale.US);
    }

    protected String getAddress() {
        return extractFromPattern(ADDRESS_PATTERN);
    }

    protected String getQuery() {
        return extractFromPattern(QUERY_PATTERN);
    }

    protected String getFragment() {
        return extractFromPattern(FRAGMENT_PATTERN);
    }

    private String extractFromPattern(final Pattern pattern) {
        final Matcher matcher = pattern.matcher(this.value);
        return matcher.matches() ? matcher.group(matcher.groupCount()) : "";
    }

    private String value;

    private static final Pattern PROTOCOL_PATTERN = Pattern.compile("(.*://).*");
    private static final Pattern DOMAIN_PATTERN = Pattern.compile("(.*://)?([^/#?]*).*$");
    private static final Pattern ADDRESS_PATTERN = Pattern.compile("(.*://)?[^/#?]*([^#?]*).*$");
    private static final Pattern QUERY_PATTERN = Pattern.compile(".*(\\?[^#]*).*$");
    private static final Pattern FRAGMENT_PATTERN = Pattern.compile(".*(#.*)$");
}
