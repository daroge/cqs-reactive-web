package de.daroge.reactiveweb.cqs.domain;

import lombok.Getter;

@Getter
public class UserCreatedEvent extends Event {

    private String domainId;
    private String email;
    private String fistName;
    private String lastName;

    public UserCreatedEvent(User user){
        this.domainId = user.getUserId().getValue();
        this.email = user.getEmail().getValue();
        this.fistName = user.getFullName().getFirstName();
        this.lastName = user.getFullName().getLastName();
    }
}
