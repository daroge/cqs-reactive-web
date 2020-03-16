package de.daroge.reactiveweb.cqs.application.service;

import de.daroge.reactiveweb.cqs.application.UserDto;
import de.daroge.reactiveweb.cqs.application.service.api.IQueryUserService;
import de.daroge.reactiveweb.cqs.domain.IQueryUserRepository;
import de.daroge.reactiveweb.cqs.domain.UserId;
import de.daroge.reactiveweb.cqs.util.ApplicationService;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.concurrent.Flow;

@ApplicationService
public class QueryUserService implements IQueryUserService {

    private IQueryUserRepository queryUserRepository;

    public QueryUserService(IQueryUserRepository queryUserRepository){
        this.queryUserRepository = queryUserRepository;
    }

    @Override
    public Mono<UserDto> find(UserId userId) {
        return Mono.fromCompletionStage(queryUserRepository.findById(userId))
                .map(UserDto::toDto);
    }

    @Override
    public Flux<UserDto> findAll() {
        return Flux.from(queryUserRepository.findAll())
                .map(UserDto::toDto);
    }
}