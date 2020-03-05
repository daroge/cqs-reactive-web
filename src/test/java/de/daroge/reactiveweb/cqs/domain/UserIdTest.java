package de.daroge.reactiveweb.cqs.domain;

import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.junit.jupiter.api.Assertions.assertThrows;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;

@ExtendWith(SpringExtension.class)
public class UserIdTest {

    @ParameterizedTest
    @ValueSource(strings = {"afdq252gsh44673"})
    public void testCorrectId(String userId){
        assertThat(UserId.userId(userId).getValue(), is(equalTo("afdq252gsh44673")));
    }

    @ParameterizedTest
    @ValueSource(strings = {"h44673"})
    public void testWrongId(String userId){
        assertThrows(IllegalArgumentException.class, () -> UserId.userId(userId));
    }
}
