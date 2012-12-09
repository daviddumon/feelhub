package com.feelhub.domain.bingsearch;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.feelhub.domain.bingsearch.readmodel.*;
import com.feelhub.tools.*;
import com.google.common.collect.Lists;
import org.restlet.*;
import org.restlet.data.Method;

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
            final String queryOptions = "'&Adult='Off'&$top=2&$format=JSON";
            stringBuilder.append(queryOptions);
            return stringBuilder.toString();
        } catch (Exception e) {
        }
        return "";
    }

    private void addAuthorizationHeader(final URLConnection uc) {
        final String basicAuth = "Basic OmQ1MllKNGlPWjBKTzZscWI3NnhHcndWV3BETzVDeXJ1bC9ETldtZk40NHM9";
        uc.setRequestProperty("Authorization", basicAuth);
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
        return results;
    }

    private List<String> unmarshall(final InputStream inputStream) {
        final List<String> links = Lists.newArrayList();
        final ObjectMapper objectMapper = new ObjectMapper();
        BingResults bingResults = null;
        try {
            bingResults = objectMapper.readValue(inputStream, BingResults.class);
            for (final BingEntity result : bingResults.d.results) {
                try {
                    links.add(exist(result.MediaUrl));
                } catch (BadIllustrationLink e) {
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return links;
    }

    private String exist(final String result) {
        final Client client = Clients.create();
        final Request request = Requests.create(Method.HEAD, result);
        final Response response = client.handle(request);
        if (response.getStatus().isSuccess()) {
            return result;
        } else {
            throw new BadIllustrationLink();
        }
    }

    private FeelhubApplicationProperties feelhubApplicationProperties;
}
