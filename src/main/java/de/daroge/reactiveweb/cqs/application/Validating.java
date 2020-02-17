package de.daroge.reactiveweb.cqs.application;

import javax.validation.*;
import java.util.Set;

 abstract class Validating<T> {
    private Validator validator;

    Validating(){
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    public void validate(){
        Set<ConstraintViolation<T>> violations = validator.validate((T)this);
        if(!violations.isEmpty())
            throw new ConstraintViolationException(violations);
    }

}
