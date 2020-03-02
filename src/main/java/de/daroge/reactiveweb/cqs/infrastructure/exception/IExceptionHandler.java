package de.daroge.reactiveweb.cqs.infrastructure.exception;

import org.springframework.http.HttpStatus;

public interface IExceptionHandler {
    String getExceptionName();
    HttpStatus getStatus();
}
