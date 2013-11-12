package com.feelhub.domain.feeling;

import com.feelhub.domain.eventbus.DomainEvent;
import com.google.common.base.Objects;

import static com.google.common.base.Preconditions.*;

public class FirstFeelingCreatedEvent extends DomainEvent {

    public FirstFeelingCreatedEvent(final Feeling feeling) {
        checkNotNull(feeling);
        this.feeling = feeling;
    }

    @Override
    public String toString() {
        return Objects.toStringHelper(this).add("Date", date).add("Feeling id", feeling.getId()).toString();
    }

    public Feeling getFeeling() {
        return feeling;
    }

    private final Feeling feeling;
}
