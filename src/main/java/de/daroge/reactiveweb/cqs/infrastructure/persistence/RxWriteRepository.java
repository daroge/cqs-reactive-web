package de.daroge.reactiveweb.cqs.infrastructure.persistence;

import com.github.davidmoten.rx.jdbc.Database;
import com.github.davidmoten.rx.jdbc.QuerySelect;
import com.github.davidmoten.rx.jdbc.QueryUpdate;
import de.daroge.reactiveweb.cqs.domain.EventPublisher;
import de.daroge.reactiveweb.cqs.domain.IWriteUserRepository;
import de.daroge.reactiveweb.cqs.domain.User;
import de.daroge.reactiveweb.cqs.util.InfrastructureService;
import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Mono;
import rx.Single;

@InfrastructureService
@RequiredArgsConstructor
public class RxWriteRepository implements IWriteUserRepository {

    private Database database;
    private EventPublisher eventPublisher;

    @Override
    public Mono<String> add(User user) {
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
