package de.daroge.reactiveweb.cqs;

import com.github.davidmoten.rx.jdbc.Database;
import de.daroge.reactiveweb.cqs.infrastructure.web.UserCommandHandler;
import de.daroge.reactiveweb.cqs.infrastructure.web.UserQueryHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;

import javax.sql.DataSource;

import static org.springframework.web.reactive.function.server.RequestPredicates.*;

@SpringBootApplication
public class CQSApplication {

    public static void main(String[] args) {
        SpringApplication.run(CQSApplication.class);
    }

    @Autowired
    private UserQueryHandler queryUserHandler;

    @Autowired
    private UserCommandHandler commandUserHandler;

    @Bean
    public RouterFunction<ServerResponse> route(){
        return RouterFunctions
                .route(GET("users").and(accept(MediaType.APPLICATION_JSON)),queryUserHandler::all)
                .andRoute(GET("users/{userId}").and(accept(MediaType.APPLICATION_JSON)),queryUserHandler::getUser)
                .andRoute(POST("users").and(accept(MediaType.APPLICATION_JSON)).and(contentType(MediaType.APPLICATION_JSON)),commandUserHandler::newUser);
    }

    @Bean(destroyMethod = "shutdown")
    public DataSource dataSource() {
        return new EmbeddedDatabaseBuilder()
                .setType(EmbeddedDatabaseType.H2)
                .setName("users")
                .addScript("schema.sql")
                .build();
    }

    @Bean
    public Database db(DataSource ds){
        return Database.fromDataSource(ds);
    }
}
