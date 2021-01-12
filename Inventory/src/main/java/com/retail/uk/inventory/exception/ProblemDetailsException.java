/**
 * 
 */
package com.retail.uk.inventory.exception;

import java.io.Serializable;

import org.springframework.http.HttpStatus;

import lombok.Getter;
import lombok.Setter;

/**
 * Custom Exception thrown with proper problem detials.
 *
 */
@Getter
@Setter
public class ProblemDetailsException extends Exception implements Serializable {

	private static final long serialVersionUID = 1270965746625837932L;

	private HttpStatus status;
	
	private String reasonPhrase;
	

	public ProblemDetailsException(HttpStatus status, String reasonPhrase, String detail) {
		super(detail);
		this.status = status;
        this.reasonPhrase = reasonPhrase;
	}

}
