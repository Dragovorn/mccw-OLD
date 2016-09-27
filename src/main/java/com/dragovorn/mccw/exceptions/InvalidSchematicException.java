package com.dragovorn.mccw.exceptions;

public class InvalidSchematicException extends RuntimeException {

    public InvalidSchematicException(String message) {
        super(message);
    }

    public InvalidSchematicException(Throwable throwable) {
        super(throwable);
    }
}