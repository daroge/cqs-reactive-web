package de.daroge.reactiveweb.cqs.infrastructure.event;

import de.daroge.reactiveweb.cqs.domain.Event;
import de.daroge.reactiveweb.cqs.domain.EventPublisher;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class SpringEventPublisher implements EventPublisher {

    private final ApplicationEventPublisher publisher;

    @Override
    public void publish(Event event) {
        log.debug(" publishing " + event);
        publisher.publishEvent(event);
    }
}
