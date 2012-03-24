package com.steambeat.tools;

import com.google.common.io.*;
import org.apache.commons.io.input.NullInputStream;
import org.apache.log4j.Logger;
import org.restlet.*;
import org.restlet.data.Method;

import java.io.*;

public class SitemapLink {

    public InputStream getSitemap(final String index) {
        final Request request = Requests.create(Method.GET, createUrl(index));
        final Client client = Clients.create();
        try {
            final Response response = client.handle(request);
            return copyStream(response);
        } catch (IOException e) {
            Logger.getLogger(SitemapLink.class).error("Error while fetching sitemap from sitemap builder", e);
            return new NullInputStream(1L);
        } finally {
            Clients.stop(client);
        }
    }

    private InputStream copyStream(final Response response) throws IOException {
        final ByteArrayOutputStream output = new ByteArrayOutputStream();
        System.out.println("output:" + output);
        System.out.println("response" + response.toString());
        System.out.println("entity" + response.getEntity().toString());
        System.out.println("stream:" + response.getEntity().getStream());
        ByteStreams.copy(response.getEntity().getStream(), output);
        final ByteArrayInputStream result = new ByteArrayInputStream(output.toByteArray());
        Closeables.closeQuietly(output);
        return result;
    }

    private String createUrl(final String index) {
        return sitemapBuilderAddress() + "/sitemap_" + index + ".xml";
    }

    private String sitemapBuilderAddress() {
        return new SteambeatWebProperties().getSitemapBuilderAddress();
    }
}
