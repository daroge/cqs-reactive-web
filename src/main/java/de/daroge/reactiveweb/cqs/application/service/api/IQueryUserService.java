package de.daroge.reactiveweb.cqs.application.service.api;

import de.daroge.reactiveweb.cqs.application.UserDto;
import de.daroge.reactiveweb.cqs.domain.UserId;
import de.daroge.reactiveweb.cqs.util.ApplicationService;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface IQueryUserService {

    Mono<UserDto> find(UserId userId);
    Flux<UserDto> findAll();
}
