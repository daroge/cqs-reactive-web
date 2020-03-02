package de.daroge.reactiveweb.cqs.infrastructure.exception;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;

@Component
public class ExceptionHandlerManager {

    private final Map<String, IExceptionHandler> handlers;

    public ExceptionHandlerManager(List<IExceptionHandler> handlers) {
        this.handlers = handlers.stream().collect(Collectors.toMap(IExceptionHandler::getExceptionName, Function.identity()));
    }

    public Optional<HttpStatus> getStatusForException(Throwable exception){
        IExceptionHandler handler;
        Throwable ex = exception;
        while (ex.getCause() != null && ex.getCause() != ex){
            ex = ex.getCause();
        }
        handler = handlers.get(ex.getClass().getSimpleName());
        if(handler != null){
            return Optional.of(handler.getStatus());
        }
        return Optional.empty();
    }
}
