package com.feelhub.repositories.mapping;

import com.feelhub.domain.topic.http.uri.Uri;
import org.mongolink.domain.mapper.ComponentMap;

public class UriMapping extends ComponentMap<Uri> {

    public UriMapping() {
        super(Uri.class);
    }

    @Override
    public void map() {
        property().onProperty(element().getValue());
    }
}
