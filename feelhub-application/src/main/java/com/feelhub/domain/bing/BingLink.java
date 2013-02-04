package com.feelhub.domain.bing;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.feelhub.domain.admin.ApiCallEvent;
import com.feelhub.domain.bing.readmodel.*;
import com.feelhub.domain.eventbus.DomainEventBus;
import com.feelhub.tools.FeelhubApplicationProperties;
import com.google.common.collect.Lists;
import org.apache.commons.codec.binary.Base64;

import java.io.*;
import java.net.*;
import java.util.List;

public class BingLink {

    public BingLink() {
        feelhubApplicationProperties = new FeelhubApplicationProperties();
    }

    public List<String> getIllustrations(final String value, final String type) {
        final List<String> results = Lists.newArrayList();
        results.addAll(getResults(buildQueryFor(value, type)));
        if (results.isEmpty()) {
            results.addAll(getResults(buildQueryFor(value, "")));
        }
        return results;
    }

    private String buildQueryFor(final String value, final String type) {
        try {
            final StringBuilder stringBuilder = new StringBuilder();
            final String queryRoot = feelhubApplicationProperties.getBingRoot();
            stringBuilder.append(queryRoot);
            stringBuilder.append(URLEncoder.encode(value, "UTF-8"));
            if (!type.isEmpty()) {
                stringBuilder.append(URLEncoder.encode(" " + type, "UTF-8"));
            }
            final String queryOptions = "'&Adult='Off'&$top=5&$format=JSON";
            stringBuilder.append(queryOptions);
            return stringBuilder.toString();
        } catch (Exception e) {
        }
        return "";
    }

    private List<String> getResults(final String query) {
        final List<String> results = Lists.newArrayList();
        try {
            final URL url = new URL(query);
            final URLConnection uc = url.openConnection();
            addAuthorizationHeader(uc);
            results.addAll(unmarshall(uc.getInputStream()));
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        DomainEventBus.INSTANCE.post(ApiCallEvent.bingSearch());
        return results;
    }

    private void addAuthorizationHeader(final URLConnection uc) {
        String userAndKey = ":" + feelhubApplicationProperties.getBingApiKey();
        uc.setRequestProperty("Authorization", "Basic " + new String(Base64.encodeBase64(userAndKey.getBytes())));
    }

    private List<String> unmarshall(final InputStream inputStream) {
        final List<String> links = Lists.newArrayList();
        try {
            final ObjectMapper objectMapper = new ObjectMapper();
            BingResults bingResults = objectMapper.readValue(inputStream, BingResults.class);
            for (final BingEntity result : bingResults.d.results) {
                links.add(result.MediaUrl);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return links;
    }

    private FeelhubApplicationProperties feelhubApplicationProperties;
}
