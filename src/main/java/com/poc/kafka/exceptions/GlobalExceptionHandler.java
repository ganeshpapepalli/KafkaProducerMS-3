package com.poc.kafka.exceptions;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {
	
	@ExceptionHandler(CustomException.class)
	public CustomException handleCustomException(String message) {
		return new CustomException(message);
	}

}
