package de.daroge.reactiveweb.cqs.infrastructure.exception;

import org.springframework.http.HttpStatus;

import java.util.List;
import java.util.Map;
import java.util.Optional;

public abstract class AbstractExceptionHandler {

    protected abstract String getExceptionName();
    protected abstract HttpStatus getStatus();
}
