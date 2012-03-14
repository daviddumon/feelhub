package com.steambeat.application.dto;

import com.steambeat.domain.opinion.Feeling;
import com.steambeat.domain.subject.Subject;

import java.util.UUID;

public class JudgmentDTO {

    public JudgmentDTO(final Subject subject, final Feeling feeling) {
        this.subjectId = subject.getId();
        this.feeling = feeling.toString();
    }

    public UUID subjectId;
    public String feeling;
}
