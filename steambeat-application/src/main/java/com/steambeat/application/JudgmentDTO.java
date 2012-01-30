package com.steambeat.application;

import com.steambeat.domain.opinion.Feeling;
import com.steambeat.domain.subject.Subject;

public class JudgmentDTO {

    public JudgmentDTO(final Subject subject, final Feeling feeling) {
        this.subjectId = subject.getId();
        this.feeling = feeling.toString();
    }

    public String subjectId;
    public String feeling;
}
