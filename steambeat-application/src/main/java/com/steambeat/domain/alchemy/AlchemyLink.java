package com.steambeat.domain.alchemy;

import com.steambeat.tools.SteambeatApplicationProperties;

import java.io.*;
import java.net.*;

public class AlchemyLink {

    public AlchemyLink() {
        final SteambeatApplicationProperties steambeatApplicationProperties = new SteambeatApplicationProperties();
        apiKey = steambeatApplicationProperties.getAlchemyApiKey();
    }

    public InputStream get(final String uri) {
        final String alchemyUri = buildUri(uri);
        URL url = null;
        try {
            url = new URL(alchemyUri);
            final HttpURLConnection handle = (HttpURLConnection) url.openConnection();
            handle.setDoOutput(true);
            return handle.getInputStream();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    private String buildUri(final String uri) {
        final StringBuilder uriBuilder = new StringBuilder();
        try {
            final String requestUri = "http://access.alchemyapi.com/calls/url/URLGetRankedNamedEntities?outputMode=json&apikey=";
            uriBuilder.append(requestUri).append(apiKey).append("&url=").append(URLEncoder.encode(uri, "UTF-8")).append("&linkedData=1");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return uriBuilder.toString();
    }

    private final String apiKey;
}
