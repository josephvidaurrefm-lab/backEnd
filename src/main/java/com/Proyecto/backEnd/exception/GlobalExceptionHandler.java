package com.Proyecto.backEnd.exception;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;

import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import lombok.extern.slf4j.Slf4j;

@ControllerAdvice
@Slf4j
public class GlobalExceptionHandler {
	
    @ExceptionHandler(value= ResourceNotFoundException.class)
        public ResponseEntity<ErrorResponse> handleResourceNotFoundException(ResourceNotFoundException ex){
       return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ErrorResponse(HttpStatus.NOT_FOUND.value(),
                ex.getMessage()));
    }
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleCommonException(Exception ex){
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR.value(),ex.getMessage()));
    }

    @ExceptionHandler(ArithmeticException.class)
    public ResponseEntity<ErrorResponse> ArithmeticException(ArithmeticException ex) {
         String errorMessage = ex.getMessage(); 

         String userFriendlyMessage = "Error de cálculo. Por favor, revise los valores numéricos ingresados. Detalle: " + errorMessage;

         return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                              .body(new ErrorResponse(
                                  HttpStatus.BAD_REQUEST.value(),
                                  userFriendlyMessage
                              ));
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> handleMethodArgumentNotValidException(MethodArgumentNotValidException ex) {
        List<String> errorMessages = ex.getBindingResult().getFieldErrors()
                .stream().map(FieldError::getDefaultMessage).collect(Collectors.toList());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
        		.body(new ErrorResponse(
        			HttpStatus.BAD_REQUEST.value(),
                	errorMessages.toString()
                ));
    }
    
    @ExceptionHandler(value= MethodArgumentTypeMismatchException.class)
    public ResponseEntity<ErrorResponse> handleMethodArgumentTypeMismatchException(MethodArgumentTypeMismatchException ex){
        return ResponseEntity.status(HttpStatus.METHOD_NOT_ALLOWED).body(new ErrorResponse(HttpStatus.METHOD_NOT_ALLOWED.value(),
                ex.getMessage()));
    }
    @ExceptionHandler(DuplicateRelationException.class)
    public ResponseEntity<ErrorResponse> handleDuplicateRelationException(DuplicateRelationException ex) {
        return ResponseEntity.status(HttpStatus.CONFLICT)
                .body(new ErrorResponse(
                        HttpStatus.CONFLICT.value(),
                        ex.getMessage()
                ));
    }


    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<ErrorResponse> handleDataIntegrityViolation(DataIntegrityViolationException ex) {
        return ResponseEntity.status(HttpStatus.CONFLICT)
                .body(new ErrorResponse(409, "Ya existe una materia con ese código."));
    }

    
}
