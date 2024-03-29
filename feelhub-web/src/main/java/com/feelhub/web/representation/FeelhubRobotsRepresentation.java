package com.feelhub.web.representation;

import com.google.common.collect.Lists;
import com.google.common.io.*;
import org.joda.time.DateTime;
import org.restlet.data.*;
import org.restlet.representation.StreamRepresentation;

import java.io.*;
import java.util.List;

public class FeelhubRobotsRepresentation extends StreamRepresentation {

    public FeelhubRobotsRepresentation(final InputStream sitemap) {
        super(MediaType.TEXT_PLAIN);
        setExpirationDate(new DateTime().toDate());
        setModificationDate(new DateTime().toDate());
        final List<Encoding> encodings = Lists.newArrayList();
        encodings.add(new Encoding("UTF-8"));
        setEncodings(encodings);
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
