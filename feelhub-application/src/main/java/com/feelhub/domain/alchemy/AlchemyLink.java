package com.feelhub.domain.alchemy;

import com.feelhub.domain.topic.http.uri.Uri;
import com.feelhub.tools.FeelhubApplicationProperties;

import java.io.*;
import java.net.*;

public class AlchemyLink {

    public AlchemyLink() {
        final FeelhubApplicationProperties feelhubApplicationProperties = new FeelhubApplicationProperties();
        apiKey = feelhubApplicationProperties.getAlchemyApiKey();
    }

    public InputStream get(final Uri uri) {
        final String alchemyUri = buildUri(uri.getValue());
        InputStream inputStream;
        try {
            URL url = new URL(alchemyUri);
            final HttpURLConnection handle = (HttpURLConnection) url.openConnection();
            handle.setDoOutput(true);
            inputStream = handle.getInputStream();
        } catch (MalformedURLException e) {
            e.printStackTrace();
            return null;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
        incrementApiCallsCount();
        return inputStream;
    }

    private void incrementApiCallsCount() {
        //To change body of created methods use File | Settings | File Templates.
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
