package com.feelhub.domain.topic.http;

import com.feelhub.domain.thesaurus.FeelhubLanguage;
import com.feelhub.domain.topic.Topic;
import com.feelhub.domain.topic.TopicType;
import com.feelhub.domain.topic.http.uri.Uri;
import org.restlet.data.MediaType;

import java.util.UUID;

public class HttpTopic extends Topic {

    //mongolink
    protected HttpTopic() {

    }

    public HttpTopic(final UUID id) {
        super(id);
    }

    @Override
    public TopicType getType() {
        return HttpTopicType.valueOf(typeValue);
    }

    public void setType(final HttpTopicType type) {
        this.typeValue = type.toString();
    }

    @Override
    public void addUri(final Uri uri) {
        super.addUri(uri);
        if(getType() != HttpTopicType.Website) {
            final String uriAsString = uri.toString();
            final int lastSlash = uriAsString.lastIndexOf("/");
            addName(FeelhubLanguage.none(), uriAsString.substring(lastSlash + 1, uriAsString.length()));
        }
    }

    @Override
    public void addName(final FeelhubLanguage feelhubLanguage, final String name) {
        names.put(feelhubLanguage.getCode(), name);
    }

    public String getTypeValue() {
        return typeValue;
    }

    public MediaType getMediaType() {
        return MediaType.valueOf(mediaTypeValue);
    }

    public void setMediaType(final MediaType mediaType) {
        this.mediaTypeValue = mediaType.toString();
        setTypeForMediaType(mediaType);
    }

    private void setTypeForMediaType(final MediaType mediaType) {
        if (mediaType.getMainType().equalsIgnoreCase("application")) {
            setType(HttpTopicType.Data);
        } else if (mediaType.getMainType().equalsIgnoreCase("audio")) {
            setType(HttpTopicType.Audio);
        } else if (mediaType.getMainType().equalsIgnoreCase("image")) {
            setType(HttpTopicType.Image);
        } else if (mediaType.getMainType().equalsIgnoreCase("message")) {
            setType(HttpTopicType.Other);
        } else if (mediaType.getMainType().equalsIgnoreCase("model")) {
            setType(HttpTopicType.Data);
        } else if (mediaType.getMainType().equalsIgnoreCase("multipart")) {
            setType(HttpTopicType.Data);
        } else if (mediaType.getMainType().equalsIgnoreCase("text")) {
            setTypeForMediaTypeText(mediaType);
        } else if (mediaType.getMainType().equalsIgnoreCase("video")) {
            setType(HttpTopicType.Video);
        }
    }

    private void setTypeForMediaTypeText(final MediaType mediaType) {
        if (mediaType.getSubType().equalsIgnoreCase("html") || mediaType.getSubType().equalsIgnoreCase("xml")) {
            setType(HttpTopicType.Website);
        } else {
            setType(HttpTopicType.Data);
        }
    }

    public String getMediaTypeValue() {
        return mediaTypeValue;
    }

    public String getOpenGraphType() {
        return openGraphType;
    }

    public void setOpenGraphType(final String openGraphType) {
        this.openGraphType = openGraphType;
    }

    private String typeValue = HttpTopicType.Website.name();
    private String mediaTypeValue;
    private String openGraphType;
}