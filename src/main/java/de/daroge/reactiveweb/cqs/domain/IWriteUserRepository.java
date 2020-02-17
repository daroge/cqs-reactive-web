package de.daroge.reactiveweb.cqs.domain;

import de.daroge.reactiveweb.cqs.util.Repository;
import reactor.core.publisher.Mono;

@Repository
public interface IWriteUserRepository {

    Mono<String> add(User user);
}
