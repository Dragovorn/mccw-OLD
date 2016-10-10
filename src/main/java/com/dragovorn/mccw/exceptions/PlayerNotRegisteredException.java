package com.dragovorn.mccw.exceptions;

public class PlayerNotRegisteredException extends RuntimeException {

    public PlayerNotRegisteredException(String message) {
        super(message);
    }
}