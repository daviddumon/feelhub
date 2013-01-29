package com.feelhub.domain.alchemy;

import com.feelhub.domain.admin.AdminStatisticCallsCounter;
import com.feelhub.domain.admin.Api;
import com.feelhub.domain.topic.http.uri.Uri;
import com.feelhub.tools.FeelhubApplicationProperties;
import com.google.inject.Inject;

import java.io.*;
import java.net.*;

public class AlchemyLink {

    @Inject
    public AlchemyLink(AdminStatisticCallsCounter callsCounter) {
        final FeelhubApplicationProperties feelhubApplicationProperties = new FeelhubApplicationProperties();
        apiKey = feelhubApplicationProperties.getAlchemyApiKey();
        this.callsCounter = callsCounter;
    }

    public InputStream get(final Uri uri) {
        InputStream inputStream = get(buildUri(uri.getValue()));
        if (inputStream != null) {
            incrementApiCallsCount();
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

    private void incrementApiCallsCount() {
        callsCounter.increment(Api.Alchemy);
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
    private AdminStatisticCallsCounter callsCounter;
}
