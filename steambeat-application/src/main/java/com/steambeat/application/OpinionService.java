package com.steambeat.application;

import com.steambeat.domain.opinion.*;
import com.steambeat.domain.topic.Topic;
import com.steambeat.repositories.Repositories;

import java.util.List;

public class OpinionService {

    public void addOpinion(final String text, final List<JudgmentDTO> judgmentDTOs) {
        final Opinion opinion = new Opinion(text);
        createJudgments(judgmentDTOs, opinion);
        Repositories.opinions().add(opinion);
    }

    private void createJudgments(final List<JudgmentDTO> judgmentDTOs, final Opinion opinion) {
        for (final JudgmentDTO judgmentDTO : judgmentDTOs) {
            opinion.addJudgment(getTopicFor(judgmentDTO), Feeling.valueOf(judgmentDTO.feeling));
        }
    }

    private Topic getTopicFor(final JudgmentDTO judgmentDTO) {
        final Topic topic = Repositories.topics().get(judgmentDTO.topicId);
        if (topic == null) {
            throw new OpinionCreationException();
        }
        return topic;
    }
}
