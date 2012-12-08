package com.feelhub.domain.tag;

import com.feelhub.domain.eventbus.DomainEvent;
import com.feelhub.domain.topic.usable.UsableTopic;

public class TagRequestEvent extends DomainEvent {

    public TagRequestEvent(final UsableTopic topic, final String name) {
        this.usableTopic = topic;
        this.name = name;
    }

    @Override
    public String toString() {
        return "Tag request event";
    }

    public UsableTopic getUsableTopic() {
        return usableTopic;
    }

    public String getName() {
        return name;
    }

    private final UsableTopic usableTopic;
    private final String name;
}
