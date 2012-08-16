package com.steambeat.application;

import com.steambeat.application.dto.JudgmentDTO;
import com.steambeat.domain.opinion.*;
import com.steambeat.domain.reference.Reference;
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
            opinion.addJudgment(getReferenceFor(judgmentDTO), Feeling.valueOf(judgmentDTO.feeling));
        }
    }

    private Reference getReferenceFor(final JudgmentDTO judgmentDTO) {
        final Reference reference = Repositories.references().get(judgmentDTO.referenceId);
        if (reference == null) {
            throw new OpinionCreationException();
        }
        return reference;
    }
}
