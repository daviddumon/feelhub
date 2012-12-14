package com.feelhub.domain.topic.http.uri;

import com.google.common.collect.Lists;
import org.restlet.data.MediaType;

import java.util.List;

public class ResolverResult {

    public void addUriToPath(final Uri uri) {
        this.path.add(uri);
    }

    public List<Uri> getPath() {
        return path;
    }

    public MediaType getMediaType() {
        return mediaType;
    }

    public void setMediaType(final MediaType mediaType) {
        this.mediaType = mediaType;
    }

    private List<Uri> path = Lists.newArrayList();
    private MediaType mediaType;
}
