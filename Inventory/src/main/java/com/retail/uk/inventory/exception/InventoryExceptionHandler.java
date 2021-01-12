/**
 * 
 */
package com.retail.uk.inventory.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

/**
 * Handler used to handle the custom exceptions thrown and prepare response entity
 *
 */
@ControllerAdvice
public class InventoryExceptionHandler extends ResponseEntityExceptionHandler{
	
	@ExceptionHandler(value= {ProblemDetailsException.class})
	public ResponseEntity<Object> handleProblemDetailsException(ProblemDetailsException exception,WebRequest request) {
		ErrorDetails errorDetails = new ErrorDetails();
		errorDetails.setErrorCode(exception.getStatus().value());
		errorDetails.setReasonPhrase(exception.getReasonPhrase());
		errorDetails.setErrorMessage(exception.getMessage());
		
		return new ResponseEntity<Object>(errorDetails,exception.getStatus());
	}

}
