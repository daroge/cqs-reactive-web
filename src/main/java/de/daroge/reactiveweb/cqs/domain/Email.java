package de.daroge.reactiveweb.cqs.domain;

import de.daroge.reactiveweb.cqs.util.ValueObject;
import lombok.Getter;

import java.util.regex.Pattern;

@ValueObject
@Getter
public class Email {

    private static final String EMAIL_REGEX = "^[_A-Za-z0-9-]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
    private static final Pattern PATTERN = Pattern.compile(EMAIL_REGEX);

    private final String value;

    private Email(String email){
        this.value = email;
    }

    private static boolean isValid(String data){
        return PATTERN.matcher(data).matches();
    }

    private static void assertValid(String data){
        if(!isValid(data)){
            throw new IllegalArgumentException("The syntax for the given email is wrong");
        }
    }

    public static Email email(String email){
        assertValid(email);
        return new Email(email);
    }
}
