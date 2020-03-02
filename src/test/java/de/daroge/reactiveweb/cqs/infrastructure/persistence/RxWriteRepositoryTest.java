package de.daroge.reactiveweb.cqs.infrastructure.persistence;

import de.daroge.reactiveweb.cqs.CQSApplication;
import de.daroge.reactiveweb.cqs.domain.IWriteUserRepository;
import de.daroge.reactiveweb.cqs.domain.User;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import reactor.test.StepVerifier;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = CQSApplication.class)
public class RxWriteRepositoryTest {

    @Autowired
    private IWriteUserRepository writeUserRepository;

    @Test
    public void testNewUser(){
        User user = User.UserFactory.mapKnownUserFrom("asgeu74sfd2ab79","albert","lars","dar@gmx.de");
        StepVerifier.create(writeUserRepository.add(user))
                .expectNext("asgeu74sfd2ab79")
                .verifyComplete();
    }

}
