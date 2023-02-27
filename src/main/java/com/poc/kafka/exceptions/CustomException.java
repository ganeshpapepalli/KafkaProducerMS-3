package com.poc.kafka.exceptions;

public class CustomException extends RuntimeException {

	static final long serialVersionUID = 1L;
	public String message;

	public CustomException(String message) {
		super(message);
	}

}
