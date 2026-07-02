package com.Proyecto.backEnd.exception;

public class DuplicateItemMateriaException extends RuntimeException {
    public DuplicateItemMateriaException(String message) {
        super(message);
    }
}