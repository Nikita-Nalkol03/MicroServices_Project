package com.tka.exception;

import org.springframework.http.HttpStatus;

public class MissingParameterException extends RuntimeException {

	private String message;
	private HttpStatus status;
	
	public MissingParameterException(String message) {
		super();
		this.message = message;
		this.status = HttpStatus.BAD_REQUEST;
	}

	public String getMessage() {
		return message;
	}

	public HttpStatus getStatus() {
		return status;
	}
	
	
}
