package de.daroge.reactiveweb.cqs.infrastructure.exception;

import org.apache.commons.lang.exception.ExceptionUtils;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;

@Component
public class ExceptionHandlerManager {

    private final Map<String,AbstractExceptionHandler> handlers;

    public ExceptionHandlerManager(List<AbstractExceptionHandler> handlers) {
        this.handlers = handlers.stream().collect(Collectors.toMap(AbstractExceptionHandler::getExceptionName, Function.identity()));
    }

    public Optional<HttpStatus> getStatusForException(Throwable exception){
        AbstractExceptionHandler handler;
        exception =  ExceptionUtils.getRootCause(exception);
        handler = handlers.get(exception.getClass().getSimpleName());
        if(handler != null){
            return Optional.of(handler.getStatus());
        }
        return Optional.empty();
    }
}
