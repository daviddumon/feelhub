package com.steambeat.application.dto;

import com.steambeat.domain.opinion.Feeling;
import com.steambeat.domain.reference.Reference;

import java.util.UUID;

public class JudgmentDTO {

    public JudgmentDTO(final Reference reference, final Feeling feeling) {
        this.referenceId = reference.getId();
        this.feeling = feeling.toString();
    }

    public UUID referenceId;
    public String feeling;
}
