package com.feelhub.domain.topic.web;

import com.feelhub.domain.topic.*;

import java.util.UUID;

public class WebTopic extends Topic {

    //mongolink
    protected WebTopic() {

    }

    @Override
    public TopicType getType() {
        return WebTopicType.valueOf(typeValue);
    }

    public void setType(final WebTopicType type) {
        this.typeValue = type.toString();
    }

    public String getTypeValue() {
        return typeValue;
    }

    public WebTopic(final UUID id, final WebTopicType type) {
        super(id);
        this.typeValue = type.toString();
    }

    private String typeValue;
}

//private void requestAlchemyAnalysis(final RealTopic realTopic, final String value) {
//    final AlchemyRequestEvent alchemyRequestEvent = new AlchemyRequestEvent(realTopic, value);
//    DomainEventBus.INSTANCE.post(alchemyRequestEvent);
//}