package de.daroge.reactiveweb.cqs.infrastructure.web;

import de.daroge.reactiveweb.cqs.application.UserDto;
import de.daroge.reactiveweb.cqs.application.service.api.IWriteUserService;
import de.daroge.reactiveweb.cqs.util.InfrastructureService;
import lombok.Getter;
import org.springframework.web.reactive.function.server.*;
import reactor.core.publisher.Mono;

import java.net.URI;

import static org.springframework.web.reactive.function.server.ServerResponse.created;

@Getter
@InfrastructureService
public class UserCommandHandler {

    private final IWriteUserService writeUserService;
    private final RouterFunction<ServerResponse> routerFunction;

    public UserCommandHandler(IWriteUserService writeUserService){
        this.writeUserService = writeUserService;
        this.routerFunction = RouterFunctions.route(RequestPredicates.POST(""),this::newUser);
    }

    public Mono<ServerResponse> newUser(ServerRequest serverRequest){
        return serverRequest.bodyToMono(UserDto.class)
                .doOnNext(userDto -> userDto.validate())
                .flatMap(writeUserService::newUser)
                .flatMap(userId -> created(URI.create("/users/"+userId)).build());
    }
}
