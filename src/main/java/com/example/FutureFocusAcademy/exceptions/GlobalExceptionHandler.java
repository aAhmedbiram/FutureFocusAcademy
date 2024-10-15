package com.example.FutureFocusAcademy.exceptions;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.LocalDateTime;
import java.util.Locale;

@ControllerAdvice
public class GlobalExceptionHandler {
    @Autowired
    MessageSource messageSource;

    @ExceptionHandler(CustomException.class)
    public ResponseEntity<ExceptionResponse> customExceptionHandler(CustomException ex){
        String message =messageSource.getMessage(ex.getMessage(),null , Locale.ENGLISH);
        return new ResponseEntity<ExceptionResponse>(
                new ExceptionResponse(message,ex.getStatus(),ex.getStatus().value(), LocalDateTime.now()).getStatus());
    }

    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<ExceptionResponse> runtimeExceptionHandler(RuntimeException ex){
        return exceptionHandler(ex,HttpStatus.INTERNAL_SERVER_ERROR,HttpStatus.INTERNAL_SERVER_ERROR.value(), ex.getMessage());
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ExceptionResponse> invalidArgumentHandler(MethodArgumentNotValidException ex){
        return exceptionHandler(ex,HttpStatus.BAD_REQUEST,HttpStatus.BAD_REQUEST.value(), ex.getMessage());
    }
    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ExceptionResponse> invalidArgumentHandler(IllegalArgumentException ex){
        return exceptionHandler(ex,HttpStatus.BAD_REQUEST,HttpStatus.BAD_REQUEST.value(), ex.getMessage());
    }

    private ResponseEntity<ExceptionResponse> exceptionHandler(Exception ex, HttpStatus status,int code,String message){
        String messsage;
        try{
            messsage=messageSource.getMessage(message,null,Locale.ENGLISH);
        }catch (Exception exception){
            System.err.println("couldn't find the message in resources");
            messsage=message;
        }
        return  new ResponseEntity<ExceptionResponse>(
                new ExceptionResponse(message,status ,code,LocalDateTime.now()).getStatus());
    }
}
