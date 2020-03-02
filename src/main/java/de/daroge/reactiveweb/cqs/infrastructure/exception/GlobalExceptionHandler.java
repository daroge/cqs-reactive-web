package de.daroge.reactiveweb.cqs.infrastructure.exception;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Value;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.annotation.Order;
import org.springframework.core.io.buffer.DefaultDataBufferFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.support.WebExchangeBindException;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Slf4j
@Order(-2)
@Component
public class GlobalExceptionHandler implements org.springframework.web.server.WebExceptionHandler {

    private final ObjectMapper objectMapper;
    private final ExceptionHandlerManager exceptionHandlerManager;

    public GlobalExceptionHandler(ObjectMapper mapper,ExceptionHandlerManager manager){
        log.info("global handler initialisation");
        this.objectMapper = mapper;
        this.exceptionHandlerManager = manager;
    }

    @Override
    public Mono<Void> handle(ServerWebExchange exchange, Throwable ex) {
        if (ex instanceof WebExchangeBindException) {
            WebExchangeBindException webExchangeBindException = (WebExchangeBindException) ex;
            log.debug("errors:" + webExchangeBindException.getFieldErrors());
            var errors = new Errors("Validation failed.");
            webExchangeBindException.getFieldErrors().forEach(e -> errors.add(e.getField(), e.getDefaultMessage()));
            log.debug("handled errors::" + errors);
            return setExchangeResponse(HttpStatus.UNPROCESSABLE_ENTITY,exchange,errors);
        } else  {
            Optional<HttpStatus> optionalHttpStatus = exceptionHandlerManager.getStatusForException(ex);
            Error error = new Error(null,ex.getMessage());
            HttpStatus status = optionalHttpStatus.orElseGet(() -> HttpStatus.BAD_REQUEST);
            return setExchangeResponse(status,exchange,error);
        }
    }

    private Mono<Void> setExchangeResponse( HttpStatus status, ServerWebExchange exchange, Object error){
        try {
            exchange.getResponse().setStatusCode(status);
            exchange.getResponse().getHeaders().setContentType(MediaType.APPLICATION_JSON);
            var db = new DefaultDataBufferFactory().wrap(objectMapper.writeValueAsBytes(error));
            return exchange.getResponse().writeWith(Mono.just(db));
        }catch (JsonProcessingException e) {
            e.printStackTrace();
            return Mono.empty();
        }
    }

    @Value
    class Errors implements Serializable {
        private String message;
        private List<Error> errors = new ArrayList<>();

        @JsonCreator
        Errors(String message) {
            this.message = message;
        }

        public void add(String path, String message) {
            this.errors.add(new Error(path, message));
        }
    }

    @Value
    class Error implements Serializable {
        private String path;
        private String message;

        @JsonCreator
        Error(String path, String message) {
            this.path = path;
            this.message = message;
        }
    }
}