package com.ss.utopia.api.exception;

import java.sql.SQLIntegrityConstraintViolationException;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.NoSuchElementException;
import java.util.logging.Logger;

import org.hibernate.annotations.common.util.impl.LoggerFactory;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.fasterxml.jackson.databind.exc.InvalidFormatException;

@ControllerAdvice
public class ApiExceptionHandler {


	@ExceptionHandler(value = { NoSuchElementException.class })
	public ResponseEntity<Object> handleInvalidIdException(NoSuchElementException ex) {

		HttpStatus notFound = HttpStatus.NOT_FOUND;
		String message = "No entity with this Id found";
		
		ApiException apiException = new ApiException(message, 
				notFound,
				ZonedDateTime.now(ZoneId.of("Z")));
		
		return new ResponseEntity<Object>(apiException, notFound);

	}
	
	@ExceptionHandler(value = { DataIntegrityViolationException.class })
	public ResponseEntity<Object> handleMissingDataException(DataIntegrityViolationException ex) {


		HttpStatus notFound = HttpStatus.BAD_REQUEST;
		String message = "Missing or incorrect fields";
		
		ApiException apiException = new ApiException(message, 
				notFound,
				ZonedDateTime.now(ZoneId.of("Z")));
		
		
		return new ResponseEntity<Object>(apiException, HttpStatus.BAD_REQUEST);

	}
	
	@ExceptionHandler(value = { InvalidFormatException.class })
	public ResponseEntity<Object> handleInvalidDataException(InvalidFormatException ex) {

		HttpStatus notFound = HttpStatus.BAD_REQUEST;
		String message = "Wrong value type for field";
		
		ApiException apiException = new ApiException(message, 
				notFound,
				ZonedDateTime.now(ZoneId.of("Z")));
		
		return new ResponseEntity<Object>(apiException, HttpStatus.BAD_REQUEST);

	}
	
	
	
	

}
