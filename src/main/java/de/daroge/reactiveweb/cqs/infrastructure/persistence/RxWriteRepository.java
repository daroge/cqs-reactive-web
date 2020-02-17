package de.daroge.reactiveweb.cqs.infrastructure.persistence;

import de.daroge.reactiveweb.cqs.domain.IWriteUserRepository;
import de.daroge.reactiveweb.cqs.domain.User;
import de.daroge.reactiveweb.cqs.util.InfrastructureService;
import reactor.core.publisher.Mono;

@InfrastructureService
public class RxWriteRepository implements IWriteUserRepository {
    @Override
    public Mono<String> add(User user) {
        return null; // TODO implementation
    }
}
