package de.daroge.reactiveweb.cqs.application;

import de.daroge.reactiveweb.cqs.UserDataTest;
import de.daroge.reactiveweb.cqs.application.service.WriteUserService;
import de.daroge.reactiveweb.cqs.domain.*;
import de.daroge.reactiveweb.cqs.domain.User.UserFactory;
import org.assertj.core.api.SoftAssertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import reactor.core.publisher.Mono;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class WriteUserServiceTest {

    @Mock UserFactory userFactory;
    @Mock IWriteUserRepository writeUserRepository;
    @InjectMocks private WriteUserService writeUserService;

    @BeforeEach void setUp(){
        User user = UserDataTest.userBuilder().withUserId("sgswtz25wsd52gstewr").withFirstName("albert").withLastName("smith")
                .withEmail("smith@mail.com")
                .buildUser();
        when(userFactory.newUser(any(UserId.class),any(FullName.class), any(Email.class))).thenReturn(Mono.just(user));
        when(writeUserRepository.add(any(User.class))).thenReturn(Mono.just("sgswtz25wsd52gstewr"));
    }

    @ParameterizedTest
    @ValueSource(strings = {"sgswtz25wsd52gstewr"})
    void test_newUser_with_userId(String userId){
        UserDto userDto = UserDataTest.userBuilder().withUserId(userId).withFirstName("albert").withLastName("smith")
                .withEmail("smith@mail.com").buildUserDto();
        SoftAssertions.assertSoftly(softly -> softly.assertThat(writeUserService.newUser(userDto).block()).isEqualTo(userId));
    }

    @Test void test_newUser_without_userId(){
        UserDto userDto = UserDataTest.userBuilder().withFirstName("albert").withLastName("smith")
                .withEmail("smith@mail.com").buildUserDto();
        SoftAssertions.assertSoftly(softly -> softly.assertThat(writeUserService.newUser(userDto).block()).isNotEmpty());
    }
}
