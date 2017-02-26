package com.intimetec.crns.core.exceptions;

/**
 * @author shiva.dixit
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
