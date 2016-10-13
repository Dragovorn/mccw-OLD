package com.dragovorn.mccw.exceptions;

public class BuildingException extends RuntimeException {

    public BuildingException(String message) {
        super(message);
    }

    public BuildingException(Throwable throwable) {
        super(throwable);
    }
}