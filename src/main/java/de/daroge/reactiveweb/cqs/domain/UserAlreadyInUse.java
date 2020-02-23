package de.daroge.reactiveweb.cqs.domain;

 public class UserAlreadyInUse extends RuntimeException {
    public UserAlreadyInUse(String msg){
        super(msg);
    }
}
