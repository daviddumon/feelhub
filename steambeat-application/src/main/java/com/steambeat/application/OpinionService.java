package com.steambeat.application;

import com.steambeat.application.dto.JudgmentDTO;
import com.steambeat.domain.opinion.*;
import com.steambeat.domain.subject.Subject;
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
            opinion.addJudgment(getSubjectFor(judgmentDTO), Feeling.valueOf(judgmentDTO.feeling));
        }
    }

    private Subject getSubjectFor(final JudgmentDTO judgmentDTO) {
        final Subject subject = Repositories.subjects().get(judgmentDTO.subjectId);
        if (subject == null) {
            throw new OpinionCreationException();
        }
        return subject;
    }
}
