package de.daroge.reactiveweb.cqs.domain;

import de.daroge.reactiveweb.cqs.util.AggregateRoot;
import lombok.Getter;
import reactor.core.publisher.Mono;

@Getter
@AggregateRoot
public class User {

    private final UserId userId;
    private final FullName fullName;
    private final Email email;

    private User(UserId userId,FullName fullName, Email email){
        this.userId = userId;
        this.fullName = fullName;
        this.email = email;
    }


    public static class UserFactory{

        private IQueryUserRepository queryUserRepository;
        UserFactory(IQueryUserRepository queryUserRepository){
            this.queryUserRepository = queryUserRepository;
        }

        public Mono<User> newUser(final UserId userId, final FullName fullName, final Email email){
            return queryUserRepository.findByEmail(email)
                    .doOnNext( user ->  Mono.error(new UserAlreadyInUse(user.email.getValue())))
                    .switchIfEmpty(Mono.defer(() -> Mono.just( new User(userId,fullName,email))));
        }
    }
}
