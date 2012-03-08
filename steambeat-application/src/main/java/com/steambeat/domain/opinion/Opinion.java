package com.steambeat.domain.opinion;

import com.google.common.collect.Lists;
import com.steambeat.domain.*;
import com.steambeat.domain.subject.Subject;
import org.joda.time.DateTime;

import java.util.*;

public class Opinion extends BaseEntity {

    protected Opinion() {
    }

    public Opinion(final String text) {
        this.text = text;
        DomainEventBus.INSTANCE.spread(new OpinionPostedEvent(this));
    }

    public void addJudgment(final Subject subject, final Feeling feeling) {
        final Judgment judgment = new Judgment(subject, feeling);
        judgments.add(judgment);
        DomainEventBus.INSTANCE.spread(new JudgmentPostedEvent(judgment));
    }

    public String getId() {
        return id;
    }

    public String getText() {
        return text;
    }

    public DateTime getCreationDate() {
        return creationDate;
    }

    public Date getCreationDateAsDate() {
        return creationDate.toDate();
    }

    public List<Judgment> getJudgments() {
        return judgments;
    }

    private String id;
    private String text;
    private final DateTime creationDate = new DateTime();
    private final List<Judgment> judgments = Lists.newArrayList();
}
