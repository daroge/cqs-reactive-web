package de.daroge.reactiveweb.cqs;

import de.daroge.reactiveweb.cqs.application.UserDto;
import de.daroge.reactiveweb.cqs.domain.User;

public class UserDataTest {

    public static UserBuilder userBuilder(){
        return new UserBuilder();
    }

    public static class UserBuilder{
        private String userId;
        private String firstName;
        private String lastName;
        private String email;

        private UserBuilder(){}

        public UserBuilder withUserId(String userId){
            this.userId = userId;
            return this;
        }

        public UserBuilder withFirstName(String firstName){
            this.firstName = firstName;
            return this;
        }

        public UserBuilder withLastName(String lastName){
            this.lastName = lastName;
            return this;
        }

        public UserBuilder withEmail(String email){
            this.email = email;
            return this;
        }

        public User buildUser(){
            return User.UserFactory.mapKnownUserFrom(userId,firstName,lastName,email);
        }
        public UserDto buildUserDto(){
            return new UserDto(this.userId,this.firstName,this.lastName,this.email);
        }
    }
}

