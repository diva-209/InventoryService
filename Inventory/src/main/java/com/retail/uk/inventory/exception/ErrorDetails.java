/**
 * 
 */
package com.retail.uk.inventory.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Custom error details which are returned to the consumer.
 *
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ErrorDetails {

	private int errorCode;
	private String reasonPhrase;
	private String errorMessage;

}
