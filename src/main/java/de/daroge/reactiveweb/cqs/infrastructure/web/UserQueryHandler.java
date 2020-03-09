package de.daroge.reactiveweb.cqs.infrastructure.web;

import de.daroge.reactiveweb.cqs.application.UserDto;
import de.daroge.reactiveweb.cqs.application.service.api.IQueryUserService;
import de.daroge.reactiveweb.cqs.domain.UserId;
import de.daroge.reactiveweb.cqs.util.InfrastructureService;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

import static org.springframework.web.reactive.function.BodyInserters.fromPublisher;
import static org.springframework.web.reactive.function.server.RequestPredicates.*;
import static org.springframework.web.reactive.function.server.RequestPredicates.GET;
import static org.springframework.web.reactive.function.server.RouterFunctions.route;
import static org.springframework.web.reactive.function.server.ServerResponse.ok;

@Slf4j
@Getter
@InfrastructureService
public class UserQueryHandler {

    private final IQueryUserService queryUserService;

    public UserQueryHandler(IQueryUserService queryUserService){
        this.queryUserService = queryUserService;
    }

    public Mono<ServerResponse> getUser(ServerRequest serverRequest){
        String userId = serverRequest.pathVariable("userId");
        log.debug("new request for "+ userId);
        return Mono.just(userId)
                .map(UserId::userId)
                .flatMap(queryUserService::find)
                .flatMap(userDto -> ok().contentType(MediaType.APPLICATION_JSON)
                        .body(fromPublisher(Mono.just(userDto),UserDto.class)));
    }

    public Mono<ServerResponse> all(ServerRequest serverRequest){
        log.debug("new request for all users");
        return ok().contentType(MediaType.APPLICATION_JSON)
                .body(fromPublisher(queryUserService.findAll(), UserDto.class));
    }

    @Configuration
    public class UserQueryRouter{
        @Bean public RouterFunction<ServerResponse> userQueryRoute(UserQueryHandler userQueryHandler){
            return RouterFunctions.nest(accept(MediaType.APPLICATION_JSON).and(path("/users")),route(GET("/{userId}"),userQueryHandler::getUser)
                    .and(route(GET(""),userQueryHandler::all)));
        }
    }
}
