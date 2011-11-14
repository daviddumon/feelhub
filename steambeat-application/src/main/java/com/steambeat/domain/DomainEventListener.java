package com.steambeat.domain;

public interface DomainEventListener<T extends DomainEvent> {

    void notify(T event);
}
