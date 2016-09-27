package com.dragovorn.mccw.exceptions;

public class SchematicNotFoundException extends RuntimeException {

    public SchematicNotFoundException(String message) {
        super(message);
    }
}