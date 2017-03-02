package com.intimetec.crns.core.exceptions;

/**
 * {@code InvalidAuthTokenException} class to handle the invalid 
 * authentication token exception.
 * @author In Time Tec
 *
 */
@SuppressWarnings("serial")
public class InvalidAuthTokenException extends Exception {

	/**
	 * Creating object of the the class {@code InvalidAuthTokenException}.
	 */
	public InvalidAuthTokenException() {
	}

	/**
	 * @param message the detail message.
	 */
	public InvalidAuthTokenException(final String message) {
		super(message);
	}

}
