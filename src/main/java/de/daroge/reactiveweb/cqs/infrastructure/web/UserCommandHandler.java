package de.daroge.reactiveweb.cqs.infrastructure.web;

import de.daroge.reactiveweb.cqs.application.UserDto;
import de.daroge.reactiveweb.cqs.application.service.api.IWriteUserService;
import de.daroge.reactiveweb.cqs.util.InfrastructureService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.support.WebExchangeBindException;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import org.springframework.web.server.ResponseStatusException;
import reactor.core.publisher.Mono;

import javax.validation.ConstraintViolationException;
import java.net.URI;
import java.util.function.Consumer;

import static org.springframework.web.reactive.function.server.ServerResponse.created;

@InfrastructureService
@AllArgsConstructor
public class UserCommandHandler {

    private IWriteUserService writeUserService;

    public Mono<ServerResponse> newUser(ServerRequest serverRequest){
        return serverRequest.bodyToMono(UserDto.class)
                .doOnNext(userDto -> userDto.validate())
                .flatMap(userDto -> writeUserService.newUser(userDto))
                .flatMap(userId -> created(URI.create("users/"+userId)).build());
    }
}
