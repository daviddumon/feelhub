package com.feelhub.domain.alchemy;

import com.feelhub.domain.admin.ApiCallEvent;
import com.feelhub.domain.eventbus.DomainEventBus;
import com.feelhub.domain.topic.http.uri.Uri;
import com.feelhub.tools.FeelhubApplicationProperties;

import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;

public class AlchemyLink {

    public AlchemyLink() {
        final FeelhubApplicationProperties feelhubApplicationProperties = new FeelhubApplicationProperties();
        apiKey = feelhubApplicationProperties.getAlchemyApiKey();
    }

    public InputStream get(final Uri uri) {
        InputStream inputStream = get(buildUri(uri.getValue()));
        if (inputStream != null) {
            DomainEventBus.INSTANCE.post(ApiCallEvent.alchemy());
        }
        return inputStream;
    }

    protected InputStream get(String alchemyUri) {
        try {
            URL url = new URL(alchemyUri);
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
