package de.daroge.reactiveweb.cqs.infrastructure.web;

import de.daroge.reactiveweb.cqs.application.UserDto;
import de.daroge.reactiveweb.cqs.application.service.api.IWriteUserService;
import de.daroge.reactiveweb.cqs.util.InfrastructureService;
import lombok.AllArgsConstructor;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

import java.net.URI;

import static org.springframework.web.reactive.function.server.ServerResponse.created;

@InfrastructureService
@AllArgsConstructor
public class UserCommandHandler {

    private IWriteUserService writeUserService;

    // TODO provide better error handling
    public Mono<ServerResponse> newUser(ServerRequest serverRequest){
        return serverRequest.bodyToMono(UserDto.class)
                .doOnNext(userDto -> userDto.validate())
                .flatMap(userDto -> writeUserService.newUser(userDto))
                .flatMap(userId -> created(URI.create("users/"+userId)).build())
                .doOnError(ex -> ServerResponse.badRequest());

    }
}
