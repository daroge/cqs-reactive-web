package de.daroge.reactiveweb.cqs.application.service.api;

import de.daroge.reactiveweb.cqs.application.UserDto;
import de.daroge.reactiveweb.cqs.util.ApplicationService;
import reactor.core.publisher.Mono;

public interface IWriteUserService {
    Mono<String> newUser(UserDto userDto);
}
