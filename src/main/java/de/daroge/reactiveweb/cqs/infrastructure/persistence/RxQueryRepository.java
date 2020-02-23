package de.daroge.reactiveweb.cqs.infrastructure.persistence;

import com.github.davidmoten.rx.jdbc.Database;
import com.github.davidmoten.rx.jdbc.QuerySelect;
import com.github.davidmoten.rx.jdbc.ResultSetMapper;
import de.daroge.reactiveweb.cqs.application.UserDto;
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
        QuerySelect.Builder builder = this.database.select("select * post where domainId=?")
                .parameter(userId.getValue());
        Observable<UserDatabase> observable = getFrom(builder);
        return Mono.from(RxReactiveStreams.toPublisher(observable.map(UserDatabase::toUser)));
    }

    @Override
    public Mono<Boolean> isKnown(Email email) {
        QuerySelect.Builder builder = this.database.select("select * post where email=?")
                .parameter(email.getValue());
        Observable<UserDatabase> observable = getFrom(builder);
        return Mono.from(RxReactiveStreams.toPublisher(observable.isEmpty()));
    }

    @Override
    public Flux<User> findAll() {
        QuerySelect.Builder builder = this.database.select("select * from users");
        Observable<UserDatabase> observable = getFrom(builder);
        return Flux.from(RxReactiveStreams.toPublisher(observable.map(UserDatabase::toUser)));
    }

    private Observable<UserDatabase> getFrom(QuerySelect.Builder builder) {
        return builder.get( rs -> new
                UserDatabase(rs.getInt("id"),
                rs.getString("domainId"),
                rs.getString("firstName"),
                rs.getString("lastName"),
                rs.getString("email")))
                .asObservable();
    }
}
