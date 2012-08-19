package com.steambeat.domain.opinion;

import com.google.common.collect.Lists;
import com.steambeat.domain.BaseEntity;
import com.steambeat.domain.eventbus.DomainEventBus;
import com.steambeat.domain.reference.Reference;
import org.joda.time.DateTime;

import java.util.*;

public class Opinion extends BaseEntity {

    protected Opinion() {
    }

    public Opinion(final String text) {
        this.id = UUID.randomUUID();
        this.text = text;
        DomainEventBus.INSTANCE.post(new OpinionCreatedEvent(this));
    }

    public void addJudgment(final Reference reference, final Feeling feeling) {
        final Judgment judgment = new Judgment(reference, feeling);
        judgments.add(judgment);
        reference.setLastModificationDate(new DateTime());
        DomainEventBus.INSTANCE.post(new JudgmentCreatedEvent(judgment));
    }

    public UUID getId() {
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

    private UUID id;
    private String text;
    private final DateTime creationDate = new DateTime();
    private final List<Judgment> judgments = Lists.newArrayList();
}
