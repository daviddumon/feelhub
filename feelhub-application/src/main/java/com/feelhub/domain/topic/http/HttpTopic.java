package com.feelhub.domain.topic.http;

import com.feelhub.domain.alchemy.AlchemyRequestEvent;
import com.feelhub.domain.eventbus.DomainEventBus;
import com.feelhub.domain.topic.*;
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

    public void setType(final HttpTopicType type) {
        this.typeValue = type.toString();
    }

    @Override
    public TopicType getType() {
        return HttpTopicType.valueOf(typeValue);
    }

    @Override
    public void addUri(final Uri uri) {
        super.addUri(uri);
        if (HttpTopicType.valueOf(typeValue).equals(HttpTopicType.Website)) {
            requestAlchemy();
        }
    }

    private void requestAlchemy() {
        final AlchemyRequestEvent alchemyRequestEvent = new AlchemyRequestEvent(this);
        DomainEventBus.INSTANCE.post(alchemyRequestEvent);
    }

    public String getTypeValue() {
        return typeValue;
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

    public MediaType getMediaType() {
        return MediaType.valueOf(mediaTypeValue);
    }

    public String getMediaTypeValue() {
        return mediaTypeValue;
    }

    private String typeValue;
    private String mediaTypeValue;
}