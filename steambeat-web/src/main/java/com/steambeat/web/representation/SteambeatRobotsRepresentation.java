package com.steambeat.web.representation;

import com.google.common.collect.Lists;
import com.google.common.io.*;
import org.joda.time.DateTime;
import org.restlet.data.*;
import org.restlet.representation.*;

import java.io.*;
import java.util.List;

public class SteambeatRobotsRepresentation extends StreamRepresentation {

    public SteambeatRobotsRepresentation(final InputStream sitemap) {
        super(MediaType.TEXT_PLAIN);
        setExpirationDate(new DateTime().toDate());
        setModificationDate(new DateTime().toDate());
        List<Encoding> encodings = Lists.newArrayList();
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
