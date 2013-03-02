package com.feelhub.domain.topic.real;

import com.feelhub.domain.thesaurus.FeelhubLanguage;
import com.feelhub.domain.topic.Topic;
import com.feelhub.domain.topic.TopicType;
import com.google.common.base.Strings;

import java.util.UUID;

public class RealTopic extends Topic {

    //mongolink constructor do not delete!
    protected RealTopic() {
    }

    public RealTopic(final UUID id, final RealTopicType type) {
        super(id);
        this.typeValue = type.toString();
    }

    @Override
    public TopicType getType() {
        return RealTopicType.valueOf(typeValue);
    }

    public String getTypeValue() {
        return typeValue;
    }

    @Override
    public void addName(final FeelhubLanguage feelhubLanguage, final String name) {
        super.addName(feelhubLanguage, name);
    }

    public RealTopicType getRealTopicType() {
        return RealTopicType.valueOf(typeValue);
    }

    public boolean mustTranslate() {
        return getRealTopicType().isTranslatable() && needReference() && !names.isEmpty() && !onlyHasNoneLanguageName();
    }

    private boolean onlyHasNoneLanguageName() {
        return names.size() == 1 && !Strings.isNullOrEmpty(names.get(FeelhubLanguage.none().getCode()));
    }

    private boolean needReference() {
        return !names.containsKey(FeelhubLanguage.reference().getCode());
    }

    private String typeValue;
}
