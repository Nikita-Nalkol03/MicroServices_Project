package com.tka.exception;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobleExceptionHandler {

	@ExceptionHandler(ResourceNotFoundException.class)
	public ResponseEntity<ErrorResponse> handleResourceNotFoundException(ResourceNotFoundException ex){
		ErrorResponse response=new ErrorResponse(ex.getMessage(), ex.getStatus());
		return new ResponseEntity<>(response, ex.getStatus());
	}
	
	@ExceptionHandler(BadRequestException.class)
	public ResponseEntity<ErrorResponse> handleBadRequestException(BadRequestException ex){
		ErrorResponse response=new ErrorResponse(ex.getMessage(), ex.getStatus());
		return new ResponseEntity<>(response, ex.getStatus());
	}
	
	@ExceptionHandler(MissingParameterException.class)
	public ResponseEntity<ErrorResponse> handleMissingParametrException(MissingParameterException ex){
		ErrorResponse response=new ErrorResponse(ex.getMessage(), ex.getStatus());
		return new ResponseEntity<>(response, ex.getStatus());
	}
}
