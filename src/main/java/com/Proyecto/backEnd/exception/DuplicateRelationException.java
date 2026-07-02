package com.Proyecto.backEnd.exception;

public class DuplicateRelationException extends RuntimeException {
    public DuplicateRelationException(String message) {
        super(message);
    }
}