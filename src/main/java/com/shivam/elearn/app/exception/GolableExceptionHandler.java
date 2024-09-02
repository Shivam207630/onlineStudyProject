package com.shivam.elearn.app.exception;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.shivam.elearn.app.dto.CustomMessage;

@RestControllerAdvice
public class GolableExceptionHandler {
	@ExceptionHandler(ResourceNotFoundException.class)
	public ResponseEntity<CustomMessage> handleResourceNotFound(ResourceNotFoundException ex){
		
		CustomMessage customMessage=new CustomMessage();
		customMessage.setMessage(ex.getMessage());
		customMessage.setSucess(false);
		return ResponseEntity.status(HttpStatus.NOT_FOUND).body(customMessage);
		
	}
	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity<Map<String, String>> handleValidError(MethodArgumentNotValidException ex){
		
		Map<String, String> errors=new HashMap<>();
		ex.getBindingResult().getAllErrors().forEach(error -> {
			String fieldName=((FieldError)error).getField();
			String errorMessage=error.getDefaultMessage();
			errors.put(fieldName,errorMessage);
		} );
		
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errors);
		
	}

}
