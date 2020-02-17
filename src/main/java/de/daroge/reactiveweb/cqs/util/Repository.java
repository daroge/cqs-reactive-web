package de.daroge.reactiveweb.cqs.util;

import org.springframework.core.annotation.AliasFor;

import java.lang.annotation.*;

@org.springframework.stereotype.Repository
@Documented
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface Repository {
    @AliasFor(annotation = org.springframework.stereotype.Repository.class)
    String value() default "";
}
