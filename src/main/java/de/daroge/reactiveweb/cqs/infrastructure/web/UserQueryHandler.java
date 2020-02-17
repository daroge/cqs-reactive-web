package de.daroge.reactiveweb.cqs.infrastructure.web;

import de.daroge.reactiveweb.cqs.application.UserDto;
import de.daroge.reactiveweb.cqs.application.service.api.IQueryUserService;
import de.daroge.reactiveweb.cqs.domain.UserId;
import de.daroge.reactiveweb.cqs.util.InfrastructureService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

import static org.springframework.web.reactive.function.BodyInserters.fromPublisher;
import static org.springframework.web.reactive.function.server.ServerResponse.notFound;
import static org.springframework.web.reactive.function.server.ServerResponse.ok;
import static de.daroge.reactiveweb.cqs.domain.UserId.userId;

@InfrastructureService
@RequiredArgsConstructor
public class UserQueryHandler {

    private IQueryUserService queryUserService;

    public Mono<ServerResponse> getUser(ServerRequest serverRequest){
        return Mono.just(serverRequest.pathVariable("userId"))
                .map(UserId::userId)
                .flatMap(idModel -> queryUserService.find(idModel))
                .flatMap(userDto -> ok().contentType(MediaType.APPLICATION_JSON)
                        .body(fromPublisher(Mono.just(userDto),UserDto.class)))
                .switchIfEmpty(Mono.defer(() -> notFound().build()));
    }

    public Mono<ServerResponse> all(ServerRequest serverRequest){
        return ok().contentType(MediaType.APPLICATION_JSON)
                .body(fromPublisher(queryUserService.findAll(), UserDto.class));
    }
}
