package com.steambeat.web.representation;

import com.google.common.io.*;
import org.restlet.data.MediaType;
import org.restlet.representation.StreamRepresentation;

import java.io.*;

public class SteambeatRobotsRepresentation extends StreamRepresentation {

    public SteambeatRobotsRepresentation(final InputStream sitemap) {
        super(MediaType.TEXT_PLAIN);
        this.sitemap = sitemap;
    }

    @Override
    public InputStream getStream() throws IOException {
        return sitemap;
    }

    @Override
    public void write(final OutputStream outputStream) throws IOException {
        ByteStreams.copy(sitemap, outputStream);
        Closeables.closeQuietly(sitemap);
    }

    private final InputStream sitemap;
}
