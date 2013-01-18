package com.feelhub.web.dto;

import com.feelhub.domain.feeling.Feeling;
import com.feelhub.web.authentification.CurrentUser;
import com.google.common.collect.Lists;
import com.google.inject.Inject;

import java.util.*;

public class FeelingDataFactory {

    @Inject
    public FeelingDataFactory(final TopicDataFactory topicDataFactory) {
        this.topicDataFactory = topicDataFactory;
    }

    public List<FeelingData> getFeelingDatas(final List<Feeling> feelings, final UUID contextId) {
        final List<FeelingData> feelingDatas = Lists.newArrayList();
        for (final Feeling feeling : feelings) {
            feelingDatas.add(getFeelingData(feeling, contextId));
        }
        return feelingDatas;
    }

    public List<FeelingData> getFeelingDatas(final List<Feeling> feelings) {
        final List<FeelingData> feelingDatas = Lists.newArrayList();
        for (final Feeling feeling : feelings) {
            feelingDatas.add(getFeelingData(feeling));
        }
        return feelingDatas;
    }

    private FeelingData getFeelingData(final Feeling feeling, final UUID contextId) {
        final FeelingData.Builder builder = new FeelingData.Builder();
        builder.id(feeling.getId());
        builder.userId(feeling.getUserId());
        builder.text(feeling.getText());
        builder.languageCode(feeling.getLanguageCode());
        builder.topicDatas(topicDataFactory.getTopicDatas(feeling, CurrentUser.get().getLanguage()), contextId);
        return builder.build();
    }

    private FeelingData getFeelingData(final Feeling feeling) {
        final FeelingData.Builder builder = new FeelingData.Builder();
        builder.id(feeling.getId());
        builder.userId(feeling.getUserId());
        builder.text(feeling.getText());
        builder.languageCode(feeling.getLanguageCode());
        builder.topicDatas(topicDataFactory.getTopicDatas(feeling, CurrentUser.get().getLanguage()));
        return builder.build();
    }

    private TopicDataFactory topicDataFactory;
}
