package de.daroge.reactiveweb.cqs.infrastructure.exception;

import de.daroge.reactiveweb.cqs.domain.UserAlreadyInUse;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

@Component
public class UserAlreadyInUseHandler extends AbstractExceptionHandler{

    @Override
    protected String getExceptionName() {
        return UserAlreadyInUse.class.getName();
    }
    @Override
    public HttpStatus getStatus() {
        return HttpStatus.CONFLICT;
    }
}
