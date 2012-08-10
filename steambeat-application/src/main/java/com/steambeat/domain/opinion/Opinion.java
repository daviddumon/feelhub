package com.steambeat.domain.opinion;

import com.google.common.collect.Lists;
import com.steambeat.domain.BaseEntity;
import com.steambeat.domain.eventbus.DomainEventBus;
import com.steambeat.domain.topic.Topic;
import org.joda.time.DateTime;

import java.util.*;

public class Opinion extends BaseEntity {

    protected Opinion() {
    }

    public Opinion(final String text) {
        this.id = UUID.randomUUID();
        this.text = text;
        DomainEventBus.INSTANCE.post(new OpinionPostedEvent(this));
    }

    public void addJudgment(final Topic topic, final Feeling feeling) {
        final Judgment judgment = new Judgment(topic, feeling);
        judgments.add(judgment);
        topic.setLastModificationDate(new DateTime());
        DomainEventBus.INSTANCE.post(new JudgmentPostedEvent(judgment));
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
