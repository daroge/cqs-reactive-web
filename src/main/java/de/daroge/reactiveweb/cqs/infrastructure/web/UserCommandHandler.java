package de.daroge.reactiveweb.cqs.infrastructure.web;

import de.daroge.reactiveweb.cqs.application.UserDto;
import de.daroge.reactiveweb.cqs.application.service.api.IWriteUserService;
import de.daroge.reactiveweb.cqs.util.InfrastructureService;
import lombok.Getter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

import java.net.URI;

import static org.springframework.web.reactive.function.server.RequestPredicates.*;
import static org.springframework.web.reactive.function.server.ServerResponse.created;

@Getter
@InfrastructureService
public class UserCommandHandler {

    private final IWriteUserService writeUserService;

    public UserCommandHandler(IWriteUserService writeUserService){
        this.writeUserService = writeUserService;
    }

    public Mono<ServerResponse> newUser(ServerRequest serverRequest){
        return serverRequest.bodyToMono(UserDto.class)
                .doOnNext(userDto -> userDto.validate())
                .flatMap(writeUserService::newUser)
                .flatMap(userId -> created(URI.create("/users/"+userId)).build());
    }

    @Configuration
    public class UserCommandRouter {
        @Bean public RouterFunction<ServerResponse> userCommandRoute(UserCommandHandler userCommandHandler){
            return RouterFunctions.route(accept(MediaType.APPLICATION_JSON).and(contentType(MediaType.APPLICATION_JSON)).and(POST("/users")), userCommandHandler::newUser);
        }
    }
}
