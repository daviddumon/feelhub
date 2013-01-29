package com.feelhub.domain.media;

import com.feelhub.domain.topic.Topic;

public class MediaFactory {

    public Media newMedia(final Topic from, final Topic to) {
        return new Media(from.getId(), to.getId());
    }
}
