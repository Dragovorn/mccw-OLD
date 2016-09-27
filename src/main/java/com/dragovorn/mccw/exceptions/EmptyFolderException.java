package com.dragovorn.mccw.exceptions;

public class EmptyFolderException extends RuntimeException {

    public EmptyFolderException(String message) {
        super(message);
    }
}