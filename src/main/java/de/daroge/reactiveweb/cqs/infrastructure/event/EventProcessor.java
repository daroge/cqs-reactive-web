package de.daroge.reactiveweb.cqs.infrastructure.event;

import de.daroge.reactiveweb.cqs.domain.UserCreatedEvent;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class EventProcessor {

    @EventListener(UserCreatedEvent.class)
    public void processUserCreation(UserCreatedEvent event){
        log.info("fine event received " + event);
    }
}
