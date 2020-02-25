package de.daroge.reactiveweb.cqs.infrastructure.web;

import de.daroge.reactiveweb.cqs.application.UserDto;
import de.daroge.reactiveweb.cqs.application.service.api.IWriteUserService;
import de.daroge.reactiveweb.cqs.util.InfrastructureService;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.support.WebExchangeBindException;
import org.springframework.web.reactive.function.server.*;
import org.springframework.web.server.ResponseStatusException;
import reactor.core.publisher.Mono;

import javax.validation.ConstraintViolationException;
import java.net.URI;
import java.util.function.Consumer;

import static org.springframework.web.reactive.function.server.ServerResponse.created;
import static org.springframework.web.reactive.function.server.RequestPredicates.accept;
import static org.springframework.web.reactive.function.server.RequestPredicates.contentType;

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
