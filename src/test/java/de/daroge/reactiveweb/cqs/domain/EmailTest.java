package de.daroge.reactiveweb.cqs.domain;

import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.assertThrows;

@ExtendWith(SpringExtension.class)
public class EmailTest {

    @ParameterizedTest
    @ValueSource(strings = {"daro_r@gmx.de"})
    public void testCorrectEmail(String email){
        assertThat(Email.email(email).getValue(),is(equalTo("daro_r@gmx.de")));
    }

    @ParameterizedTest
    @ValueSource(strings = {"daro_rgmx.de"})
    public void testWrongEmail(String email){
        assertThrows(IllegalArgumentException.class,()-> Email.email(email));
    }
}
