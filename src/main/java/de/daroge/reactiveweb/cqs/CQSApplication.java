package de.daroge.reactiveweb.cqs;

import com.github.davidmoten.rx.jdbc.Database;
import de.daroge.reactiveweb.cqs.infrastructure.web.UserCommandHandler;
import de.daroge.reactiveweb.cqs.infrastructure.web.UserQueryHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.Banner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.WebApplicationType;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.http.MediaType;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;
import org.springframework.web.reactive.function.server.*;

import static org.springframework.web.reactive.function.server.RequestPredicates.contentType;
import static org.springframework.web.reactive.function.server.RequestPredicates.accept;
import static org.springframework.web.reactive.function.server.RequestPredicates.path;

import javax.sql.DataSource;

@EnableCaching
@SpringBootApplication
public class CQSApplication {

    public static void main(String[] args) {
        SpringApplication application = new SpringApplication(CQSApplication.class);
        application.setBannerMode(Banner.Mode.OFF);
        application.setWebApplicationType(WebApplicationType.REACTIVE);
        application.run(args);
    }

    @Autowired
    private UserQueryHandler queryUserHandler;

    @Autowired
    private UserCommandHandler commandUserHandler;

    @Bean
    public RouterFunction<ServerResponse> route(){
        return RouterFunctions.nest(path("/users").and(accept(MediaType.APPLICATION_JSON)).and(contentType(MediaType.APPLICATION_JSON)),
                commandUserHandler.getRouterFunction().and(queryUserHandler.getRouterFunction()));
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
