package de.daroge.reactiveweb.cqs.infrastructure.persistence;

import de.daroge.reactiveweb.cqs.CQSApplication;
import de.daroge.reactiveweb.cqs.domain.Email;
import de.daroge.reactiveweb.cqs.domain.IQueryUserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import reactor.test.StepVerifier;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;

@Sql("classpath:schema.sql")
@Sql("classpath:data.sql")
@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = CQSApplication.class)
public class RxQueryRepositoryTest {

    @Autowired
    private IQueryUserRepository queryUserRepository;

    @Test
    public void testIsNewFalse(){
        StepVerifier.create(queryUserRepository.isKnown(Email.email("cl@gmx.de")))
                .expectNext(false)
                .verifyComplete();
    }

    @Test
    public void testIsNewTrue(){
        StepVerifier.create(queryUserRepository.isKnown(Email.email("alain@gmx.de")))
                .expectNext(true)
                .verifyComplete();
    }

    @Test
    public void testFindAll(){
        assertThat(2,is(equalTo(queryUserRepository.findAll().collectList().block().size())));
    }

}
