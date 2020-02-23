package de.daroge.reactiveweb.cqs.domain;

import java.time.LocalDateTime;
import java.util.UUID;
public abstract class Event {

    private final UUID id;
    private final LocalDateTime createdTime;
    public Event() {
        this.id = UUID.randomUUID();
        this.createdTime = LocalDateTime.now();
    }
}
