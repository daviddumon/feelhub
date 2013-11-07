package com.feelhub.web.dto;

import com.feelhub.domain.feeling.Feeling;
import com.google.common.collect.Lists;

import java.util.List;

public class FeelingDataFactory {

    public List<FeelingData> feelingDatas(final List<Feeling> feelings) {
        final List<FeelingData> feelingDatas = Lists.newArrayList();
        for (final Feeling feeling : feelings) {
            feelingDatas.add(feelingData(feeling));
        }
        return feelingDatas;
    }

    private FeelingData feelingData(final Feeling feeling) {
        final FeelingData.Builder builder = new FeelingData.Builder();
        builder.id(feeling.getId());
        builder.userId(feeling.getUserId());
        builder.topicId(feeling.getTopicId());
        builder.text(feeling.getText());
        builder.languageCode(feeling.getLanguageCode());
        builder.feelingValue(feeling.getFeelingValue());
        builder.force(feeling.getForce());
        builder.creationDate(feeling.getCreationDate());
        return builder.build();
    }
}
