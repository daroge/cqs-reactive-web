package de.daroge.reactiveweb.cqs.infrastructure.persistence;

import static de.daroge.reactiveweb.cqs.domain.User.UserFactory.mapKnownUserFrom;

import de.daroge.reactiveweb.cqs.domain.User;
import lombok.Value;

@Value
public class UserDatabase {

    private int id;
    private String domainId;
    private String firstName;
    private String lastName;
    private String email;

    static User toUser(UserDatabase userDatabase){
        return mapKnownUserFrom(userDatabase.domainId,userDatabase.firstName,userDatabase.lastName,userDatabase.email);
    }
}
