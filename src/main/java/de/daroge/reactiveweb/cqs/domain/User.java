package de.daroge.reactiveweb.cqs.domain;

import de.daroge.reactiveweb.cqs.util.AggregateRoot;
import de.daroge.reactiveweb.cqs.util.DomainService;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
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

    @DomainService
    @RequiredArgsConstructor
    public static class UserFactory{
        
        private final IQueryUserRepository queryUserRepository;
        private final IWriteUserRepository writeUserRepository;
        private final EventPublisher publisher;

        public Mono<User> newUser(UserId userId, FullName fullName, Email email){
                return (queryUserRepository.isKnown(email))
                    .flatMap(unKnown -> unKnown ? createUser(userId,fullName,email) : Mono.error(new UserAlreadyInUse(email.getValue())));
        }

        public static User mapKnownUserFrom(String id, String firstName, String lastName, String email){
            return new User(UserId.userId(id),FullName.fullName(firstName,lastName),Email.email(email));
        }

        private Mono<User> createUser(UserId userId,FullName fullName,Email email){
            User user = new User(userId,fullName,email);
            writeUserRepository.add(user);
            UserCreatedEvent userCreatedEvent = new UserCreatedEvent(user);
            publisher.publish(userCreatedEvent);
            return Mono.just(user);
        }
    }
}
