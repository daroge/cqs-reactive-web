package de.daroge.reactiveweb.cqs.infrastructure.persistence;

import com.github.davidmoten.rx.jdbc.Database;
import com.github.davidmoten.rx.jdbc.QueryUpdate;
import de.daroge.reactiveweb.cqs.domain.IWriteUserRepository;
import de.daroge.reactiveweb.cqs.domain.User;
import de.daroge.reactiveweb.cqs.util.InfrastructureService;
import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Mono;

@Slf4j
@InfrastructureService
public class RxWriteRepository implements IWriteUserRepository {

    private Database database;

    public RxWriteRepository(Database database){
        this.database = database;
    }

    @Override
    public Mono<String> add(User user) {
        log.debug("new userId " + user.getUserId().getValue());
        QueryUpdate.Builder builder = this.database.update("insert into users(domainId,firstName,lastName,email)" +
                        "values(?,?,?,?)")
                        .parameter(user.getUserId().getValue())
                .parameter(user.getFullName().getFirstName())
                .parameter(user.getFullName().getLastName())
                .parameter(user.getEmail().getValue());
        int key = builder.execute();
        return Mono.just(user.getUserId().getValue());
    }
}
