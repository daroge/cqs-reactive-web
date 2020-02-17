package de.daroge.reactiveweb.cqs.infrastructure.persistence;

import com.github.davidmoten.rx.jdbc.Database;
import de.daroge.reactiveweb.cqs.domain.Email;
import de.daroge.reactiveweb.cqs.domain.IQueryUserRepository;
import de.daroge.reactiveweb.cqs.domain.User;
import de.daroge.reactiveweb.cqs.domain.UserId;
import de.daroge.reactiveweb.cqs.util.InfrastructureService;
import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import rx.Observable;
import rx.RxReactiveStreams;

@InfrastructureService
@RequiredArgsConstructor
public class RxQueryRepository implements IQueryUserRepository {


    private Database database;

    @Override
    public Mono<User> findById(UserId userId) {
        return null; // TODO implementation
    }

    @Override
    public Mono<User> findByEmail(Email email) {
        return null; // TODO implementation
    }

    @Override
    public Flux<User> findAll() {
        Observable<UserDatabase> userData = this.database.select("select * from users")
                .get(
                        rs -> new UserDatabase(rs.getString("domainId"),
                                rs.getString("firstName"),
                                rs.getString("lastName"),
                                rs.getString("email")
                        )
                )
                .asObservable();
        return Flux.from(RxReactiveStreams.toPublisher(userData.map(UserDatabase::toUser)));
    }
}
