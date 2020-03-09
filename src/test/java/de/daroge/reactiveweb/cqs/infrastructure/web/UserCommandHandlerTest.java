package de.daroge.reactiveweb.cqs.infrastructure.web;

import de.daroge.reactiveweb.cqs.UserDataTest;
import de.daroge.reactiveweb.cqs.application.UserDto;
import de.daroge.reactiveweb.cqs.application.service.api.IWriteUserService;
import de.daroge.reactiveweb.cqs.domain.UserId;
import org.assertj.core.api.SoftAssertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.ApplicationContext;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Mono;

import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@WebFluxTest
@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = {UserCommandHandler.UserCommandRouter.class,UserCommandHandler.class})
public class UserCommandHandlerTest {

    @Autowired WebTestClient webTestClient;
    @Autowired private ApplicationContext context;
    @MockBean private IWriteUserService writeUserService;

    @BeforeEach public void setUp(){
        String userId = UUID.randomUUID().toString();
        webTestClient = WebTestClient.bindToApplicationContext(context).build();
        when(writeUserService.newUser(any())).thenReturn(Mono.just(userId));
    }

    @Test void test_valid_newUser(){
        UserDto userDto = UserDataTest.userBuilder().withFirstName("albert").withLastName("smith")
                .withEmail("smith@mail.com").buildUserDto();
        webTestClient.post()
                .uri("/users")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .body(Mono.just(userDto),UserDto.class)
                .exchange()
                .expectStatus().is2xxSuccessful();
        SoftAssertions.assertSoftly(softly -> softly.assertThat(verify(writeUserService,times(1)).newUser(any())));
    }

    @Test void test_invalid_newUser_without_firstName(){
        UserDto userDto = UserDataTest.userBuilder().withLastName("smith")
                .withEmail("smith@mail.com").buildUserDto();
        webTestClient.post()
                .uri("/users")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .body(Mono.just(userDto),UserDto.class)
                .exchange()
                .expectStatus().is5xxServerError();
        SoftAssertions.assertSoftly(softly -> softly.assertThat(verify(writeUserService,times(0)).newUser(userDto)));
    }
}
