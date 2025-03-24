package org.persapiens.account.controller;

import java.time.Instant;

import org.persapiens.account.dto.ErrorDTO;
import org.persapiens.account.service.BeanExistsException;
import org.persapiens.account.service.BeanNotFoundException;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

	private ResponseEntity<ErrorDTO> responseEntity(HttpStatus httpStatus, String message) {
		return ResponseEntity.status(httpStatus)
			.<ErrorDTO>body(new ErrorDTO(Instant.now().toString(), httpStatus.value(), message));
	}

	private ResponseEntity<ErrorDTO> responseEntity(HttpStatus httpStatus, Exception exception) {
		return responseEntity(httpStatus, exception.getMessage());
	}

	@ExceptionHandler(IllegalArgumentException.class)
	public ResponseEntity<ErrorDTO> handleIllegalArgumentException(IllegalArgumentException exception) {
		return responseEntity(HttpStatus.BAD_REQUEST, exception);
	}

	@ExceptionHandler(BeanExistsException.class)
	public ResponseEntity<ErrorDTO> handleBeanExistsException(BeanExistsException exception) {
		return responseEntity(HttpStatus.CONFLICT, exception);
	}

	@ExceptionHandler(BeanNotFoundException.class)
	public ResponseEntity<ErrorDTO> handleBeanNotFoundException(BeanNotFoundException exception) {
		return responseEntity(HttpStatus.NOT_FOUND, exception);
	}

	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity<ErrorDTO> handleValidationExceptions(MethodArgumentNotValidException ex) {
		StringBuilder errors = new StringBuilder();
		for (var error : ex.getBindingResult().getFieldErrors()) {
			errors = errors.append(' ').append(error.getField()).append(':').append(error.getDefaultMessage());
		}
		return responseEntity(HttpStatus.BAD_REQUEST, errors.toString());
	}

	@ExceptionHandler(DataIntegrityViolationException.class)
	public ResponseEntity<ErrorDTO> handleDataIntegrityViolationException(
			DataIntegrityViolationException exception) {
		return responseEntity(HttpStatus.CONFLICT, exception);
	}

}
