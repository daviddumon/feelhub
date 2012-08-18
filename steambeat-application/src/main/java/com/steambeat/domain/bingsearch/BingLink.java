package com.steambeat.domain.bingsearch;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.steambeat.domain.bingsearch.readmodel.BingResults;
import com.steambeat.domain.keyword.Keyword;

import java.io.*;
import java.net.*;

public class BingLink {

    public String getIllustration(final Keyword keyword) {
        final String query = buildQueryFor(keyword);
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

    private String buildQueryFor(final Keyword keyword) {
        try {
            final StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append(queryRoot);
            stringBuilder.append(URLEncoder.encode(keyword.toString(), "UTF-8"));
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
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "";
    }

    private static String queryRoot = "https://api.datamarket.azure.com/Data.ashx/Bing/Search/Image?Query='";
    private static String queryOptions = "'&Adult='Off'&$top=1&$format=JSON";
}
