package com.dms.backend.exception;

import java.io.IOException;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExpectionHandler {

	@ExceptionHandler(IOException.class)
	public ResponseEntity<ExceptionResponse> handleIOException(Exception ex) {
		ExceptionResponse excepResponse = new ExceptionResponse(ex.getMessage(), "An unexpected error occurred", System.currentTimeMillis());
	    return new ResponseEntity<>(excepResponse, HttpStatus.BAD_REQUEST);
	}
	
	@ExceptionHandler(Exception.class)
	public ResponseEntity<ExceptionResponse> handleGlobalException(Exception ex) {
		ExceptionResponse excepResponse = new ExceptionResponse(ex.getMessage(), "An unexpected error occurred", System.currentTimeMillis());
	    return new ResponseEntity<>(excepResponse, HttpStatus.INTERNAL_SERVER_ERROR);
	}
}
