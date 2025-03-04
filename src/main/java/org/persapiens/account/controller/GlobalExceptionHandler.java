package org.persapiens.account.controller;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import org.persapiens.account.service.BeanExistsException;
import org.persapiens.account.service.BeanNotFoundException;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

	private static final String ERROR = "error";

	@ExceptionHandler(IllegalArgumentException.class)
	public ResponseEntity<Map<String, String>> handleIllegalArgumentException(IllegalArgumentException exception) {
		return ResponseEntity.status(HttpStatus.BAD_REQUEST)
			.body(Collections.singletonMap(ERROR, exception.getMessage()));
	}

	@ExceptionHandler(BeanExistsException.class)
	public ResponseEntity<Map<String, String>> handleBeanExistsException(BeanExistsException exception) {
		return ResponseEntity.status(HttpStatus.CONFLICT).body(Collections.singletonMap(ERROR, exception.getMessage()));
	}

	@ExceptionHandler(BeanNotFoundException.class)
	public ResponseEntity<Map<String, String>> handleBeanNotFoundException(BeanNotFoundException exception) {
		return ResponseEntity.status(HttpStatus.NOT_FOUND)
			.body(Collections.singletonMap(ERROR, exception.getMessage()));
	}

	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity<Map<String, String>> handleValidationExceptions(MethodArgumentNotValidException ex) {
		Map<String, String> errors = new HashMap<>();
		ex.getBindingResult()
			.getFieldErrors()
			.forEach((error) -> errors.put(error.getField(), error.getDefaultMessage()));
		return ResponseEntity.badRequest().body(errors);
	}

}
