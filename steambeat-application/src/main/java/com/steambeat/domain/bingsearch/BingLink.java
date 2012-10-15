package com.steambeat.domain.bingsearch;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.steambeat.domain.bingsearch.readmodel.BingResults;

import java.io.*;
import java.net.*;

public class BingLink {

    public String getIllustration(final String value) {
        final String query = buildQueryFor(value);
        try {
            final URL url = new URL(query);
            final URLConnection uc = url.openConnection();
            addAuthorizationHeader(uc);
            return unmarshall(uc.getInputStream());
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "";
    }

    private String buildQueryFor(final String value) {
        try {
            final StringBuilder stringBuilder = new StringBuilder();
            final String queryRoot = "https://api.datamarket.azure.com/Data.ashx/Bing/Search/Image?Query='";
            stringBuilder.append(queryRoot);
            stringBuilder.append(URLEncoder.encode(value, "UTF-8"));
            final String queryOptions = "'&Adult='Off'&$top=1&$format=JSON";
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

    private String unmarshall(final InputStream inputStream) {
        final ObjectMapper objectMapper = new ObjectMapper();
        try {
            final BingResults bingResults = objectMapper.readValue(inputStream, BingResults.class);
            return bingResults.d.results.get(0).MediaUrl;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }

}
