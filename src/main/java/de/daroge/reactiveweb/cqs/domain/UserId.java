package de.daroge.reactiveweb.cqs.domain;

import lombok.Getter;
import static java.util.UUID.randomUUID;

@Getter
public class UserId {

    private final String value;

    private UserId(){
        this(randomUUID().toString());
    }

    private UserId(String userId){
        this.value = userId;
    }

    private static boolean isValid(String data){
        return data.matches("[a-z0-9-]{10,}");
    }

    private static void assertValid(String data){
        if(!isValid(data)){
            throw new IllegalArgumentException("The Syntax of the provided id is wrong");
        }
    }

    public static UserId userId(String id){
        if(id == null){
            return new UserId();
        }
        assertValid(id);
        return new UserId(id);
    }
}
