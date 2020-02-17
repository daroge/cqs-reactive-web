package de.daroge.reactiveweb.cqs.infrastructure.persistence;

import de.daroge.reactiveweb.cqs.domain.User;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.Value;

@Value
class UserDatabase {

    private String domainId;
    private String firstName;
    private String lastName;
    private String email;

    static User toUser(UserDatabase userDatabase){
        return null; // TODO implementation
    }
}
