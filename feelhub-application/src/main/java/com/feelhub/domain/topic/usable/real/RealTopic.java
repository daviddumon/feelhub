package com.feelhub.domain.topic.usable.real;

import com.feelhub.domain.eventbus.DomainEventBus;
import com.feelhub.domain.thesaurus.FeelhubLanguage;
import com.feelhub.domain.topic.TopicType;
import com.feelhub.domain.topic.usable.UsableTopic;
import com.feelhub.domain.translation.ReferenceTranslationRequestEvent;
import com.google.common.collect.Lists;

import java.util.*;

public class RealTopic extends UsableTopic {

    //mongolink constructor do not delete!
    protected RealTopic() {
    }

    @Override
    public TopicType getType() {
        return RealTopicType.valueOf(typeValue);
    }

    public String getTypeValue() {
        return typeValue;
    }

    public void setType(final RealTopicType type) {
        this.typeValue = type.toString();
    }

    public RealTopic(final UUID id, final RealTopicType type) {
        super(id);
        this.typeValue = type.toString();
    }

    public List<String> getSubTypes() {
        return subTypes;
    }

    public void addSubType(final String subtype) {
        subTypes.add(subtype);
    }

    @Override
    public void addName(final FeelhubLanguage feelhubLanguage, final String name) {
        super.addName(feelhubLanguage, name);
        if (getRealTopicType().isTranslatable() && needReference() && !feelhubLanguage.isNone()) {
            addReferenceName(feelhubLanguage, name);
        }
    }

    private RealTopicType getRealTopicType() {
        return RealTopicType.valueOf(typeValue);
    }

    private void addReferenceName(final FeelhubLanguage feelhubLanguage, final String name) {
        DomainEventBus.INSTANCE.post(new ReferenceTranslationRequestEvent(this, feelhubLanguage, name));
    }

    private boolean needReference() {
        return !names.containsKey(FeelhubLanguage.reference().getCode());
    }

    private final List<String> subTypes = Lists.newArrayList();
    private String typeValue;
}
