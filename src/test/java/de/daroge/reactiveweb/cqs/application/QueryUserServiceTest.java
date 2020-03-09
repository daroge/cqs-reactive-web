package de.daroge.reactiveweb.cqs.application;

import de.daroge.reactiveweb.cqs.application.service.QueryUserService;
import de.daroge.reactiveweb.cqs.domain.IQueryUserRepository;
import de.daroge.reactiveweb.cqs.domain.User;
import de.daroge.reactiveweb.cqs.domain.UserId;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import reactor.core.publisher.Mono;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class QueryUserServiceTest {

    @Mock
    private IQueryUserRepository queryUserRepository;

    @InjectMocks
    private QueryUserService queryUserService;

    @ParameterizedTest
    @ValueSource(strings = {"afdsg3637dshdshs373"})
    void testFind(String id){

        UserId userId = UserId.userId(id);
        User user = User.UserFactory.mapKnownUserFrom(id,"albert","smith","reg@mail.com");

        when(queryUserRepository.findById(any(UserId.class))).thenReturn(Mono.just(user));

        assertThat(queryUserService.find(userId).block()).isInstanceOf(UserDto.class);
        assertThat(queryUserService.find(userId).block().getUserId()).isEqualTo(id);
    }
}
