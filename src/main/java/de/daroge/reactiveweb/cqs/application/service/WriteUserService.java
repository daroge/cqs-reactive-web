package de.daroge.reactiveweb.cqs.application.service;

import de.daroge.reactiveweb.cqs.application.UserDto;
import de.daroge.reactiveweb.cqs.application.service.api.IWriteUserService;
import de.daroge.reactiveweb.cqs.domain.*;
import de.daroge.reactiveweb.cqs.domain.User.UserFactory;
import de.daroge.reactiveweb.cqs.util.ApplicationService;
import reactor.core.publisher.Mono;

import static de.daroge.reactiveweb.cqs.domain.Email.email;
import static de.daroge.reactiveweb.cqs.domain.FullName.fullName;
import static de.daroge.reactiveweb.cqs.domain.UserId.userId;

@ApplicationService
public class WriteUserService implements IWriteUserService {

    private final UserFactory userFactory;
    private final IWriteUserRepository writeUserRepository;

    public WriteUserService(UserFactory userFactory,IWriteUserRepository writeUserRepository) {
        this.userFactory = userFactory;
        this.writeUserRepository = writeUserRepository;
    }

    @Override
    public Mono<String> newUser(UserDto userDto){
        UserId userId = userId(userDto.getUserId());
        FullName fullName = fullName(userDto.getFirstName(),userDto.getLastName());
        Email email = email(userDto.getEmail());
        return Mono.fromCompletionStage(userFactory.newUser(userId,fullName,email))
                .flatMap( u -> Mono.fromCompletionStage(writeUserRepository.add(u)));
    }

}
