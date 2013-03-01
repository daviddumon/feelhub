package com.feelhub.web.dto;

import com.feelhub.domain.feeling.Feeling;
import com.feelhub.web.authentification.CurrentUser;
import com.google.common.collect.Lists;
import com.google.inject.Inject;

import java.util.*;

public class FeelingDataFactory {

    @Inject
    public FeelingDataFactory(final SentimentDataFactory sentimentDataFactory) {
        this.sentimentDataFactory = sentimentDataFactory;
    }

    public List<FeelingData> feelingDatas(final List<Feeling> feelings, final UUID contextId) {
        final List<FeelingData> feelingDatas = Lists.newArrayList();
        for (final Feeling feeling : feelings) {
            feelingDatas.add(feelingData(feeling, contextId));
        }
        return feelingDatas;
    }

    public List<FeelingData> feelingDatas(final List<Feeling> feelings) {
        final List<FeelingData> feelingDatas = Lists.newArrayList();
        for (final Feeling feeling : feelings) {
            feelingDatas.add(feelingData(feeling));
        }
        return feelingDatas;
    }

    private FeelingData feelingData(final Feeling feeling, final UUID contextId) {
        final FeelingData.Builder builder = new FeelingData.Builder();
        builder.id(feeling.getId());
        builder.userId(feeling.getUserId());
        builder.text(feeling.getText());
        builder.languageCode(feeling.getLanguageCode());
        builder.sentimentDatas(sentimentDataFactory.sentimentDatas(feeling, CurrentUser.get().getLanguage()), contextId);
        return builder.build();
    }

    private FeelingData feelingData(final Feeling feeling) {
        final FeelingData.Builder builder = new FeelingData.Builder();
        builder.id(feeling.getId());
        builder.userId(feeling.getUserId());
        builder.text(feeling.getText());
        builder.languageCode(feeling.getLanguageCode());
        builder.sentimentDatas(sentimentDataFactory.sentimentDatas(feeling, CurrentUser.get().getLanguage()));
        return builder.build();
    }

    private final SentimentDataFactory sentimentDataFactory;
}
