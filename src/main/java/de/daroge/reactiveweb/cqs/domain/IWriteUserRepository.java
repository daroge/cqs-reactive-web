package de.daroge.reactiveweb.cqs.domain;

import de.daroge.reactiveweb.cqs.util.Repository;

import java.util.concurrent.CompletionStage;

@Repository
public interface IWriteUserRepository {

    CompletionStage<String> add(User user);
}
