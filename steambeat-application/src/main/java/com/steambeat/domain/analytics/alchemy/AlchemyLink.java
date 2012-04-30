package com.steambeat.domain.analytics.alchemy;

import com.steambeat.tools.SteambeatApplicationProperties;

import java.io.*;
import java.net.*;

public class AlchemyLink {

    public AlchemyLink() {
        final SteambeatApplicationProperties steambeatApplicationProperties = new SteambeatApplicationProperties();
        apiKey = steambeatApplicationProperties.getAlchemyApiKey();
    }

    public InputStream get(final String webPageUri) {
        final String alchemyUri = buildUri(webPageUri);
        URL url = null;
        try {
            url = new URL(alchemyUri);
            HttpURLConnection handle = (HttpURLConnection) url.openConnection();
            handle.setDoOutput(true);
            return handle.getInputStream();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    private String buildUri(final String webPageUri) {
        StringBuilder uri = new StringBuilder();
        try {
            uri.append(requestUri).append(apiKey).append("&url=").append(URLEncoder.encode(webPageUri, "UTF-8")).append("&linkedData=0");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return uri.toString();
    }

    private final String apiKey;
    private final String requestUri = "http://access.alchemyapi.com/calls/url/URLGetRankedNamedEntities?outputMode=json&apikey=";
}
