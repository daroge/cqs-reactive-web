package de.daroge.reactiveweb.cqs.infrastructure.web;

import de.daroge.reactiveweb.cqs.application.UserDto;
import de.daroge.reactiveweb.cqs.application.service.api.IQueryUserService;
import de.daroge.reactiveweb.cqs.domain.UserId;
import de.daroge.reactiveweb.cqs.util.InfrastructureService;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

import static org.springframework.web.reactive.function.BodyInserters.fromPublisher;
import static org.springframework.web.reactive.function.server.ServerResponse.ok;
import static org.springframework.web.reactive.function.server.RequestPredicates.GET;

@Getter
@InfrastructureService
public class UserQueryHandler {

    private final RouterFunction<ServerResponse> routerFunction;
    private final IQueryUserService queryUserService;

    public UserQueryHandler(IQueryUserService queryUserService){
        this.queryUserService = queryUserService;
        this.routerFunction = RouterFunctions.route(GET(""),this::all)
                .and(RouterFunctions.route(GET("/{userId}"),this::getUser));
    }

    public Mono<ServerResponse> getUser(ServerRequest serverRequest){
        return Mono.just(serverRequest.pathVariable("userId"))
                .map(UserId::userId)
                .flatMap(queryUserService::find)
                .flatMap(userDto -> ok().contentType(MediaType.APPLICATION_JSON)
                        .body(fromPublisher(Mono.just(userDto),UserDto.class)));
    }

    public Mono<ServerResponse> all(ServerRequest serverRequest){
        return ok().contentType(MediaType.APPLICATION_JSON)
                .body(fromPublisher(queryUserService.findAll(), UserDto.class));
    }
}
