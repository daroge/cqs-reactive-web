package de.daroge.reactiveweb.cqs.infrastructure.persistence;

import com.github.davidmoten.rx.jdbc.Database;
import com.github.davidmoten.rx.jdbc.QuerySelect;
import de.daroge.reactiveweb.cqs.domain.Email;
import de.daroge.reactiveweb.cqs.domain.IQueryUserRepository;
import de.daroge.reactiveweb.cqs.domain.User;
import de.daroge.reactiveweb.cqs.domain.UserId;
import de.daroge.reactiveweb.cqs.util.InfrastructureService;
import org.reactivestreams.Publisher;
import rx.Observable;
import rx.RxReactiveStreams;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionStage;

@InfrastructureService
public class RxQueryRepository implements IQueryUserRepository {

    private Database database;
    public RxQueryRepository(Database database){
        this.database = database;
    }

    @Override
    public CompletionStage<User> findById(UserId userId) {
        QuerySelect.Builder builder = this.database.select("SELECT * FROM USERS WHERE domainId=?")
                .parameter(userId.getValue());
        Observable<User> observable = getFrom(builder).map(UserDatabase::toUser);
        CompletableFuture<User> result = new CompletableFuture<>();
        observable.doOnError(result::completeExceptionally)
                .forEach(result::complete);
        return result;
    }

    @Override
    public CompletionStage<Boolean> isKnown(Email email) {
        QuerySelect.Builder builder = this.database.select("SELECT * FROM USERS WHERE email=?")
                .parameter(email.getValue());
        Observable<UserDatabase> observable = getFrom(builder);
        Observable<Boolean> observableResult = observable.isEmpty();
        final CompletableFuture<Boolean> result = new CompletableFuture<>();
        observableResult.doOnError(result::completeExceptionally)
                .forEach(result::complete);
        return result;
    }

    @Override
    public Publisher<User> findAll() {
        QuerySelect.Builder builder = this.database.select("select * from users");
        Observable<User> observable = getFrom(builder).map(UserDatabase::toUser);
        return RxReactiveStreams.toPublisher(observable);
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
