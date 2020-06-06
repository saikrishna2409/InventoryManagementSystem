package com.product.exception;

import java.util.Date;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
@RestController
public class ExceptionHandlerControllerAdvice extends ResponseEntityExceptionHandler{

	public ExceptionHandlerControllerAdvice() {
		// TODO Auto-generated constructor stub
	}
	
	@ExceptionHandler(DataNotFoundException.class)
	public final ResponseEntity<ExceptionResponse> handleDataNotFoundException(DataNotFoundException exception, WebRequest request) {
		ExceptionResponse error = new ExceptionResponse();
		error.setTimeStamp(new Date());
		error.setErrorMessage(exception.getMessage());
		return new ResponseEntity<ExceptionResponse>(error, HttpStatus.NOT_FOUND);
	}
	
	@ExceptionHandler(Exception.class)
	public ResponseEntity<ExceptionResponse> handleAllException(Exception exception, WebRequest request) {
		ExceptionResponse error = new ExceptionResponse();
		error.setErrorMessage(exception.getMessage());
		error.setTimeStamp(new Date());
		return new ResponseEntity<ExceptionResponse>(error, HttpStatus.INTERNAL_SERVER_ERROR);
	}
}
