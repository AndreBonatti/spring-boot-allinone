package br.com.test.springbootdemo.model.exception;

import org.springframework.http.HttpStatus;

public class BusinessException extends RuntimeException{

    public final HttpStatus status;

    public BusinessException(String message, HttpStatus status) {
        super(message);
        this.status = status;
    }

    public HttpStatus getStatus() {
        return status;
    }
}
