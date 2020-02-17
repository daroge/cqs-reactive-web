package de.daroge.reactiveweb.cqs.domain;

import de.daroge.reactiveweb.cqs.util.ValueObject;
import lombok.Getter;

@Getter
@ValueObject
public class FullName {

    private final String firstName;
    private final String lastName;

    private FullName(String firstName, String lastName){
        this.firstName = firstName;
        this.lastName = lastName;
    }

    public static FullName fullName(String firstName,String lastName){
        return new FullName(firstName,lastName);
    }
}
