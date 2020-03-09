package de.daroge.reactiveweb.cqs.infrastructure.web;

import de.daroge.reactiveweb.cqs.UserDataTest;
import de.daroge.reactiveweb.cqs.application.UserDto;
import de.daroge.reactiveweb.cqs.application.service.api.IQueryUserService;
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
@ContextConfiguration(classes = {UserQueryHandler.UserQueryRouter.class,UserQueryHandler.class})
public class UserQueryHandlerTest {

    @Autowired private WebTestClient webTestClient;
    @Autowired private ApplicationContext context;
    @MockBean private IQueryUserService queryUserService;

    @BeforeEach void setUp(){
        UserDto userDto = UserDataTest.userBuilder().withUserId(UUID.randomUUID().toString()).withFirstName("albert").withLastName("smith")
                .withEmail("smith@mail.com").buildUserDto();
        webTestClient = WebTestClient.bindToApplicationContext(context).build();
        when(queryUserService.find(any(UserId.class))).thenReturn(Mono.just(userDto));
    }

    @Test void test_getUser_with_valid_userId(){
        String userId = UUID.randomUUID().toString();
        webTestClient.get()
                .uri("/users/{userId}",userId)
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus().is2xxSuccessful();
        SoftAssertions.assertSoftly(softly -> softly.assertThat(verify(queryUserService,times(1)).find(any(UserId.class))));
    }

    @Test void test_getUser_with_invalid_userId(){
        String userId = "sgsgafaf";
        webTestClient.get()
                .uri("/users/{userId}",userId)
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus().is5xxServerError();
        SoftAssertions.assertSoftly(softly -> softly.assertThat(verify(queryUserService,times(0)).find(any(UserId.class))));
    }
}

