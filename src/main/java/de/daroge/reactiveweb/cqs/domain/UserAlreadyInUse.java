package de.daroge.reactiveweb.cqs.domain;

 class UserAlreadyInUse extends Exception {
    UserAlreadyInUse(String msg){
        super(msg);
    }
}
