package de.daroge.reactiveweb.cqs.domain;

import de.daroge.reactiveweb.cqs.util.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Repository
public interface IQueryUserRepository {

    Mono<User> findById(UserId userId);
    Mono<User> findByEmail(Email email);
    Flux<User> findAll();
}
