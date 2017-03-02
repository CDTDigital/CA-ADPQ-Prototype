/**
 * 
 */
package com.intimetec.crns.core.exceptions;

/**
 * {@code InvalidUserException} class to handle the invalid 
 * user exception.
 * @author shiva.dixit
 *
 */
@SuppressWarnings("serial")
public class InvalidUserException extends Exception {

	/**
	 *  Creating object of the {@link InvalidUserException}.
	 */
	public InvalidUserException() {
	}

	/**
	 * @param message the detail message.
	 */
	public InvalidUserException(final String message) {
		super(message);
	}

}
