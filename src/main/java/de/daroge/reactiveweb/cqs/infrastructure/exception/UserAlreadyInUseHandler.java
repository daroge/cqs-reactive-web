package de.daroge.reactiveweb.cqs.infrastructure.exception;

import de.daroge.reactiveweb.cqs.domain.UserAlreadyInUse;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

@Component
public class UserAlreadyInUseHandler implements IExceptionHandler {

    @Override
    public String getExceptionName() {
        return UserAlreadyInUse.class.getName();
    }
    @Override
    public HttpStatus getStatus() {
        return HttpStatus.CONFLICT;
    }
}
