package de.daroge.reactiveweb.cqs.application.service;

import de.daroge.reactiveweb.cqs.application.UserDto;
import de.daroge.reactiveweb.cqs.application.service.api.IQueryUserService;
import de.daroge.reactiveweb.cqs.domain.IQueryUserRepository;
import de.daroge.reactiveweb.cqs.domain.UserId;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public class QueryUserService implements IQueryUserService {

    private IQueryUserRepository queryUserRepository;

    @Override
    public Mono<UserDto> find(UserId userId) {
        return queryUserRepository.findById(userId)
                .map(UserDto::toDto)
                .doOnError(e -> Mono.empty());
    }

    @Override
    public Flux<UserDto> findAll() {
        return queryUserRepository.findAll()
                .map(UserDto::toDto);
    }
}