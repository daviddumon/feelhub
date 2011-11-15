package com.steambeat.tools;

import com.google.common.io.*;
import org.apache.commons.io.input.NullInputStream;
import org.apache.log4j.Logger;
import org.restlet.*;
import org.restlet.data.Method;

import java.io.*;

public class Hiram {

    public InputStream getSitemap(final String index) {
        final Request request = Requests.create(Method.GET, createUrl(index));
        final Client client = Clients.create();
        try {
            final Response response = client.handle(request);
            return copyStream(response);
        } catch (IOException e) {
            Logger.getLogger(Hiram.class).error("Error while fetching sitemap from Hiram", e);
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

    private String createUrl(final String index) {
        return hiramAdress() + "/sitemap_" + index + ".xml.gz";
    }

    private String hiramAdress() {
        return new SteambeatProperties().getHiramAddress();
    }
}
