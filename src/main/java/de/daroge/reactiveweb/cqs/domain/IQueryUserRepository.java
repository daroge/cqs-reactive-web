package de.daroge.reactiveweb.cqs.domain;

import de.daroge.reactiveweb.cqs.util.Repository;
import org.reactivestreams.Publisher;

import java.util.concurrent.CompletionStage;

@Repository
public interface IQueryUserRepository {

    CompletionStage<User> findById(UserId userId);
    CompletionStage<Boolean> isKnown(Email email);
    Publisher<User> findAll();
}
