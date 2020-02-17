package de.daroge.reactiveweb.cqs.util;

import org.springframework.core.annotation.AliasFor;
import org.springframework.stereotype.Service;

import java.lang.annotation.*;

@Service
@Documented
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface ApplicationService {
    @AliasFor(annotation = Service.class)
    String value() default "";
}
