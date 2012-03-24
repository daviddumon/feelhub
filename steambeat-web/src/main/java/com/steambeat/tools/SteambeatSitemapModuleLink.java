package com.steambeat.tools;

import com.google.common.io.*;
import org.apache.commons.io.input.NullInputStream;
import org.apache.log4j.Logger;
import org.restlet.*;
import org.restlet.data.Method;

import java.io.*;

public class SteambeatSitemapModuleLink {

    public InputStream get(final String index) {
        final Request request = Requests.create(Method.GET, createSitemapUrl(index));
        final Client client = Clients.create();
        try {
            final Response response = client.handle(request);
            return copyStream(response);
        } catch (Exception e) {
            Logger.getLogger(SteambeatSitemapModuleLink.class).error("Error while fetching sitemap from sitemap builder", e);
            return new NullInputStream(1L);
        } finally {
            Clients.stop(client);
        }
    }

    private InputStream copyStream(final Response response) throws IOException {
        final ByteArrayOutputStream output = new ByteArrayOutputStream();
        ByteStreams.copy(response.getEntity().getStream(), output);
        final ByteArrayInputStream result = new ByteArrayInputStream(output.toByteArray());
        Closeables.closeQuietly(output);
        return result;
    }

    private String createSitemapUrl(final String index) {
        return sitemapBuilderAddress() + index;
    }

    private String sitemapBuilderAddress() {
        return new SteambeatWebProperties().getSitemapBuilderAddress();
    }
}
