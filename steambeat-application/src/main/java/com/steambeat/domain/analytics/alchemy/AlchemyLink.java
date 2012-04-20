package com.steambeat.domain.analytics.alchemy;

import com.google.common.io.*;
import com.steambeat.tools.*;
import org.apache.commons.io.input.NullInputStream;
import org.apache.log4j.Logger;
import org.restlet.*;
import org.restlet.data.Method;

import java.io.*;
import java.net.URLEncoder;

public class AlchemyLink {

    public AlchemyLink() {
        final SteambeatApplicationProperties steambeatApplicationProperties = new SteambeatApplicationProperties();
        apiKey = steambeatApplicationProperties.getAlchemyApiKey();
    }

    public InputStream get(final String webPageUri) {
        final String uri = buildUri(webPageUri);
        final Request request = Requests.create(Method.GET, webPageUri);
        final Client client = Clients.create();
        try {
            final Response response = client.handle(request);
            return copyStream(response);
        } catch (Exception e) {
            e.printStackTrace();
            Logger.getLogger(AlchemyNamedEntityProvider.class).error("Error while fetching data from alchemy API", e);
            return new NullInputStream(1L);
        } finally {
            Clients.stop(client);
        }
    }

    private String buildUri(final String webPageUri) {
        StringBuilder uri = new StringBuilder();
        try {
            uri.append(requestUri).append(apiKey).append("&url=").append(URLEncoder.encode(webPageUri, "UTF-8"));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return uri.toString();
    }

    private InputStream copyStream(final Response response) throws IOException {
        final ByteArrayOutputStream output = new ByteArrayOutputStream();
        ByteStreams.copy(response.getEntity().getStream(), output);
        final ByteArrayInputStream result = new ByteArrayInputStream(output.toByteArray());
        Closeables.closeQuietly(output);
        return result;
    }

    private final String apiKey;
    private final String requestUri = "http://access.alchemyapi.com/calls/url/URLGetRankedNamedEntities?apikey=";
}
